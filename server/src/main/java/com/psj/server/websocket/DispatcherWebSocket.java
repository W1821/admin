package com.psj.server.websocket;

import com.google.gson.JsonObject;
import com.psj.common.util.JsonUtil;
import com.psj.common.util.ResponseMessageUtil;
import com.psj.common.util.SpringContextUtil;
import com.psj.pojo.dto.base.message.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.socket.WebSocketSession;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import static com.psj.common.constant.GlobalCodeEnum.ErrorCode;

/**
 * web socket分发处理
 *
 * @author saiya
 * @date 2018/5/24 0024
 */
@Component
@Slf4j
public class DispatcherWebSocket {

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    public DispatcherWebSocket(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    /**
     * 执行分发
     */
    public void doDispatch(WebSocketSession session, String requestMessage) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        // 判断是否有data节点
        if (!hasJsonData(requestMessage)) {
            WebSocketService.sendMsgBySession(session, ResponseMessageUtil.error(ErrorCode.ERROR_1030));
            return;
        }

        // 命令码
        Integer code = getJsonCode(requestMessage);
        // 拦截验证什么的
        if (!doPreHandlerInterceptor(code, session)) {
            WebSocketService.sendMsgBySession(session, ResponseMessageUtil.error(ErrorCode.ERROR_1030));
            return;
        }
        // 执行controller中的方法
        ResponseMessage result = handle(requestMessage, code);
        // 设置返回码
        result.setCode(code);
        // 返回响应
        WebSocketService.sendMsgBySession(session, result);
        log.info("返回响应json格式数据，{}", clearPassword(result));
    }


    /**
     * 执行拦截判断
     */
    private boolean doPreHandlerInterceptor(Integer code, WebSocketSession session) {
        // 如果是登录
        return true;
    }

    /**
     * 执行controller中的方法
     */
    private ResponseMessage handle(String requestMessage, Integer code) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        // 请求url
        String requestMapping = "/" + code;
        // 找到匹配的handlerMethod
        HandlerMethod handlerMethod = getHandlerMethod(requestMapping);
        if (handlerMethod == null) {
            return ResponseMessageUtil.error(ErrorCode.ERROR_1030);
        }
        // 执行HandlerMethod方法
        Object objectResult = invokeHandlerMethod(handlerMethod, requestMessage);
        // 响应数据
        return getServiceResult(objectResult);
    }

    /**
     * 找到匹配的handlerMethod
     */
    private HandlerMethod getHandlerMethod(String requestMapping) {
        // 所有的resultMapping
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            // @RequestMapping注解的value
            Set<String> patterns = entry.getKey().getPatternsCondition().getPatterns();
            // 匹配到方法
            if (patterns.contains(requestMapping)) {
                // 调用controller层匹配的方法
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * 调用匹配到的方法
     */
    private Object invokeHandlerMethod(HandlerMethod handlerMethod, String requestMessage) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        // 获取参数
        Object[] parameters = getMethodArgumentValues(requestMessage, handlerMethod);
        // 调用Controller中的方法
        return doInvoke(handlerMethod, parameters);
    }

    /**
     * 获取请求的参数，其实就是Controller方法中的参数
     */
    private Object[] getMethodArgumentValues(String requestMessage, HandlerMethod handlerMethod) throws IOException {
        MethodParameter[] parameters = handlerMethod.getMethodParameters();
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            // 如果参数有RequestBody注解，就赋值
            if (parameters[i].hasParameterAnnotation(RequestBody.class)) {
                MappingJackson2HttpMessageConverter messageConverter = createHttpMessageConverter();
                HttpInputMessage httpInputMessage = createHttpInputMessage(requestMessage);
                // 第i个参数
                args[i] = messageConverter.read(parameters[i].getNestedGenericParameterType(), null, httpInputMessage);
            }
        }
        return args;
    }

    /**
     * 获取HttpInputMessage
     */
    private HttpInputMessage createHttpInputMessage(String requestMessage) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        HttpHeaders headers = new HttpHeaders();
        return new MappingJacksonInputMessage(inputStream, headers);
    }

    /**
     * 使用spring mvc中的MappingJackson2HttpMessageConverter
     */
    private MappingJackson2HttpMessageConverter createHttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    /**
     * 调用Controller中的方法
     */
    private Object doInvoke(HandlerMethod handlerMethod, Object[] parameters) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class<?> controllerClass = handlerMethod.getMethod().getDeclaringClass();
        Class<?>[] parameterTypes = handlerMethod.getMethod().getParameterTypes();
        // controller中匹配的方法
        Method method = controllerClass.getDeclaredMethod(handlerMethod.getMethod().getName(), parameterTypes);
        // 设置可以访问私有方法
        method.setAccessible(true);
        // controller 对象
        Object controller = SpringContextUtil.getBean(controllerClass);
        // 调用方法
        return method.invoke(controller, parameters);
    }


    /**
     * 解析返回对象。
     */
    private ResponseMessage getServiceResult(Object objectResult) {
        if (!(objectResult instanceof ResponseEntity)) {
            return ResponseMessageUtil.success();
        }
        // 强转ResponseEntity类型
        ResponseEntity response = (ResponseEntity) objectResult;
        Object objectBody = response.getBody();
        if (!(objectBody instanceof ResponseMessage)) {
            return ResponseMessageUtil.success();
        }
        // 强转成ServiceResult类型
        return (ResponseMessage) objectBody;
    }

    /**
     * 获取json中的code
     */
    private Integer getJsonCode(String jsonStr) {
        return JsonUtil.getJsonInt(jsonStr, "code");
    }

    /**
     * 获取json中的data
     */
    private boolean hasJsonData(String jsonStr) {
        try {
            JsonObject data = JsonUtil.getJsonObject(jsonStr, "data");
            if (data == null) {
                log.error("The parameter value is not data", jsonStr);
                return false;
            }
        } catch (Exception e) {
            log.error("The parameter value is not data", jsonStr);
            return false;
        }
        return true;
    }

    /**
     * 获取json中的serialNum
     */
    private String getJsonSerialNum(JsonObject jsonObject) {
        String serialNum = null;
        try {
            serialNum = jsonObject.getAsJsonObject("serialNum").getAsString();
        } catch (Exception e) {
            log.info("The parameter value is not serialNum", jsonObject);
        }
        return serialNum;
    }

    /**
     * 清理日志中密码信息
     */
    public static String clearPassword(Object obj) {
        return JsonUtil.remove(JsonUtil.objToJsonString(obj), "password");
    }

}

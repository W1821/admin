package com.psj.server.spring.security;

import com.psj.common.constant.GlobalConstant;
import com.psj.common.util.JsonUtil;
import com.psj.common.util.ResponseMessageUtil;
import com.psj.pojo.dto.base.message.ResponseMessage;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 默认情况下, 由Spring Security提供的BasicAuthenticationEntryPoint 返回一个完整的401 Unauthorized 的html响应页面给客户端。
 * html格式在浏览器中很好的展示了错误信息，但是对其他情形却不太适合，
 * 比如对一个 REST API 来说，json 格式会更好。
 *
 * @author Administrator
 */
@Slf4j
@Component
public class RestAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    /**
     * 认证密码错误提示
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding(GlobalConstant.CHARACTER_ENCODING_UTF8);
        response.setContentType(GlobalConstant.CONTENT_TYPE_APPLICATION_JSON);
        ResponseMessage message = ResponseMessageUtil.buildNoAuthErrorMessage();
        @Cleanup PrintWriter out = response.getWriter();
        out.append(JsonUtil.objToJsonString(message));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName(GlobalConstant.REALM_NAME);
        super.afterPropertiesSet();
    }

}

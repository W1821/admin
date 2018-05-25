package com.psj.server.spring.advice;

import com.psj.common.exception.AdminException;
import com.psj.common.util.JsonUtil;
import com.psj.common.util.ResponseMessageUtil;
import com.psj.pojo.dto.base.message.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <br>
 * <b>功能：</b>全局异常处理<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Administrator
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage> errorHandler(Exception ex) {
        log.error("全局异常捕捉处理", ex);
        return ResponseMessageUtil.createResponseEntity(ResponseMessageUtil.error(ex.getMessage()));
    }


    /**
     * 参数验证异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage> errorHandler(MethodArgumentNotValidException ex) {
        // 参数校验失败信息
        FieldError fieldError = ex.getBindingResult().getFieldError();

        log.error("拦截捕捉参数验证异常 - message = {}", fieldError);

        if (fieldError == null) {
            return ResponseMessageUtil.createResponseEntity(ResponseMessageUtil.error());
        } else {
            return ResponseMessageUtil.createResponseEntity(ResponseMessageUtil.error(fieldError.getDefaultMessage()));
        }

    }

    /**
     * 拦截捕捉自定义异常
     */
    @ResponseBody
    @ExceptionHandler(value = AdminException.class)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage> myErrorHandler(AdminException ex) {
        ResponseMessage responseMessage = ex.getResponseMessage();
        log.error("拦截捕捉自定义异常 - message = {}", JsonUtil.objToJsonStr(responseMessage));
        return ResponseMessageUtil.createResponseEntity(responseMessage);
    }


}

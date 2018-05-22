package com.psj.common.exception;

import com.psj.common.constant.GlobalCodeEnum;
import com.psj.pojo.dto.base.message.ResponseMessage;
import lombok.Getter;

/**
 * <br>
 * <b>功能：</b>全局异常处理<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Pan.ShiJu
 */
@Getter
public class AdminException extends RuntimeException {

    private ResponseMessage responseMessage;

    public AdminException(GlobalCodeEnum.ErrorCode errorCode) {
        this.responseMessage = ResponseMessage.builder()
                .errorCode(errorCode.getKey())
                .errorMsg(errorCode.getValue())
                .build();
    }

}

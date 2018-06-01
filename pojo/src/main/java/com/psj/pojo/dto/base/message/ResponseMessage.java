package com.psj.pojo.dto.base.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <br>
 * <b>功能：</b>返回前端json消息类<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Administrator
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ApiModel("统一响应")
public class ResponseMessage<T> {

    @ApiModelProperty(value = "返回码", example = "1000")
    private Integer code;

    @ApiModelProperty(value = "错误状态码,如果是0，表示正确", example = "0")
    private Integer errorCode;

    @ApiModelProperty(value = "错误信息", example = "参数错误")
    private String errorMsg;

    @ApiModelProperty(value = "消息体，响应正确信息", example = "{}")
    private T data;

}
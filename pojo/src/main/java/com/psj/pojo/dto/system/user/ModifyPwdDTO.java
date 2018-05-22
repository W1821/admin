package com.psj.pojo.dto.system.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Administrator
 */
@Data
public class ModifyPwdDTO {

    @NotEmpty(message = "oldPwd cannot be empty")
    private String oldPwd;

    @NotEmpty(message = "newPwd cannot be empty")
    private String newPwd;

    @NotEmpty(message = "verifiedPwd cannot be empty")
    private String verifiedPwd;

}

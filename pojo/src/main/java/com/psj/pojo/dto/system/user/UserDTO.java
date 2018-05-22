package com.psj.pojo.dto.system.user;

import com.psj.pojo.dto.BaseDTO;
import com.psj.pojo.dto.system.role.BaseRoleDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

/**
 * 用户信息
 *
 * @author Administrator
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(callSuper = true)
@ApiModel("用户信息")
public class UserDTO extends BaseDTO {

    @ApiModelProperty(value = "主键id")
    private Long id;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", example = "18888888888")
    @NotEmpty(message = "phoneNumber cannot be empty")
    private String phoneNumber;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", example = "xxx")
    @NotEmpty(message = "password cannot be empty")
    private String password;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", example = "超级管理员")
    @NotEmpty(message = "userName cannot be empty")
    private String userName;
    /**
     * 头像相对路径
     */
    @ApiModelProperty(value = "头像相对路径", example = "/public/upload/2018/05/18/xxx.jpg")
    private String headPictureUrl;
    /**
     * 头像base64字符串
     */
    @ApiModelProperty(value = "头像base64字符串", example = "")
    private String headPictureBase64;
    /**
     * 帐号类型，0:平台，1：XXX，2：XXX，...
     */
    @ApiModelProperty(value = "帐号类型，0:平台，1：XXX，2：XXX，...", example = "0")
    private Integer accountType;

    /**
     * 帐号状态，0：可用，1：不可用
     */
    @ApiModelProperty(value = "帐号状态，0：可用，1：不可用", example = "0")
    @Pattern(regexp = "^0|1$", message = "accountStatus is illegal parameter")
    private Integer accountStatus;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2018-05-06 12:48:30")
    private Date createTime;

    /**
     * 区域
     */
    @ApiModelProperty(value = "区域id", example = "340105")
    private Integer areaId;
    @ApiModelProperty(value = "区域名称", example = "合肥市包河区")
    private String areaName;
    @ApiModelProperty(value = "详细地址", example = "xx路xx号")
    private String address;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", example = "")
    private List<BaseRoleDTO> roles;


}

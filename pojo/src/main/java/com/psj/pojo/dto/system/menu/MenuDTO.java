package com.psj.pojo.dto.system.menu;

import com.psj.pojo.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Id;

/**
 * 菜单信息
 * @author Administrator
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
@ApiModel("菜单信息")
public class MenuDTO extends BaseDTO {


    @Id
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 父id
     */
    @ApiModelProperty(value = "父id", example = "2")
    private Long pid;
    /**
     * 父id
     */
    @ApiModelProperty(value = "父id集合", example = "1,2")
    private String pids;
    /**
     * 系统url
     */
    @ApiModelProperty(value = "系统url", example = "/menu/list")
    private String url;
    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", example = "菜单管理")
    private String name;
    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标" )
    private String icon;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", example = "1")
    private Integer rank;
    /**
     * 注册RequestMapping ,分隔
     */
    @ApiModelProperty(value = "注册RequestMapping ,分隔", example = "/menu/list,/menu/query/{id}")
    private String actions;
    /**
     * 注册路由 ,分隔
     */
    @ApiModelProperty(value = "注册路由 ,分隔", example = "18888888888")
    private String routerUrls;

}

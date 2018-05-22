package com.psj.pojo.dto.system.role;

import com.psj.pojo.po.system.RoleModel;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RoleDTO extends BaseRoleDTO {


    /**
     * 角色状态, 0:启用 ，1:禁用
     */
    private Integer roleStatus;
    /**
     * 角色描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 角色关联菜单的id
     */
    private List<Long> menuIds;

    public RoleDTO(Long id) {
        this.setId(id);
    }

}

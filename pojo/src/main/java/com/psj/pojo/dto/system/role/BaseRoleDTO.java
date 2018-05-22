package com.psj.pojo.dto.system.role;

import com.psj.pojo.dto.BaseDTO;
import com.psj.pojo.po.system.RoleModel;
import lombok.*;

/**
 * @author Administrator
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class  BaseRoleDTO extends BaseDTO {

    /**
     * id主键
     */
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;


    public BaseRoleDTO(RoleModel model) {
        this.setId(model.getId());
        this.setRoleName(model.getRoleName());
    }
}

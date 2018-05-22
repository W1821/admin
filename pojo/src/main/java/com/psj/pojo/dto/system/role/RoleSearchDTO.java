package com.psj.pojo.dto.system.role;

import com.psj.pojo.dto.base.search.SearchDTO;
import lombok.*;


/**
 * @author Administrator
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoleSearchDTO extends SearchDTO {

    private String roleName;

}

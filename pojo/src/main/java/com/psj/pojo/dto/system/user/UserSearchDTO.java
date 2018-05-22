package com.psj.pojo.dto.system.user;

import com.psj.pojo.dto.base.search.SearchDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;


/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserSearchDTO extends SearchDTO {

    private String userName;

    /**
     * 帐号类型，0:平台，1：XXX，2：XXX
     */
    private Integer accountType;

    private Integer areaId;

}

package com.psj.pojo.po.system;

import com.psj.pojo.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "user")
public class UserModel extends BasePO {

    @Id
    @GeneratedValue
    private Long id;

    private String phoneNumber;

    private String userName;
    private String password;

    /**
     * 头像
     */
    private String headPictureUrl;

    /**
     * 身份证号码
     */
    @Column(updatable = false)
    private String idCard;


    /**
     * 帐号类型，0:平台，1：XXX，2：XXX，...
     */
    @Column(updatable = false)
    private Integer accountType;
    /**
     * 帐号状态，0：启用，1：禁用
     */
    private Integer accountStatus;
    /**
     * 删除状态，0：否，1：是
     */
    private Integer deleted;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 区域id
     */
    private Integer areaId;
    /**
     * 区域名称
     */
    private String areaName;
    /**
     * 详细地址
     */
    private String address;

    /**
     * 超级管理员，0：否，1：是
     */
    private Integer superAdmin;

    /**
     * 用户多对多关联角色
     */
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<RoleModel> roles;

}

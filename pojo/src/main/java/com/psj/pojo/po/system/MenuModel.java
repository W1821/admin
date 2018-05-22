package com.psj.pojo.po.system;

import com.psj.pojo.po.BasePO;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 菜单
 *
 * @author Administrator
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@Table(name = "menu")
public class MenuModel extends BasePO {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 父id
     */
    private Long pid;
    /**
     * 所有的父id
     */
    private String pids;
    /**
     * 系统url
     */
    private String url;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单名称
     */
    private String icon;
    /**
     * 排序
     */
    private Integer rank;
    /**
     * 注册RequestMapping ,分隔
     */
    private String actions;
    /**
     * 注册路由 ,分隔
     */
    private String routerUrls;
    /**
     * 删除状态，0：否，1：是
     */
    private Integer deleted;
    /**
     * 创建时间
     */
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * 修改时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 菜单和角色多对多关系
     */
    @ManyToMany(mappedBy = "menus")
    private List<RoleModel> roles;


    /**
     * 菜单名称添加前缀符号
     */
    public void addMenuMark(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("│ ");
        }
        this.setName(sb.append("├─").append(this.getName()).toString());
    }

}

package com.psj.core.dao.repository.system;


import com.psj.pojo.po.system.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>角色<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/6/19 17:10<br>
 *
 * @author Administrator
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long>, JpaSpecificationExecutor<RoleModel> {

    /**
     * 查询未删除的所有角色
     *
     * @param key 删除状态
     * @return
     */
    List<RoleModel> findByDeleted(int key);

    /**
     * 根据id集合查询
     *
     * @param roleIds 主键集合
     * @return
     */
    List<RoleModel> findByIdIn(List<Long> roleIds);
}

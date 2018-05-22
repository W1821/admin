package com.psj.core.dao.repository.system;


import com.psj.pojo.po.system.MenuModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <br>
 * <b>功能：</b><br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/6/19 17:10<br>
 *
 * @author Administrator
 */
@Repository
public interface MenuRepository extends JpaRepository<MenuModel, Long>, JpaSpecificationExecutor<MenuModel> {

    /**
     * 查询所有deleted菜单
     *
     * @param key 删除状态
     * @return 菜单集合
     */
    List<MenuModel> findByDeletedOrderByRank(int key);

    /**
     * 根据菜单id集合查询
     *
     * @param menuIds
     * @return
     */
    List<MenuModel> findByIdIn(List<Long> menuIds);


    /**
     * 删除子菜单
     *
     * @param id
     * @param deleted
     */
    @Modifying
    @Query(value = "update MenuModel set deleted = ?2 where pid = ?1")
    void deleteChildrenMenu(Long id, Integer deleted);
}

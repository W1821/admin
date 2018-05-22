package com.psj.core.dao.mapper.system;

import com.psj.pojo.po.system.MenuModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Administrator
 */
@Mapper
public interface MenuMapper {
    /**
     * 根据id查询
     *
     * @param id 菜单表主键
     * @return 菜单表对象
     */
    MenuModel queryById(Long id);
}

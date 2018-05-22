package com.psj.core.service.system;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.psj.common.constant.GlobalConstant;
import com.psj.common.constant.GlobalEnum;
import com.psj.common.util.ResponseMessageUtil;
import com.psj.core.bo.system.MenuBO;
import com.psj.core.bo.system.RoleBO;
import com.psj.core.bo.system.UserBO;
import com.psj.core.dao.repository.system.MenuRepository;
import com.psj.core.service.BaseService;
import com.psj.pojo.dto.base.message.ResponseMessage;
import com.psj.pojo.dto.system.menu.MenuDTO;
import com.psj.pojo.po.system.MenuModel;
import com.psj.pojo.po.system.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单控制器
 *
 * @author Administrator
 */
@Service
public class MenuService extends BaseService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    /**
     * 查询所有
     */
    public ResponseMessage list() {
        List<MenuModel> menuList = menuRepository.findByDeletedOrderByRank(GlobalEnum.DELETED.NO.getKey());
        // 排序菜单
        List<MenuModel> sortedMenuList = getSortedMenuModels(menuList);
        // 返回DTO
        List<MenuDTO> dtoList = poListToDtoList(sortedMenuList);
        return ResponseMessageUtil.success(dtoList);
    }

    /**
     * 查询一个
     */
    public ResponseMessage query(Long id) {
        MenuBO menuBO = MenuBO.builder().repository(menuRepository).dto(MenuDTO.builder().id(id).build()).build();
        return menuBO.queryOne();
    }

    /**
     * 保存
     */
    public ResponseMessage save(MenuDTO dto) {
        MenuBO menuBO = MenuBO.builder().repository(menuRepository).dto(dto).build();
        return menuBO.save();
    }

    /**
     * 删除成功
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage delete(Long id) {
        MenuBO bo = MenuBO.builder().repository(menuRepository).dto(MenuDTO.builder().id(id).build()).build();
        return bo.delete();
    }

    /**
     * 当前登陆人的菜单
     */
    public ResponseMessage getMenuList() {
        UserModel model = getUserDetail();
        List<MenuModel> menuList = getMenuModels(model);
        // 返回DTO
        List<MenuDTO> menuDTOList = poListToDtoList(menuList);
        return ResponseMessageUtil.success(menuDTOList);
    }

    /**
     * 当前登陆人的菜单
     */
    public List<String> getMenuActionList(UserModel model) {
        List<MenuModel> menuList = getMenuModels(model);
        Set<String> menuUrls = Sets.newHashSet();
        menuList.stream()
                .filter(e -> e.getActions() != null)
                .forEach(e -> Collections.addAll(menuUrls, e.getActions().split(GlobalConstant.COMMA)));
        return Lists.newArrayList(menuUrls);
    }

    /* ================================================ 私有方法 =================================================*/

    /**
     * 返回DTO对象
     */
    private List<MenuDTO> poListToDtoList(List<MenuModel> sortedMenuList) {
        return sortedMenuList.stream().map(e -> MenuBO.builder().model(e).build().poToDto()).collect(Collectors.toList());
    }


    /**
     * 用户的所有权限的菜单
     */
    private List<MenuModel> getMenuModels(UserModel model) {
        List<MenuModel> menuList;

        // 如果是超级管理员
        UserBO userBO = UserBO.builder().model(model).build();
        if (userBO.userIsSuperAdmin()) {
            menuList = menuRepository.findByDeletedOrderByRank(GlobalEnum.DELETED.NO.getKey());
        } else {
            // 用户关联的权限菜单
            Set<MenuModel> menus = Sets.newHashSet();
            model.getRoles().stream()
                    .filter(e -> RoleBO.builder().model(e).build().unDeleted())
                    .forEach(e -> menus.addAll(e.getMenus()));
            // 上级菜单
            List<MenuModel> parentMenuList = getParentMenus(menus);
            menus.addAll(parentMenuList);

            menuList = Lists.newArrayList(menus);
        }
        return menuList;
    }


    /**
     * 上级菜单
     */
    private List<MenuModel> getParentMenus(Set<MenuModel> menuList) {
        Set<String> menuIds = Sets.newHashSet();
        menuList.stream()
                .filter(e -> e.getPids() != null)
                .forEach(e -> menuIds.addAll(Lists.newArrayList(e.getPids().split(GlobalConstant.COMMA))));
        List<Long> parentIds = menuIds.stream().map(Long::parseLong).collect(Collectors.toList());
        return menuRepository.findByIdIn(parentIds);
    }


    /**
     * 排序菜单
     */
    private List<MenuModel> getSortedMenuModels(List<MenuModel> menuList) {
        // 顶级菜单
        List<MenuModel> firstMenuList = menuList.stream()
                .filter(e -> e.getPid() == null)
                .collect(Collectors.toList());

        List<MenuModel> sortedMenuList = new ArrayList<>();
        firstMenuList.forEach(menuModel -> {
            // 菜单深度,级别
            int index = 0;
            sortedMenuList.add(menuModel);
            sortedMenuList.addAll(getAllChildrenMenu(menuList, menuModel, index));
        });
        return sortedMenuList;
    }

    /**
     * 递归出所有的下级菜单
     */
    private List<MenuModel> getAllChildrenMenu(List<MenuModel> menuList, MenuModel menuModel, int depth) {
        List<MenuModel> tempList = new ArrayList<>();
        for (int i = 0; i < menuList.size(); i++) {
            if (menuModel.getId().equals(menuList.get(i).getPid())) {
                MenuModel temp = menuList.get(i);
                temp.addMenuMark(depth);
                tempList.add(temp);
                menuList.remove(i);
                i--;
                tempList.addAll(getAllChildrenMenu(menuList, temp, depth + 1));
            }
        }
        return tempList;
    }

}

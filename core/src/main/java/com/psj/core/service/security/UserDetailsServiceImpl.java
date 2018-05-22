package com.psj.core.service.security;

import com.psj.common.constant.GlobalConstant;
import com.psj.common.constant.system.UserEnum;
import com.psj.core.service.system.MenuService;
import com.psj.core.service.system.UserService;
import com.psj.pojo.dto.security.UserDetail;
import com.psj.pojo.po.system.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <br>
 * <b>功能：</b>spring security 鉴权，重写loadUserByUsername方法，通过帐号获取帐号id、码、是否可用等信息<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/10/4 23:27<br>
 *
 * @author Administrator
 */
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final MenuService menuService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService, MenuService menuService) {
        this.userService = userService;
        this.menuService = menuService;
    }

    /**
     * 查询登录信息是否正确
     *
     * @param userName 手机号码
     */
    @Override
    public UserDetails loadUserByUsername(String userName) {
        log.debug("--------------系统登录鉴权开始---------------");

        UserModel user = userService.findByPhoneNumber(userName);
        if (user == null) {
            throw new UsernameNotFoundException("系统中不存在该用户。");
        }

        // 设置系统帐号类型
        Set<GrantedAuthority> authSet = new HashSet<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(UserEnum.ACCOUNT_TYPE.getValueByKey(user.getAccountType()));
        authSet.add(grantedAuthority);

        // 当前用户的所有菜单url
        UserDetail userDetail = new UserDetail(user, authSet);

        List<String> menus = menuService.getMenuActionList(user);
        List<String[]> pathVariableArray = getPathVariable(menus);

        userDetail.setPathInvariable(menus);
        userDetail.setPathVariable(pathVariableArray);
        log.debug("--------------系统登录鉴权结束---------------");
        return userDetail;
    }

    private List<String[]> getPathVariable(List<String> menus) {
        List<String> pathVariable = new ArrayList<>();
        for (int i = 0; i < menus.size(); i++) {
            if (menus.get(i).contains(GlobalConstant.OPEN_BRACE) && menus.get(i).contains(GlobalConstant.CLOSE_BRACE)) {
                pathVariable.add(menus.get(i));
                menus.remove(i);
                i--;
            }
        }
        List<String[]> pathVariableArray = new ArrayList<>();
        for (String path : pathVariable) {
            if (path.startsWith(GlobalConstant.LEFT_SLASH)) {
                path = path.replaceFirst(GlobalConstant.LEFT_SLASH, GlobalConstant.EMPTY_STRING);
            }
            pathVariableArray.add(path.split(GlobalConstant.LEFT_SLASH));
        }
        return pathVariableArray;
    }

}

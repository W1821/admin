package com.psj.pojo.dto.security;

import com.psj.pojo.po.system.UserModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>自定义security用户<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/10/4 23:27<br>
 *
 * @author Administrator
 */

@Getter
@Setter
public class UserDetail extends User {

    /**
     * 用户名（账号）
     */
    private Long id;

    /**
     * 用户表对象
     */
    private UserModel user;

    /**
     * 普通uri，没有可变参数
     */
    private List<String> pathInvariable;
    /**
     * 含有参数的rui
     */
    private List<String[]> pathVariable;

    public UserDetail(UserModel user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getPhoneNumber(), user.getPassword(), authorities);
        this.id = user.getId();
        this.user = user;
    }


}

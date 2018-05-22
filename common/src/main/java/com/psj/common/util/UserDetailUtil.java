package com.psj.common.util;

import com.psj.pojo.dto.security.UserDetail;
import com.psj.pojo.po.system.UserModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * <br>
 * <b>功能：</b>鉴权用户工具类<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/6/1 17:46<br>
 *
 * @author Administrator
 */
public class UserDetailUtil {

    /**
     * 当前用户
     */
    public static UserDetail getCurrentUserDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetail) {
            return ((UserDetail) principal);
        }
        return null;
    }


    /**
     * 当前用户
     */
    public static UserModel getCurrentUser() {
        UserDetail userDetail = getCurrentUserDetail();
        if (userDetail != null) {
            return userDetail.getUser();
        }
        return null;
    }


}

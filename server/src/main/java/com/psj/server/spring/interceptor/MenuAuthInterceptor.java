package com.psj.server.spring.interceptor;

import com.psj.common.constant.GlobalConstant;
import com.psj.common.util.ExceptionUtil;
import com.psj.common.util.UserDetailUtil;
import com.psj.core.bo.system.UserBO;
import com.psj.pojo.dto.security.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>菜单权限拦截器<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Administrator
 */
@Slf4j
@Component
public class MenuAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.debug("---------------拦截器开始---------------");

        // 判断当前登录人的权限
        UserDetail principal = UserDetailUtil.getCurrentUserDetail();
        // 没有登录直接不通过,使用了spring
        if (principal == null) {
            log.debug("没有登录，不通过");
            ExceptionUtil.throwUnauthorizedError();
            return false;
        }

        // 判断是否是超级管理员
        UserBO bo = UserBO.builder().model(principal.getUser()).build();
        if (bo.userIsSuperAdmin()) {
            log.debug("我是超级管理员，通过");
            return true;
        }

        // 判断访问权限
        // 1.无可变参数url
        String uri = request.getRequestURI();
        if (principal.getPathInvariable().contains(uri)) {
            return true;
        }

        // 2.含有可变参数url
        String[] uris = getURIDirs(uri);
        List<String[]> pathVariable = principal.getPathVariable();
        if (!matchURI(uris, pathVariable)) {
            // 没权限返回401
            log.debug("无权限，不通过");
            ExceptionUtil.throwUnauthorizedError();
        }
        log.debug("---------------拦截器结束---------------");
        // 只有返回true才会继续向下执行，返回false取消当前请求
        return true;
    }


    /**
     * 匹配请求路径是否在权限的url
     */
    private boolean matchURI(String[] uris, List<String[]> pathVariable) {
        for (String[] paths : pathVariable) {
            if (uris.length != paths.length) {
                continue;
            }
            for (int i = 0; i < paths.length; i++) {
                if (paths[i].contains(GlobalConstant.OPEN_BRACE) && paths[i].contains(GlobalConstant.CLOSE_BRACE)) {
                    continue;
                }
                if (!paths[i].equals(uris[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private String[] getURIDirs(String uri) {
        if (uri.startsWith(GlobalConstant.LEFT_SLASH)) {
            uri = uri.replaceFirst(GlobalConstant.LEFT_SLASH, GlobalConstant.EMPTY_STRING);
        }
        return uri.split(GlobalConstant.LEFT_SLASH);
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

}

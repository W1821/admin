package com.psj.config.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * <br>
 * <b>功能：</b>基本配置<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Administrator
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
@Configuration
@ConfigurationProperties(prefix = "admin.config")
public class AdminConfig {

    /**
     * 手机号码正则表达式
     */
    private String phoneNumberRegexp;
    /**
     * security的session退出请求
     */
    private String logoutUrl;
    /**
     * security退出请求成功跳转url
     */
    private String logoutSuccessUrl = "/";

    /**
     * 拦截器、权限验证忽略path
     */
    private String[] ignoredPath = {
            /* 静态资源 */
            "/",
            "/*.ico",
            "/*.js",
            "/*.css",
            "/*.html",
            "/assets/**",

            /* swagger页面，TODO 未做安全限制，项目部署注意一下 */
            "/webjars/**",
            "/v2/api-docs",
            "/swagger-resources/**",

            /* 前端访问防止404 */
            "/main/**",

            /* web socket TODO 暂时忽略，便于测试*/
            "/ws/**",

            /* 阿里数据库连接池监控 */
            "/druid/**"
    };

}

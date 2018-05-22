package com.psj.server.spring.config;

import com.psj.config.file.FileUploadConfig;
import com.psj.config.ueditor.UeditorUploadConfig;
import com.psj.config.web.AdminConfig;
import com.psj.core.service.security.UserDetailsServiceImpl;
import com.psj.server.spring.security.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <br>
 * <b>功能：</b>Security登录配置，HTTP基本认证(Basic Authentication)<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 * <p>
 * <br>@EnableGlobalMethodSecurity(prePostEnabled = true) //允许进入页面方法前检验
 *
 * @author Administrator
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AdminConfig adminConfig;
    private final FileUploadConfig fileUploadConfig;
    private final UeditorUploadConfig ueditorUploadConfig;
    private final PasswordEncoder passwordEncoder;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public WebSecurityConfig(AdminConfig adminConfig, FileUploadConfig fileUploadConfig, UeditorUploadConfig ueditorUploadConfig, PasswordEncoder passwordEncoder, RestAuthenticationEntryPoint restAuthenticationEntryPoint, UserDetailsServiceImpl userDetailsService) {
        this.adminConfig = adminConfig;
        this.fileUploadConfig = fileUploadConfig;
        this.ueditorUploadConfig = ueditorUploadConfig;
        this.passwordEncoder = passwordEncoder;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //添加我们自定的user detail service认证；
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()

                .authenticationEntryPoint(restAuthenticationEntryPoint)

                /* 登出请求配置 */
                .and().logout().logoutUrl(adminConfig.getLogoutUrl()).logoutSuccessUrl(adminConfig.getLogoutSuccessUrl())

                .and().authorizeRequests().anyRequest().authenticated()
                .and().headers().frameOptions().disable()
                .and().csrf().disable();

    }

    @Override
    public void configure(WebSecurity web) {
        //忽略的路径，不会权限校验
        web.ignoring()
                .antMatchers(adminConfig.getIgnoredPath())
                .antMatchers(fileUploadConfig.getResourceHandler())
                .antMatchers(ueditorUploadConfig.getResourceHandler());
    }


}
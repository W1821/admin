package com.psj.server.spring.servlet;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * 阿里数据库连接池监控Servlet
 * @author Administrator
 */
@WebServlet(urlPatterns = "/druid/*",
        initParams = {
                @WebInitParam(name = "allow", value = "127.0.0.1,192.168.1.88"),// IP白名单(没有配置或者为空，则允许所有访问)
                @WebInitParam(name = "deny", value = ""),// IP黑名单 (存在共同时，deny优先于allow)
                @WebInitParam(name = "loginUsername", value = "admin@123456"),// 用户名
                @WebInitParam(name = "loginPassword", value = "123456*admin"),// 密码
                @WebInitParam(name = "resetEnable", value = "false")// 禁用HTML页面上的“Reset All”功能
        })
public class AliDruidStatViewServlet extends StatViewServlet {


}
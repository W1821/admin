package com.psj.server.spring.filter;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * 阿里连接池过滤器配置
 *
 * @author Administrator
 */
@WebFilter(
        filterName = "druidWebStatFilter",
        urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")
        }
)
public class AliDruidStatFilter extends WebStatFilter {

}

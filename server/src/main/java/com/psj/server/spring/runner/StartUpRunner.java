package com.psj.server.spring.runner;


import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * <br>
 * <b>功能：</b>服务启动时执行<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 * @author Administrator
 */
@Component
@Order(value = 1)
public class StartUpRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作 <<<<<<<<<<<<<");
    }

}
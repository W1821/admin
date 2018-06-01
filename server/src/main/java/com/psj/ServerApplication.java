package com.psj;

import com.psj.common.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

/**
 * @author Administrator
 */
@SpringBootApplication
@Slf4j
public class ServerApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ServerApplication.class, args);
        SpringContextUtil.setApplicationContext(applicationContext);
    }

    @PostConstruct
    public void init() {
        log.info("应用启动");
    }

}

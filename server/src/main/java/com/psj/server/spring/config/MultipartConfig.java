package com.psj.server.spring.config;

import com.psj.config.file.FileUploadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * <br>
 * <b>功能：</b>文件上传配置<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Administrator
 */
@Configuration
@EnableConfigurationProperties(FileUploadConfig.class)
public class MultipartConfig {

    private final FileUploadConfig fileUploadConfig;

    @Autowired
    public MultipartConfig(FileUploadConfig fileUploadConfig) {
        this.fileUploadConfig = fileUploadConfig;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(fileUploadConfig.getLocation());
        factory.setMaxFileSize(fileUploadConfig.getMaxFileSize());
        factory.setMaxRequestSize(fileUploadConfig.getMaxRequestSize());
        return factory.createMultipartConfig();
    }

}

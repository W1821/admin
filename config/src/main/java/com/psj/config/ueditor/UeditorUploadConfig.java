package com.psj.config.ueditor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "ueditor.upload")
public class UeditorUploadConfig {

    /**
     * 所有上传文件根目录
     */
    private String rootPath;
    /**
     * 图片访问映射前缀
     */
    private String resourcePrefix;
    /**
     * 图片文件映射匹配
     */
    private String resourceHandler;


}

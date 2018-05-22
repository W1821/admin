package com.psj.config.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * <br>
 * <b>功能：</b>文件上传配置<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Administrator
 */
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "file.upload")
public class FileUploadConfig {

    private static final String KB = "KB";
    private static final String MB = "MB";

    /**
     * 文件缓存位置
     */
    private String location;
    /**
     * 统一文件大小限制 KB,MB
     */
    private String maxFileSize;
    /**
     * 设置图片大小限制 KB,MB
     */
    private String maxImageSize;
    /**
     * 设置总上传数据总大小 KB,MB
     */
    private String maxRequestSize;

    /**
     * 所有上传文件根目录
     */
    private String rootPath;
    /**
     * 图片访问映射前缀
     */
    private String imageUrlPrefix;
    /**
     * 图片文件映射匹配
     */
    private String resourceHandler;

    public long getMaxSize(String size) {
        size = size.toUpperCase();
        if (size.endsWith(KB)) {
            return Long.valueOf(size.substring(0, size.length() - 2)) * 1024L;
        } else {
            return size.endsWith(MB) ? Long.valueOf(size.substring(0, size.length() - 2)) * 1024L * 1024L : Long.valueOf(size);
        }
    }

    public String getLocation() {
        File rootPathFile = new File(location);
        if (!rootPathFile.exists() && !rootPathFile.isDirectory()) {
            if (!rootPathFile.mkdirs()) {
                return location;
            }
        }
        return location;
    }
}

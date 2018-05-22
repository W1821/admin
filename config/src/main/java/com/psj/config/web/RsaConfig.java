package com.psj.config.web;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Getter
@Setter
@Component
@Configuration
@ConfigurationProperties(prefix = "rsa")
public class RsaConfig {

    /**
     * 公钥文件路径，可以是绝对路径或相对路径
     */
    private String publicKeyPath;
    /**
     * 私钥文件路径，可以是绝对路径或相对路径
     */
    private String privateKeyPath;

}

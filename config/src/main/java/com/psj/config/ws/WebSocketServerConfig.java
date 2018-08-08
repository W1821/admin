package com.psj.config.ws;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 股票资管socket api server配置
 *
 * @author saiya
 * @date 2018/03/28.
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "ws.server")
public class WebSocketServerConfig {

    /**
     * 缓冲区大小
     */
    private int bufferSize;

    /**
     * 发送超时时间
     */
    private long sendTimeout;

    /**
     * web socket URL paths
     */
    private String[] paths;

}


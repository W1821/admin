package com.psj.server.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * UserInfoDO工厂
 *
 * @author saiya
 * @date 2018/5/29 0029
 */
@Component
@Slf4j
public class UserInfoFactory {


    /**
     * 唯一生产方法
     */
    public UserInfoDO create() {

        // TODO 具体实现
        return new UserInfoDO();
    }

}

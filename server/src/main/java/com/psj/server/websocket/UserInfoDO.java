package com.psj.server.websocket;

import lombok.Getter;
import lombok.ToString;

/**
 * 用户实时信息对象
 * 项目启动时，查询数据库所有用户信息，初始化此对象
 * 每当用户信息变动，需要修改具体信息
 *
 * @author saiya
 * @date 2018/5/24 0024
 * @see UserInfoFactory 初始化工厂
 */
@Getter
@ToString
public class UserInfoDO {

    // TODO 具体对待

    /**
     * 获取用户主键
     *
     * @return 用户主键字符串
     */
    public String getUserId() {
        return null;
    }


}

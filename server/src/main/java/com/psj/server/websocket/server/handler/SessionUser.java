package com.psj.server.websocket.server.handler;


import com.psj.common.util.ThreadPoolUtil;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * web socket每次建立连接时，创建此对象
 * 用户登录后，绑定用户信息
 * 只提供一个构造方法和get方法
 *
 * @author saiya
 * @date 2018/2/23.
 */
@Getter
public class SessionUser {

    /**
     * 用户详细缓存对象
     */
    private UserInfoDO userInfoDO;

    /**
     * 用户socket session
     */
    private WebSocketSession session;

    /**
     * 用户发送数据的线程池
     */
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 构造方法
     */
    SessionUser(WebSocketSession session) {
        this.session = session;
        this.threadPoolExecutor = ThreadPoolUtil.createThreadPoolExecutor("web-socket-send-msg", 1000);
    }

    /**
     * 绑定用户信息到Session上
     */
    public void bindUserInfoDO(UserInfoDO userInfoDO) {
        this.userInfoDO = userInfoDO;
    }

    /**
     * 解绑session与用户的关系
     */
    public void unBindUserInfoDO() {
        this.userInfoDO = null;
    }


    /**
     * 获取当前session绑定的用户主键
     *
     * @return 用户主键
     */
    public String getUserId() {
        return this.userInfoDO == null ? null : this.userInfoDO.getUserId();
    }


}

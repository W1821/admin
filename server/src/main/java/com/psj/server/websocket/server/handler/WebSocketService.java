package com.psj.server.websocket.server.handler;


import com.psj.common.util.JsonUtil;
import com.psj.pojo.dto.base.message.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

/**
 * web socket
 *
 * @author saiya
 * @date 2018/05/24
 */
@Slf4j
public class WebSocketService {

    /**
     * 记录WebSocket链接信息
     */
    private static Map<String, SessionUser> sessionUserMap = new ConcurrentHashMap<>(1 << 7);


    /**
     * 当有一个连接建立的时候，调用此方法
     */
    public static void put(WebSocketSession session) {
        SessionUser user = getSessionUserBySessionId(session.getId());
        if (user == null) {
            sessionUserMap.put(session.getId(), new SessionUser(session));
            log.info("用户session:" + session.getId() + " 建立新链接");
        }
    }

    /**
     * 当有一个连接关闭或出现异常的时候，调用此方法
     */
    public static void remove(WebSocketSession session) {
        if (hasSessionId(session.getId())) {
            sessionUserMap.remove(session.getId());
        }
    }

    /* =============================================== 查询信息 ================================================= */


    /**
     * 获取当前登录用户信息
     */
    public static SessionUser getCurrentSessionUser() {
        return ThreadLocalUserInfo.get();
    }

    /**
     * 根据一个userId判断是否在线
     */
    public static boolean isOnline(String userId) {
        if (userId == null) {
            return false;
        }
        for (SessionUser sessionUser : getTotalSessionUser()) {
            if (StringUtils.equals(sessionUser.getUserId(), userId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据一个userId 获取对应的sessionId
     */
    public static List<SessionUser> getSessionUserByUserId(String userId) {
        if (userId == null) {
            return null;
        }
        return sessionUserMap.entrySet()
                .stream()
                .filter(e -> StringUtils.equals(e.getValue().getUserId(), userId))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    /**
     * 根据一个sessionId获取当前session绑定的用户信息
     */
    public static SessionUser getSessionUserBySessionId(String sessionId) {
        if (hasSessionId(sessionId)) {
            return sessionUserMap.get(sessionId);
        }
        return null;
    }

    /**
     * 获取所有客户端连接数量
     */
    public static int getTotalSessionUserCount() {
        return sessionUserMap.size();
    }

    /**
     * 获取所有在线用户，已登录的用户
     */
    public static List<SessionUser> getTotalSessionUser() {
        return sessionUserMap.entrySet()
                .stream()
                .filter(e -> e.getValue().getUserId() != null)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    /* =============================================== 响应数据 ================================================= */

    /**
     * 给指定用户的所有登录终端推送消息
     */
    public static void sendMsgByUserId(String userId, ResponseMessage message) {
        sessionUserMap.entrySet()
                .stream()
                .filter(e -> e.getValue().getUserId() != null && StringUtils.equals(e.getValue().getUserInfoDO().getUserId(), userId))
                .forEach(e -> sendMsg(e.getValue(), JsonUtil.objToJsonString(message)));
    }

    /**
     * 响应消息给客户端
     */
    public static void sendMsgBySession(WebSocketSession session, ResponseMessage message) {
        SessionUser userMap = getSessionUserBySessionId(session.getId());
        if (userMap != null) {
            sendMsg(userMap, JsonUtil.objToJsonString(message));
        }
    }

    /* =============================================== 私有方法 ================================================= */

    /**
     * 每个用户使用独立的一个线程池推送消息
     */
    private static void sendMsg(SessionUser user, String message) {
        WebSocketSession session = user.getSession();
        if (session.isOpen()) {
            try {
                user.getThreadPoolExecutor().execute(() -> WebSocketMessageUtil.sendMessage(user.getSession(), message));
            } catch (RejectedExecutionException e) {
                //在客户端接收慢的时候，会出现队列已满的情况
                log.error("系统主动推送消息给客户端失败 队列已满,id={}, ip={}", session.getId());
                if (user.getSession().isOpen()) {
                    try {
                        user.getSession().close(CloseStatus.SERVICE_RESTARTED);
                    } catch (IOException e1) {
                        log.error("系统主动推送消息给客户端失败 队列已满，尝试关闭socket失败", e);
                    }
                }
            } catch (Exception e) {
                //其他未知异常
                log.error("系统主动推送消息给客户端失败,id={}", session.getId());
            }
        } else {
            if (!user.getThreadPoolExecutor().isShutdown()) {
                user.getThreadPoolExecutor().shutdownNow();
            }
        }
    }


    /**
     * 判断key是否存在
     */
    private static boolean hasSessionId(String sessionId) {
        return sessionId != null;
    }


}

package com.psj.server.websocket.server.handler;

/**
 * 用户信息线程副本
 *
 * @author saiya
 */
public class ThreadLocalUserInfo {

    /**
     * 线程级变量
     */
    private static final ThreadLocal<SessionUser> LOCAL_USER_INFO = new ThreadLocal<>();

    private ThreadLocalUserInfo() {
    }

    public static void set(SessionUser threadUser) {
        LOCAL_USER_INFO.set(threadUser);
    }

    public static SessionUser get() {
        return LOCAL_USER_INFO.get();
    }

    public static void clear() {
        LOCAL_USER_INFO.remove();
    }

}

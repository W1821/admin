package com.psj.server.websocket.server.endpoint;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author saiya
 * @date 2018/8/3 0003
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class WsSessionUser {

    @Getter
    private Session session;

    private ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("WsSessionUser-send-thread-pool-%d").build();

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(2), factory);

    public WsSessionUser(Session session) {
        this.session = session;
    }

    public void sendMessageByPool(String message) {
        try {
            if (!threadPoolExecutor.isShutdown()) {
                threadPoolExecutor.execute(() -> sendMessage(message));
            }
        } catch (RejectedExecutionException e) {
            log.error("RejectedExecutionException", e);
            threadPoolExecutor.shutdownNow();
            try {
                session.close();
            } catch (IOException e1) {
                log.error("close socket error", e);
            }

        }
    }

    private void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("send message error，sessionId={}", session.getId(), e);
        }
    }


}
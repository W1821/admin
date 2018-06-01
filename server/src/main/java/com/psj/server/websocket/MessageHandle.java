package com.psj.server.websocket;

import com.psj.common.constant.GlobalCodeEnum;
import com.psj.common.exception.AdminException;
import com.psj.common.util.ResponseMessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * web socket
 *
 * @author saiya
 * @date 2018/5/24 0024
 */
@Slf4j
@Component
public class MessageHandle extends TextWebSocketHandler {

    private final DispatcherWebSocket dispatcherWebSocket;

    @Autowired
    public MessageHandle(DispatcherWebSocket dispatcherWebSocket) {
        this.dispatcherWebSocket = dispatcherWebSocket;
    }

    /**
     * socket 接收到文字消息
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        // 清理线程对象
        ThreadLocalUserInfo.clear();

        // 接受的文本信息
        String requestMessage = message.getPayload();
        log.debug("socket receive message, id={}, content={}", session.getId(), requestMessage);
        try {
            // 为空判断处理
            if (StringUtils.isEmpty(requestMessage)) {
                WebSocketService.sendMsgBySession(session, ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1030));
                return;
            }
            // 心跳消息处理
            String heart = "heart";
            if (StringUtils.equals(heart, requestMessage)) {
                WebSocketService.sendMsgBySession(session, ResponseMessageUtil.success());
                return;
            }
            // 设置线程对象
            ThreadLocalUserInfo.set(WebSocketService.getSessionUserBySessionId(session.getId()));
            // 执行分发处理
            dispatcherWebSocket.doDispatch(session, requestMessage);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            log.error("调用controller中的方法错误", e);
            WebSocketService.sendMsgBySession(session, ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_500));
        } catch (InvocationTargetException e) {
            log.error("controller中的方法抛出异常", e);
            if (e.getTargetException() instanceof AdminException) {
                AdminException exception = (AdminException) e.getTargetException();
                WebSocketService.sendMsgBySession(session, exception.getResponseMessage());
            } else {
                WebSocketService.sendMsgBySession(session, ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_500));
            }
        } catch (Exception e) {
            log.error("socket received message process fail, message={}", requestMessage, e);
            WebSocketService.sendMsgBySession(session, ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_500));
        }
    }


    /**
     * 新连接打开处理
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("new session connected,sessionId={}", session.getId());
        WebSocketService.put(session);
    }

    /**
     * socket 传输异常处理
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        SessionUser user = WebSocketService.getSessionUserBySessionId(session.getId());
        String userId = "";
        if (user != null) {
            userId = user.getUserId();
        }
        if (exception instanceof EOFException || exception instanceof IOException) {
            log.error("a session transport error, sessionId={}, userId={}, errorMessage={}", session.getId(), userId, exception.getMessage());
        } else {
            log.error("a session transport error, sessionId={}, userId={}", session.getId(), userId, exception);
        }
        WebSocketService.remove(session);
        session.close();
    }

    /**
     * socket 关闭处理
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        SessionUser user = WebSocketService.getSessionUserBySessionId(session.getId());
        String userId = "";
        if (user != null) {
            userId = user.getUserId();
        }
        log.error("a session close,sessionId={}, userId={}, statusCode={}, statusReason={}", session.getId(), userId, status.getCode(), status.getReason());
        WebSocketService.remove(session);
        session.close();
    }

    /**
     * 是否分段发送消息
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

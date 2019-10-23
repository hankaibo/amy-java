package cn.mypandora.springboot.config.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * MyHandler
 *
 * @author hankaibo
 * @date 2019/10/22
 */
public class MyHandler extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("=====接受到的数据" + payload);
        session.sendMessage(new TextMessage("服务器返回收到的信息," + payload));
    }
}

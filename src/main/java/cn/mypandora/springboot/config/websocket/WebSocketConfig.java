package cn.mypandora.springboot.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocketConfig
 * <p>
 * EnableWebSocketMessageBroker 表示开启 Stomp WebSocket 服务，也就是可以在 controller 中使用 @MessageMapping 等。
 *
 * @author hankaibo
 * @date 2019/10/22
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // topic 用作广播事件，queue 用作点播事件。
        registry.enableSimpleBroker("/topic", "/queue");
        // 前端访问的后台接口的前缀，为了与其它 http 接口统一，定义为 "/api/v1/messages"了。
        registry.setApplicationDestinationPrefixes("/api/v1/messages");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 前端进行 WebSocket 连接的地址，这里设置为 "/ws"；
        // 开启跨域支持，真实环境中请使用一个具体的地址而非通配符；
        // 开启SockJS 回退机制，即如果前端浏览器版本过低不支持 WebSocket 时，通过 SockJS 进行模拟。
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }

    // @Override
    // public void configureClientInboundChannel(ChannelRegistration registration) {
    // // 添加拦截器，处理客户端发来的请求
    // registration.interceptors(new WebSocketHandleInterceptor());
    // }

}

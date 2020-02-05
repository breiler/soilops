package com.tietoevry.soilops.config;

import com.tietoevry.soilops.eventbus.EventBusWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

    private final EventBusWebSocketHandler eventBusSocketHandler;

    public WebsocketConfig(EventBusWebSocketHandler eventBusSocketHandler) {
        this.eventBusSocketHandler = eventBusSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(eventBusSocketHandler, EventBusWebSocketHandler.CHANNEL_NAME)
                .setAllowedOrigins("*");

        registry.addHandler(eventBusSocketHandler, EventBusWebSocketHandler.CHANNEL_NAME_SOCKJS)
                .setAllowedOrigins("*")
                .withSockJS();
    }

}
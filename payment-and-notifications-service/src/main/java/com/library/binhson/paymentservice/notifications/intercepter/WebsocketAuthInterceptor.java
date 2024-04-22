package com.library.binhson.paymentservice.notifications.intercepter;

import com.library.binhson.paymentservice.service.keycloakservice.AuthService;
import com.library.binhson.paymentservice.notifications.sessionmanagerment.WebsocketUserSessionStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebsocketAuthInterceptor implements ChannelInterceptor {
    private final AuthService authService;
    private final WebsocketUserSessionStore sessionStore;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor= MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        assert accessor != null;
        String sessionId= (String) accessor.getMessageHeaders().get("simpSessionId");
        if(StompCommand.CONNECT==accessor.getCommand()){
            String jwtToken= (String) accessor.getFirstNativeHeader("Authorization");
            String userId=authService.verifyJwt(jwtToken);
            log.info("User id: "+ userId);
            sessionStore.add(sessionId,userId);
        }else if(StompCommand.DISCONNECT==accessor.getCommand()){
            sessionStore.removeBySession(sessionId);
        }
        return message;
    }
}

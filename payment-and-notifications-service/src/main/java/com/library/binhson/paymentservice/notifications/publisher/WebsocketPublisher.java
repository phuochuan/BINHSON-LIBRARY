package com.library.binhson.paymentservice.notifications.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.binhson.paymentservice.notifications.sessionmanagerment.WebsocketUserSessionStore;
import com.library.binhson.paymentservice.entity.Event;
import com.library.binhson.paymentservice.entity.Notifications;
import com.library.binhson.paymentservice.repository.EventRepository;
import com.library.binhson.paymentservice.repository.NotificationRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebsocketPublisher {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationRepository notificationRepository;
    private final EventRepository eventRepository;
    private final WebsocketUserSessionStore websocketUserSessionStore;
    private final ObjectMapper objectMapper;

    public void publish(Long eventId){
        Event event=eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        List<Notifications> notifications=notificationRepository.findByEvent(event);
        try {
            Map<String, String> sessionIds =websocketUserSessionStore.getDataMap();
            for (Notifications notf : notifications) {
                try {
//                    simpMessagingTemplate.convertAndSendToUser(ssId,
//                            "/queue/notification",
//                            "Huan dep trai",
//                            toMessageHeaders(ssId)
//                    );
                    var ssId=websocketUserSessionStore.getSessionIds(Collections.singletonList(notf.getUser().getId())).get(0);
                    simpMessagingTemplate.convertAndSend("/user/"+ssId+"/queue/notification", notf.getContent(), toMessageHeaders(ssId));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }catch(Exception ex1){
                log.error(ex1.getMessage());
            }
    }

    private MessageHeaders toMessageHeaders(String ssId) {
        SimpMessageHeaderAccessor accessor=SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        accessor.setSessionId(ssId);
        accessor.setLeaveMutable(true);
        return accessor.getMessageHeaders();
    }


}

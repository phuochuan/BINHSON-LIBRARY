package com.library.binhson.paymentservice.service;

import com.library.binhson.paymentservice.dto.CreatingNotificationRequest;
import com.library.binhson.paymentservice.dto.NotificationDto;
import com.library.binhson.paymentservice.entity.Event;
import com.library.binhson.paymentservice.entity.Notifications;
import com.library.binhson.paymentservice.entity.User;
import com.library.binhson.paymentservice.notifications.publisher.WebsocketPublisher;
import com.library.binhson.paymentservice.repository.CustomerRepository;
import com.library.binhson.paymentservice.repository.EventRepository;
import com.library.binhson.paymentservice.repository.NotificationRepository;
import com.library.binhson.paymentservice.util.DtoPage;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

import static com.library.binhson.paymentservice.util.AuthUtil.getUsername;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;
    private final EventRepository eventRepository;
    private final WebsocketPublisher websocketPublisher;
    private final CustomerRepository userRepository;

    public List<NotificationDto> get(int offset, int limit) {
        List<NotificationDto> notificationDtos = getAllDto();
        var page = new DtoPage(new ArrayList<>(getAllDto()));
        return page.getPage(limit, offset).stream().map(o -> (NotificationDto) o).toList();
    }

    private List<NotificationDto> getAllDto() {
        String username = getUsername();
        List<Notifications> notifications = getAll(username);
        return notifications.stream().map(nt -> {
            var ntd = modelMapper.map(nt, NotificationDto.class);
            ntd.setImgUrl(nt.getEvent().getUrlImg());
            return ntd;
        }).toList();
    }

    //detheo user
    @Cacheable(value = "notifications", key = "#username")
    public List<Notifications> getAll(String username) {
        return notificationRepository.findByUsername(username);
    }

    public void pushNotifications(CreatingNotificationRequest request) {
        var event = Event.builder()
                .eventType(request.eventType)
                .urlImg(request.getImgUrl())
                .description(request.getEventDescription())
                .dateAt(DateTime.now())
                .build();
        event = eventRepository.save(event);
        List<User> users = userRepository.findAllById(request.getUserIds());
        for (User user : users) {
            var noti = Notifications.builder()
                    .user(user)
                    .content(
                            (request.getFirstContent() + " " + request.getParameter() + " " + request.getLastContent()).trim()
                    )
                    .event(event)
                    .build();
            notificationRepository.save(noti);
        }
        websocketPublisher.publish(event.getId());

    }
}

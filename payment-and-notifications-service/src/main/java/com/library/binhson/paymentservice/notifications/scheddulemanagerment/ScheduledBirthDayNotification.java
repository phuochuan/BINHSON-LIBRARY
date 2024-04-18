package com.library.binhson.paymentservice.notifications.scheddulemanagerment;

import com.library.binhson.paymentservice.entity.Event;
import com.library.binhson.paymentservice.entity.EventType;
import com.library.binhson.paymentservice.entity.Notifications;
import com.library.binhson.paymentservice.entity.User;
import com.library.binhson.paymentservice.notifications.publisher.WebsocketPublisher;
import com.library.binhson.paymentservice.repository.CustomerRepository;
import com.library.binhson.paymentservice.repository.EventRepository;
import com.library.binhson.paymentservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledBirthDayNotification {
    private final WebsocketPublisher websocketPublisher;
    private final CustomerRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final EventRepository eventRepository;

    @Scheduled(cron = "0 0 8 * * ?")
    void sendWishMessage() {
        String birthDateMessage = "\"Happy Birthday! \uD83C\uDF89\uD83C\uDF82\\n\\n\" +\n" +
                "    \"Dear [User],\\n\\n\" +\n" +
                "    \"We hope your special day is filled with joy, laughter, and lots of wonderful surprises.\\n\" +\n" +
                "    \"May this year bring you success, happiness, and all the good things you deserve.\\n\\n\" +\n" +
                "    \"Best wishes,\\n\" +\n";
        DateTime currentDate = DateTime.now();
        List<User> users = userRepository.findOnBirthDate(currentDate.dayOfMonth().get(), currentDate.monthOfYear().get());
        Event birthDayEvent = Event
                .builder()
                .dateAt(new DateTime())
                .description("Event is make to give a wish to member on library.")
                .eventType(EventType.BIRTHDAY)
                .urlImg("")
                .build();
        birthDayEvent = eventRepository.save(birthDayEvent);

        for (User user : users) {
            Notifications notifications
                    = Notifications.builder()
                    .event(birthDayEvent)
                    .content(birthDateMessage + "    \"" + user.getUsername() + "\"")
                    .user(user)
                    .build();
            notificationRepository.save(notifications);
        }
        websocketPublisher.publish(birthDayEvent.getId());
    }

}
package com.library.binhson.paymentservice.notifications.scheddulemanagerment;

import com.library.binhson.paymentservice.entity.BorrowingSession;
import com.library.binhson.paymentservice.entity.Event;
import com.library.binhson.paymentservice.entity.EventType;
import com.library.binhson.paymentservice.entity.Notifications;
import com.library.binhson.paymentservice.notifications.publisher.WebsocketPublisher;
import com.library.binhson.paymentservice.repository.BorrowingSessionRepository;
import com.library.binhson.paymentservice.repository.EventRepository;
import com.library.binhson.paymentservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
@Slf4j
public class ScheduledBookBorrowingNotification {
    private final WebsocketPublisher websocketPublisher;
    private final NotificationRepository notificationRepository;
    private final EventRepository eventRepository;
    private final BorrowingSessionRepository borrowingSessionRepository;

    @Scheduled(cron = "0 0 9 * * ?")
    void remindDue() {
        var remindMessage = "    \"Dear Library Member,\\n\\n\" +\n" +
                "    \"This is a friendly reminder to return any library books you have borrowed on time. Returning books promptly ensures fair access to library resources for all members and helps us maintain an efficient borrowing system.\\n\\n\" +\n" +
                "    \"Please take a moment to check the due dates for any books you have borrowed. If you have any items due soon, we kindly ask that you return them by their due dates to avoid overdue fines and to allow other members to borrow them.\\n\\n\" +\n" +
                "    \"If you need assistance with renewing your books or have any questions about your borrowing privileges, feel free to reach out to our library staff.\\n\\n\" +\n" +
                "    \"Thank you for your cooperation in keeping our library resources available to all members.\\n\\n\" +\n" +
                "    \"Best regards,\\n\" +\n";
        try {
            List<BorrowingSession> goingEndDateSessions = borrowingSessionRepository.findToDonNotCompleteAndGoingEndDate();
            assert Objects.nonNull(goingEndDateSessions) && !goingEndDateSessions.isEmpty();
            Event event = Event
                    .builder()
                    .eventType(EventType.BOOK_DUE_REMINDER)
                    .description("remind members is patron return book for library on time.")
                    .urlImg("")
                    .dateAt(DateTime.now())
                    .build();
            event = eventRepository.save(event);
            for (BorrowingSession bs : goingEndDateSessions) {
                try {
                    Notifications notifications = Notifications.builder()
                            .content(remindMessage + "    \"" + bs.getMember().getUsername() + "\";\n")
                            .event(event)
                            .user(bs.getMember())
                            .build();
                    notificationRepository.save(notifications);
                } catch (Exception ignored) {

                }
            }
            websocketPublisher.publish(event.getId());
        } catch (Exception ignored) {
        }

    }


    @Scheduled(cron = "0 15 9 * * ?")
    void notifyOverdue() {
        String message =
                "We hope this message finds you well. We're writing to inform you that the book(s) are currently overdue:\n\n" +
                        "Please return these books as soon as possible to avoid further overdue fines and to allow other members to access them.\n\n" +
                        "If you have already returned the books or have any questions about your account, please contact our library staff.\n\n" +
                        "Thank you for your attention to this matter.\n\n" +
                        "Best regards,\n" +
                        "Binh Son Library";
        try {
            List<BorrowingSession> goingEndDateSessions = borrowingSessionRepository.findByOverdue();
            assert goingEndDateSessions != null && !goingEndDateSessions.isEmpty();
            Event event = Event
                    .builder()
                    .eventType(EventType.OVERDUE_BOOK_NOTICE)
                    .description("notify members is patron return book for library as soon as.")
                    .urlImg("")
                    .dateAt(DateTime.now())
                    .build();
            event = eventRepository.save(event);
            for (BorrowingSession bs : goingEndDateSessions) {
                try {
                    Notifications notifications = Notifications.builder()
                            .content("Dear " + bs.getMember() + ",\n" + message)
                            .event(event)
                            .user(bs.getMember())
                            .build();
                    notificationRepository.save(notifications);
                } catch (Exception ignored) {

                }
            }
            websocketPublisher.publish(event.getId());
        } catch (Exception ignored) {
        }
    }
    //stop automatic notifications.
}

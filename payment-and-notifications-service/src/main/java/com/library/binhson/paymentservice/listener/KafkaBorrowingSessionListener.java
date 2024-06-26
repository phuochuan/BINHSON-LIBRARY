package com.library.binhson.paymentservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.library.binhson.paymentservice.entity.BorrowingSession;
import com.library.binhson.paymentservice.repository.BorrowingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class KafkaBorrowingSessionListener {
    private final BorrowingSessionRepository borrowingSessionRepository;
    @KafkaListener(id="payment_and_notification_service_borrowing", topics = "borrowing")
    public void listenerBorrowingAction(String jsonPayload) throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        var ss=objectMapper.readValue(jsonPayload, BorrowingSession.class);
        borrowingSessionRepository.save(ss);
    }

    @KafkaListener(id="payment_and_notification_service_end_borrowing", topics = "end_borrowing")
    public void listenerEndBorrowingAction(String jsonPayload) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JodaModule());
            Long ssId = objectMapper.readValue(jsonPayload, Long.class);
            var bs = borrowingSessionRepository.findById(ssId).orElseThrow();
            bs.setCompleted(true);
            borrowingSessionRepository.save(bs);
        }catch (Exception ignored){
        }
    }
}

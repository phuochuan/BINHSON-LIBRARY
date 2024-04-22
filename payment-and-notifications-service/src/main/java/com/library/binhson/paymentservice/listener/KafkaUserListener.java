package com.library.binhson.paymentservice.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.library.binhson.paymentservice.entity.User;
import com.library.binhson.paymentservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
@RequiredArgsConstructor
@Component
public class KafkaUserListener {
    private final CustomerRepository userRepository;
    @KafkaListener(id="payment_and_notification_service_member", topics = "Member")
    public void listenerRegistrationAction(String jsonPayload) throws IOException {
        save(jsonPayload);
    }

    private void save(String jsonPayload) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        User member=objectMapper.readValue(jsonPayload, User.class);
        userRepository.save(member);
    }

    @KafkaListener(id="payment_and_notification_service_librarian", topics = "Librarian")
    public void listenerCreatingLibrarianAction(String jsonPayload) throws IOException{
        save(jsonPayload);

    }
}

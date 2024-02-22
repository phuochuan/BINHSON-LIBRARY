package com.library.binhson.userservice.service.third_party_system.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserKafkaSendToBrokerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public void sendToTopic(String topic, Object value){
        try {
            kafkaTemplate.send(topic, value);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("");
        }
    }

}

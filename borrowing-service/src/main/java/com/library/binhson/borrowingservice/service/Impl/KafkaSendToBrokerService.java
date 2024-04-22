package com.library.binhson.borrowingservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaSendToBrokerService {
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

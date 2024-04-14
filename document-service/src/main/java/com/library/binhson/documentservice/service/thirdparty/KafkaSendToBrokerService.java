package com.library.binhson.documentservice.service.thirdparty;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaSendToBrokerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    public void sendToTopic(String topic, Object value){
        try {
            log.info("Topic : "+ topic);
            kafkaTemplate.send(topic, value);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("");
        }
    }
}

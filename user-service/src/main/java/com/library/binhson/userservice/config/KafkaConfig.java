package com.library.binhson.userservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic newMemberTopic(){
        return new NewTopic("Member", 3, (short) 1);
    }
    @Bean
    public NewTopic newLibrarianTopic(){
        return new NewTopic("Librarian", 3, (short) 1);
    }


}

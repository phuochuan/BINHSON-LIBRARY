package com.library.binhson.borrowingservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.library.binhson.borrowingservice.config.CustomSerializer.CustomDateSerializer;
import com.library.binhson.borrowingservice.config.CustomSerializer.CustomDateTimeSerializer;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class ApplicationConfig {
    @Bean
    ModelMapper modelMapper(){return  new ModelMapper();}
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(DateTime.class, new CustomDateTimeSerializer("dd MM yyyy"));
        module.addSerializer(Date.class,new CustomDateSerializer("dd MM yyyy"));
        objectMapper.registerModule(module );
        return objectMapper;
    }

}

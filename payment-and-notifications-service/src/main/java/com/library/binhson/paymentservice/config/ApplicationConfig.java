package com.library.binhson.paymentservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public ObjectMapper objectMapper(){return new ObjectMapper();}

    @Bean
    public ModelMapper modelMapper(){ return new ModelMapper();}
}

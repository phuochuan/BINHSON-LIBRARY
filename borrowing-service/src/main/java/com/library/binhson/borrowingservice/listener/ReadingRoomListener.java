package com.library.binhson.borrowingservice.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.library.binhson.borrowingservice.entity.Book;
import com.library.binhson.borrowingservice.entity.ReadingRoom;
import com.library.binhson.borrowingservice.repository.ReadingRoomRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
@AllArgsConstructor
@Slf4j
public class ReadingRoomListener {
    private final ReadingRoomRepository readingRoomRepository;
    @KafkaListener(id = "brrowing-service_new-room", topics = "new-room")
    public void listenNewBook(String jsonPayload){
        try {
            ReadingRoom room=convertToRoom(jsonPayload);
            readingRoomRepository.save(room);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @KafkaListener(id = "borrowing-service_delete-room", topics = "deleted-book")
    public void listenUpdateBook(String jsonPayload){
        try{

            ReadingRoom room=convertToRoom(jsonPayload);
            if(readingRoomRepository.existsById(room.getId()))
                readingRoomRepository.deleteById(room.getId());


        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private ReadingRoom convertToRoom(String jsonPayload) throws JsonProcessingException {
        var objectMapper=getObjectMapper();
        return objectMapper.readValue(jsonPayload, ReadingRoom.class);
    }


}

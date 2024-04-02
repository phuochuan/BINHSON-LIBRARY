package com.library.binhson.borrowingservice.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.library.binhson.borrowingservice.entity.Book;
import com.library.binhson.borrowingservice.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@AllArgsConstructor
public class BookListener {
    private final BookRepository bookRepository;
    @KafkaListener(id = "brrowing-service_new-book", topics = "new-book")
    public void listenNewBook(String jsonPayload){
        try {
            var objectMapper =getObjectMapper();
            Book book= convertToBook(jsonPayload);
            bookRepository.save(book);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @KafkaListener(id = "borrowing-service_updated-book", topics = "updated-book")
    public void listenUpdateBook(String jsonPayload){
        try{

            Book updateBook= convertToBook(jsonPayload);
            if(bookRepository.existsById(updateBook.getId())){
                Book book=bookRepository.findById(updateBook.getId()).orElseThrow();
                book.setName(updateBook.getName());
                book.setDegree(updateBook.getDegree());
                bookRepository.save(book);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private Book convertToBook(String jsonPayload) throws JsonProcessingException {
        var objectMapper =getObjectMapper();
        return objectMapper.readValue(jsonPayload, Book.class);
    }

    @KafkaListener(id = "borrowing-service_deleted-book", topics = "deleted-book")
    public void listenDeleteBook(String jsonPayload){
        try{
            Book deleteBook= convertToBook(jsonPayload);
            if(bookRepository.existsById(deleteBook.getId()))
                bookRepository.deleteById(deleteBook.getId());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}

package com.library.binhson.borrowingservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.library.binhson.borrowingservice.entity.Librarian;
import com.library.binhson.borrowingservice.entity.Member;
import com.library.binhson.borrowingservice.repository.LibrarianRepository;
import com.library.binhson.borrowingservice.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;
@AllArgsConstructor
public class UserListener {
    private final MemberRepository memberRepository;
    private final LibrarianRepository librarianRepository;
    @KafkaListener(id="document_service_member", topics = "Member")
    public void listenerRegistrationAction(String jsonPayload) throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Member member=objectMapper.readValue(jsonPayload, Member.class);
        memberRepository.save(member);
    }

    @KafkaListener(id="document_service_librarian", topics = "Librarian")
    public void listenerCreatingLibrarianAction(String jsonPayload) throws IOException{
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Librarian librarian=objectMapper.readValue(jsonPayload, Librarian.class);
        librarianRepository.save(librarian);
    }
}

package com.library.binhson.documentservice.service.thirdparty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.library.binhson.documentservice.entity.Librarian;
import com.library.binhson.documentservice.entity.Member;
import com.library.binhson.documentservice.repository.LibrarianRepository;
import com.library.binhson.documentservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaListenerService {
    private final MemberRepository memberRepository;
    private final LibrarianRepository librarianRepository;
    @KafkaListener(id="document_service_member", topics = "Member")
    public void listenerRegistrationAction(String jsonPayload) throws IOException{
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Member member=objectMapper.readValue(jsonPayload, Member.class);
        memberRepository.save(member);
        log.info("Listener a registration action from user service");
    }

    @KafkaListener(id="document_service_librarian", topics = "Librarian")
    public void listenerCreatingLibrarianAction(String jsonPayload) throws IOException{
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Librarian librarian=objectMapper.readValue(jsonPayload, Librarian.class);
        librarianRepository.save(librarian);
        log.info("Listener a creating librarian action from user service");
    }
}

package com.library.binhson.borrowingservice.service.Impl;

import com.library.binhson.borrowingservice.dto.DataPage;
import com.library.binhson.borrowingservice.dto.OnReadingRoomRequest;
import com.library.binhson.borrowingservice.dto.ReadingSessionDto;
import com.library.binhson.borrowingservice.entity.Librarian;
import com.library.binhson.borrowingservice.entity.ReadSession;
import com.library.binhson.borrowingservice.repository.*;
import com.library.binhson.borrowingservice.service.IReadingSessionService;
import com.library.binhson.borrowingservice.utils.AuthUtils;
import jakarta.ws.rs.BadRequestException;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@AllArgsConstructor
public class ReadingSessionServiceImpl implements IReadingSessionService {
    private final ReadingRoomRepository roomRepository;
    private final ReadSessionRepository sessionRepository;
    private final MemberRepository memberRepository;
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    @Override
    public DataPage get(int offset, int limit) {
        if(offset<=0) offset=1;
        if(limit<=0) limit=100;
        return new DataPage(limit, offset, Arrays.asList(getDTOs()));
    }

    @Override
    public ReadingSessionDto get(String id) {
        return findDTOById(Long.parseLong(id));
    }

    @Override
    public DataPage search(HashMap<String, String> map) {
        String key=map.get("keyword");
        DateTime date=DateTime.parse(map.get("date"));
        List<ReadingSessionDto> dtos=getDTOs();
        dtos=dtos.stream()//
                .filter(dto->{
                    boolean isOk=dto.getMember().getUsername().contains(key)
                            || dto.getReadingRoom().getName().contains(key);
                    if(Objects.nonNull(date))
                        isOk=isOk || date.toDate().equals(dto.getTimeOfStarting().toDate());
                    return isOk;
                }).toList();
        return new DataPage(dtos.size(), 1, new ArrayList<>(dtos));
    }

    @Override
    @CacheEvict(allEntries = true, value = "reading-session")
    public void deleteById(String id) {
        try {
            sessionRepository.deleteById(Long.parseLong(id));
        }catch (Exception ex) {
            throw new BadRequestException("Id is incorrect");
        }
    }

    @Override
    @CacheEvict(allEntries = true, value = "reading-session")
    public ReadingSessionDto createASession(OnReadingRoomRequest onReading) {
        var room=roomRepository.findById(onReading.getRoomId()).orElseThrow(()->new BadRequestException("room id is incorrect."));
        var member=memberRepository.findById(onReading.getMemberId()).orElseThrow(()-> new BadRequestException("MMember id is incorrect."));
        ReadSession session=ReadSession.builder()
                .readingRoom(room)
                .member(member)
                .librarian(getCurrentLibrarian())
                .timeOfStarting(new DateTime())
                .build();
        session=sessionRepository.save(session);
        return modelMapper.map(session, ReadingSessionDto.class) ;
    }

    private Librarian getCurrentLibrarian() {
        String username= AuthUtils.getUsername();
        return (Librarian) personRepository.findByUsername(username).orElseThrow();

    }


    @Cacheable(value="reading-session")
    private List<ReadSession> getAll(){
        return sessionRepository.findAll();
    }

    private List<ReadingSessionDto> getDTOs(){
        return getAll().stream()//
                .map(readSession -> modelMapper.map(readSession, ReadingSessionDto.class))
                .toList();
    }

    private ReadSession findById(Long id){
        return getAll().stream().filter(rs->rs.equals(id)).findFirst().orElseThrow(()->new BadRequestException("id is incorrect."));
    }
    private ReadingSessionDto findDTOById(Long id){
        return modelMapper.map(findById(id), ReadingSessionDto.class);
    }

}

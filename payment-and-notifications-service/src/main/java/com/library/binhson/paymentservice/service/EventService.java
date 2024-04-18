package com.library.binhson.paymentservice.service;

import com.library.binhson.paymentservice.dto.EventDto;
import com.library.binhson.paymentservice.repository.EventRepository;
import com.library.binhson.paymentservice.util.DtoPage;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    @Cacheable(value="events_page", key="#offset+''+#limit")
    public List<EventDto> get(int offset, int limit) {
        List<EventDto> eventDtos=getAllDto();
        DtoPage dtoPage=new DtoPage(new ArrayList<>(eventDtos));
        return dtoPage.getPage(limit,offset).stream().map(o -> (EventDto)o).toList();

    }

    private List<EventDto> getAllDto() {
        return eventRepository.findAll().stream().map(event -> modelMapper.map(event,EventDto.class))
                .toList();
    }

    @Cacheable(value="event_by_id", key="#id")
    public EventDto find(Long id) {
        var event=eventRepository.findById(id).orElseThrow(()->new BadRequestException("Not found"));
        return modelMapper.map(event,EventDto.class);
    }
}

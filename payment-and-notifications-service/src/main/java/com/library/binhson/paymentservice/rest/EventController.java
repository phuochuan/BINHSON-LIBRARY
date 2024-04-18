package com.library.binhson.paymentservice.rest;

import com.library.binhson.paymentservice.dto.EventDto;
import com.library.binhson.paymentservice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.library.binhson.paymentservice.util.ResponseUtil.response;

@RestController
@RequestMapping("/api/v1/payment-and-notification-service/notifications/event")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    @GetMapping({"","/"})
    @Secured("hasAnyRoles('ADMIN,LIBRARIAN')")
    ResponseEntity<?> get(@RequestParam("offset") int offset,
                          @RequestParam("limit") int limit){
        List<EventDto> eventDtos=eventService.get(offset,limit);
        return response(eventDtos, eventDtos.size());
    }
    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable("id") Long id){
        EventDto eventDto=eventService.find(id);
        return response(eventDto, 1);
    }

}

package com.library.binhson.paymentservice.rest;

import com.library.binhson.paymentservice.dto.CreatingNotificationRequest;
import com.library.binhson.paymentservice.dto.NotificationDto;
import com.library.binhson.paymentservice.service.NotificationService;
import com.library.binhson.paymentservice.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController()
@RequestMapping("/api/v1/payment-and-notification-service/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    @GetMapping({"","/"})
    ResponseEntity<?> get(@RequestParam("offset") int offset,
                          @RequestParam("limit") int limit){
        List<NotificationDto> notificationDtos=notificationService.get(offset,limit);
        return response(notificationDtos);
    }
    @PostMapping({"","/"})
    void pushNotifications(@RequestBody CreatingNotificationRequest request){
        notificationService.pushNotifications(request);
    }
    private ResponseEntity<?> response(List<NotificationDto> notificationDtos) {
        notificationDtos=notificationDtos.stream().peek(this::setHatoaes).toList();
        return ResponseUtil.response(notificationDtos, notificationDtos.size());
    }

    private void setHatoaes(NotificationDto ntf) {
        Link link=linkTo(methodOn(EventController.class).get(ntf.getEventId())).withRel("event");
        ntf.add(link);
    }

}

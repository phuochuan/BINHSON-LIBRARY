package com.library.binhson.borrowingservice.rest;

import com.library.binhson.borrowingservice.dto.BorrowingSessionRequest;
import com.library.binhson.borrowingservice.dto.RareBookReadingDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/borrowing-service/borrowing")
public interface IBorrowingSessionController extends IBaseController{
    @Secured("hasRole('ROLE_MEMBER')")
    @PostMapping("/reservation")
    ResponseEntity<?> reservation(@RequestBody BorrowingSessionRequest session);


    @Secured("hasRoles({'ROLE_LIBRARIAN','ROLE_ADMIN', 'ROLE_MEMBER')")
    @PostMapping("/renew/{id}")
    ResponseEntity<?> renew(@PathVariable("id") Long borrowingSession);
}

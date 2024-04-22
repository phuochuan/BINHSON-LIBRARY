package com.library.binhson.borrowingservice.rest;

import com.library.binhson.borrowingservice.dto.BorrowingSessionRequest;
import com.library.binhson.borrowingservice.dto.RareBookReadingDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/borrowing-service/borrowing")
public interface IBorrowingSessionController extends IBaseController{
    @Secured("hasRole('ROLE_LIBRARIAN')")
    @PostMapping({""})
    ResponseEntity<?> borrow(@RequestBody BorrowingSessionRequest session);

    @Secured("hasRole('ROLE_MEMBER')")
    @PostMapping({"/reservation"})
    ResponseEntity<?> reservation(@RequestBody BorrowingSessionRequest request);

    @Secured("hasRoles({'ROLE_LIBRARIAN','ROLE_ADMIN', 'ROLE_MEMBER')")
    @PostMapping("/renewal/{id}")
    ResponseEntity<?> renew(@PathVariable("id") Long id);

    @Secured("hasRoles({'ROLE_LIBRARIAN','ROLE_ADMIN')")
    @PostMapping("/completion/{id}")
    void completeSession(@PathVariable("id") Long id);
}

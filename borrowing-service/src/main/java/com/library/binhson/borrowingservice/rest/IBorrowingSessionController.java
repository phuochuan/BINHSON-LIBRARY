package com.library.binhson.borrowingservice.rest;

import com.library.binhson.borrowingservice.dto.RareBookReadingDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/borrowing-service/borrowing")
public interface IBorrowingSessionController extends IBaseController{
    @Secured("hasRole('ROLE_MEMBER')")
    @PostMapping("/claim/{id}")
    ResponseEntity<?> claimBorrowing(@PathVariable("id") String bookId);

    @Secured("hasRoles({'ROLE_LIBRARIAN','ROLE_ADMIN')")
    @PostMapping("/reading")
    ResponseEntity<?> createBorrowingSession(@RequestBody RareBookReadingDto DTO);

    @Secured("hasRole('ROLE_MEMBER')")
    @PostMapping("/renew/{id}")
    ResponseEntity<?> renewFromMember(@PathVariable("id") Long borrowingSession);

    @Secured("hasRoles({'ROLE_LIBRARIAN','ROLE_ADMIN')")
    @PostMapping("/renew/{id}")
    ResponseEntity<?> renew(@PathVariable("id") Long borrowingSession);
}

package com.library.binhson.borrowingservice.rest;

import com.library.binhson.borrowingservice.dto.RareBookReadingDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/borrowing-service/rare-book")
public interface IReadingOfRareBookController extends IBaseController {
    @Secured("hasRole('ROLE_MEMBER')")
    @PostMapping("/claim/{id}")
     ResponseEntity<?> claimReadingRareBook(@PathVariable("id") String bookId);

    @Secured("hasRoles({'ROLE_LIBRARIAN','ROLE_ADMIN')")
    @PostMapping("/reading")
     ResponseEntity<?> createReadingSessionOfRareBook(@RequestBody RareBookReadingDto DTO);
}

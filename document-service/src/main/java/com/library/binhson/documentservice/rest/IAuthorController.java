package com.library.binhson.documentservice.rest;

import com.library.binhson.documentservice.dto.request.RequestAuthorDto;
import com.library.binhson.documentservice.dto.request.SetBookForAuthorRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/document-service/authors")
public interface IAuthorController extends IBaseController{

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    ResponseEntity<?> addNewAuthor(@RequestBody RequestAuthorDto requestAuthorDto);

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    ResponseEntity<?> updateAuthor(@RequestBody RequestAuthorDto requestAuthorDto,@PathVariable(value = "id",required = true) Long id);

    @PostMapping("/{idAuthor}/book")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    ResponseEntity<?> setBooksAuthor(@RequestBody SetBookForAuthorRequest setBookRequest, @PathVariable(value = "id",required = true) String id);





}

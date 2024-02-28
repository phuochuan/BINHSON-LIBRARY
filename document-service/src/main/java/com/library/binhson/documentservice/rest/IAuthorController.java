package com.library.binhson.documentservice.rest;

import com.library.binhson.documentservice.dto.Request.RequestAuthorDto;
import com.library.binhson.documentservice.dto.Request.SetBookForAuthorRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/document-service/authors")
public interface IAuthorController extends IBaseController{

    @PostMapping("")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> addNewAuthor(@RequestBody RequestAuthorDto requestAuthorDto);

    @PatchMapping("/{id}")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> updateAuthor(@RequestBody RequestAuthorDto requestAuthorDto,@PathVariable(value = "id",required = true) Long id);

    @PostMapping("/{idAuthor}/book")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> setBooksAuthor(@RequestBody SetBookForAuthorRequest setBookRequest, @PathVariable(value = "id",required = true) Long id);





}

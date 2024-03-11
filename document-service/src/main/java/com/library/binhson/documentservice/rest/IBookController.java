package com.library.binhson.documentservice.rest;

import com.library.binhson.documentservice.dto.request.RequestBookDto;
import com.library.binhson.documentservice.dto.request.RequestUpdateBookDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/document-service/books")
public interface IBookController extends IBaseController{

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    ResponseEntity<?> addBook(@RequestBody RequestBookDto bookDto);

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    ResponseEntity<?> updateBook(@RequestBody RequestUpdateBookDto bookDto, @PathVariable("id") String id);

    //Ebook book:
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @PatchMapping("/ebook/{id}")
    void updateEBook(@PathVariable("id") String id, @RequestParam MultipartFile ebook);
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @GetMapping("/ebook/{id}")
    ResponseEntity<?> getEBook(@PathVariable("id") String id);

    //Ebook :










}

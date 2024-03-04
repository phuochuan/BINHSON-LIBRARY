package com.library.binhson.documentservice.rest;

import com.library.binhson.documentservice.dto.request.RequestBookDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/document-service/books")
public interface IBookController extends IBaseController{


    @PostMapping("")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> addBook(@RequestBody RequestBookDto bookDto);

    @PatchMapping("/{id}")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> updateBook(@RequestBody RequestBookDto bookDto, @PathVariable("id") String id);

    //Ebook book:
    @PostMapping("/ebooks/{id}/upload")
    ResponseEntity<?> updateEBook(@PathVariable("id") String id, @RequestParam MultipartFile ebook);
    @GetMapping("/ebooks/{id}")
    ResponseEntity<?> updateEBook(@PathVariable("id") String id);










}

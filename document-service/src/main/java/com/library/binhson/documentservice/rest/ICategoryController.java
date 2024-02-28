package com.library.binhson.documentservice.rest;

import com.library.binhson.documentservice.dto.Request.RequestCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/document-service/categories")
public interface ICategoryController extends  IBaseController{
    @PostMapping("")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> addCategory(@RequestBody RequestCategoryDto categoryDto);

    @PatchMapping("/{id}")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> updateCategory(@RequestBody RequestCategoryDto categoryDto,  @PathVariable("id") Long id);





}

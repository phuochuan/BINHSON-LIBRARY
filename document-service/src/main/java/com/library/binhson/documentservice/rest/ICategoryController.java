package com.library.binhson.documentservice.rest;

import com.library.binhson.documentservice.dto.request.RequestCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/document-service/categories")
public interface ICategoryController extends  IBaseController{
    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    ResponseEntity<?> addCategory(@RequestBody RequestCategoryDto categoryDto);

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    ResponseEntity<?> updateCategory(@RequestBody RequestCategoryDto categoryDto,  @PathVariable("id") Long id);





}

package com.library.binhson.documentservice.rest;

import com.library.binhson.documentservice.dto.request.RequestShelfDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/document-service/shelf")
public interface IShelfController extends IBaseController {
    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN','WAREHOUSE_STAFF')")
    ResponseEntity<?> addNew(@RequestBody RequestShelfDto shelfDto);

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN','WAREHOUSE_STAFF')")
    ResponseEntity<?> update(@RequestBody RequestShelfDto shelfDto,@PathVariable(value = "id",required = true) String id);
}

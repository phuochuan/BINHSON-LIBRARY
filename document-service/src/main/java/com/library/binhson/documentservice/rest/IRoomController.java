package com.library.binhson.documentservice.rest;

import com.library.binhson.documentservice.dto.request.RequestRoomDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/document-service/rooms")

public interface IRoomController extends IBaseController{
    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN','WAREHOUSE_STAFF')")
    ResponseEntity<?> addNew(@RequestBody RequestRoomDto roomDto);

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN','WAREHOUSE_STAFF')")
    ResponseEntity<?> update(@RequestBody RequestRoomDto roomDto,@PathVariable(value = "id",required = true) Integer id);

}

package com.library.binhson.documentservice.rest;

import com.library.binhson.documentservice.dto.request.RequestRoomDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/document-service/rooms")

public interface IRoomController extends IBaseController{
    @PostMapping("")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> addNew(@RequestBody RequestRoomDto roomDto);

    @PatchMapping("/{id}")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> update(@RequestBody RequestRoomDto roomDto,@PathVariable(value = "id",required = true) Integer id);

}

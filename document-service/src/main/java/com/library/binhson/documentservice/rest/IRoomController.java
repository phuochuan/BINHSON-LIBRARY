package com.library.binhson.documentservice.rest;

import com.library.binhson.documentservice.dto.Request.RequestAuthorDto;
import com.library.binhson.documentservice.dto.Request.RequestRoomDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/document-service/rooms")

public interface IRoomController extends IBaseController{
    @PostMapping("")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> addNewAuthor(@RequestBody RequestRoomDto roomDto);

    @PatchMapping("/{id}")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> updateAuthor(@RequestBody RequestRoomDto roomDto,@PathVariable(value = "id",required = true) Long id);

}

package com.library.binhson.documentservice.rest;

import com.library.binhson.documentservice.dto.Request.RequestRoomDto;
import com.library.binhson.documentservice.dto.Request.RequestShelfDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/document-service/shelf")
public interface IShelfController extends IBaseController {
    @PostMapping("")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> addNewAuthor(@RequestBody RequestShelfDto shelfDto);

    @PatchMapping("/{id}")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> updateAuthor(@RequestBody RequestShelfDto shelfDto,@PathVariable(value = "id",required = true) String id);
}

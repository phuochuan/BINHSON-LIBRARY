package com.library.binhson.borrowingservice.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

public interface IBaseController {
    @GetMapping({"","/"})
    public ResponseEntity<?> get(
            @RequestParam(name = "offset") int offset, @RequestParam(name = "limit", defaultValue = "100") int limit
    );

    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable("id") String id);

    @GetMapping("/search")
    ResponseEntity<?> search(@RequestBody HashMap<String, String > map);

    @DeleteMapping("/{id}")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    void delete( @PathVariable("id") String id);
}

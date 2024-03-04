package com.library.binhson.documentservice.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

public interface IBaseController {
    @GetMapping({"", "/"})
    ResponseEntity<?> get(@RequestParam(value = "offset", required = false, defaultValue = "1") Integer offset,
                                    @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit);

    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable("id") String id);

    @GetMapping("/search")
    ResponseEntity<?> search(@RequestParam(value = "offset", required = false) Integer offset,
                             @RequestParam(value = "limit", required = false) Integer limit,
                             @RequestParam(value = "key", required = true) String key,
                             @RequestParam(value = "sort", required = false) Integer sort ,
                             @RequestParam(value = "type", required = false) String type);


    @DeleteMapping("/{id}")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> delete( @PathVariable("id") String id);
}

package com.library.binhson.documentservice.rest;

import com.library.binhson.documentservice.dto.Request.RequestCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

public interface IBaseController {
    @GetMapping({"", "/"})
    ResponseEntity<?> get(@RequestParam(value = "offset", required = false) Integer offset,
                                    @RequestParam(value = "limit", required = false) Integer limit);

    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable("id") String id);

    @GetMapping("/search")
    ResponseEntity<?> search(@RequestParam(value = "offset", required = false) Integer offset,
                                     @RequestParam(value = "limit", required = false) Integer limit,
                                     @RequestParam(value = "key", required = true) String key ,
                             @RequestParam(value = "sort", required = false) Integer sort //sort>0 decrease || sort=0 no and sort<0 increase
                             );


    @DeleteMapping("/{id}")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> deleteCategory( @PathVariable("id") String id);
}

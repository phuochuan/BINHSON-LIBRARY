package com.library.binhson.documentservice.rest.restImpl;

import com.library.binhson.documentservice.dto.request.RequestShelfDto;
import com.library.binhson.documentservice.rest.IShelfController;
import com.library.binhson.documentservice.service.common.IShelfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShelfController implements IShelfController {
    private final IShelfService shelfService;
    @Override
    public ResponseEntity<?> get(Integer offset, Integer limit) {
        return null;
    }

    @Override
    public ResponseEntity<?> get(String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> search(Integer offset, Integer limit, String key, Integer sort, String type) {
        return null;
    }

   

    @Override
    public ResponseEntity<?> delete(String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> addNewAuthor(RequestShelfDto shelfDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateAuthor(RequestShelfDto shelfDto, String id) {
        return null;
    }
}

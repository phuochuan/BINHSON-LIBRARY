package com.library.binhson.documentservice.rest.restImpl;

import com.library.binhson.documentservice.dto.request.RequestBookDto;
import com.library.binhson.documentservice.rest.IBookController;
import com.library.binhson.documentservice.service.common.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController implements IBookController {
    private final IBookService bookService;
    @Override
    public ResponseEntity<?> get(Integer offset, Integer limit) {
        return null;
    }

    @Override
    public ResponseEntity<?> get(String id) {
        return null;
    }


    @Override
    public ResponseEntity<?> delete(String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> addBook(RequestBookDto bookDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateBook(RequestBookDto bookDto, String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateEBook(String id, MultipartFile ebook) {
        return null;
    }

    @Override
    public ResponseEntity<?> getEBook(String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> search(Integer offset, Integer limit, String key, Integer sort, String type) {
        return null;
    }

    public List<Link> hateoas(){

    }
}

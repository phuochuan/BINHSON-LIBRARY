package com.library.binhson.documentservice.rest.restImpl;

import com.library.binhson.documentservice.dto.BookDto;
import com.library.binhson.documentservice.dto.request.RequestBookDto;
import com.library.binhson.documentservice.dto.request.RequestUpdateBookDto;
import com.library.binhson.documentservice.dto.response.BaseResponse;
import com.library.binhson.documentservice.entity.Book;
import com.library.binhson.documentservice.rest.*;
import com.library.binhson.documentservice.service.common.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.library.binhson.documentservice.ultil.AuthMyInfoUtils.isAdminOrLibrarianPermission;
import static com.library.binhson.documentservice.ultil.ResponseUtil.response;
import static com.library.binhson.documentservice.ultil.ResponseUtil.success;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class BookController implements IBookController {
    private final IBookService bookService;
    @Override
    public ResponseEntity<?> get(Integer offset, Integer limit) {
        List<BookDto> bookDtos=bookService.get(offset, limit);
        return generateResponse(bookDtos);
    }

    @Override
    public ResponseEntity<?> get(String id) {
        BookDto bookDto=bookService.getById(id);
        return generateResponse(bookDto);
    }

    @Override
    public ResponseEntity<?> search(Map<String, String> map) {
        List<BookDto> bookDtos=bookService.search(map);
        return generateResponse(bookDtos);
    }


    @Override
    public ResponseEntity<?> delete(String id) {
        bookService.deleteById(id);
        return success("");
    }

    @Override
    public ResponseEntity<?> addBook(RequestBookDto bookDto) {
        BookDto addedBook=bookService.add(bookDto);
        return generateResponse(addedBook);
    }

    @Override
    public ResponseEntity<?> updateBook(RequestUpdateBookDto bookDto, String id) {
        BookDto updatedBook=bookService.update(bookDto, id);
        return generateResponse(updatedBook);
    }

    @Override
    public void updateEBook(String id, MultipartFile ebook) {
        bookService.setFileForEBook(ebook, id);
    }

    @Override
    public ResponseEntity<?> getEBook(String id) {
        byte[] ebook=bookService.getEbook(id);
        try {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(ebook);
        }catch (Exception ex){
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(ebook);

        }
    }



    public List<Link> hateoas(BookDto dto){
        List<Link> links=new ArrayList<>();
        Link selfLink=linkTo(methodOn(IBookController.class).get(dto.getId())).withSelfRel();
        if(Objects.nonNull(dto.getCategoryIds()) && !dto.getCategoryIds().isEmpty()) {
            dto.getCategoryIds().forEach(categoryId->{
                if(Objects.nonNull(categoryId))
                    links.add(linkTo(methodOn(ICategoryController.class).get(categoryId+"")).withRel("category"));
            });
        }
        if(Objects.nonNull(dto.getAuthorIds()) && !dto.getAuthorIds().isEmpty()) {
            dto.getAuthorIds().forEach(authorId->{
                if(Objects.nonNull(authorId))
                    links.add(linkTo(methodOn(IAuthorController.class).get(authorId+"")).withRel("author"));
            });
        }

        if(dto.getType().equalsIgnoreCase("ebook")) {
            Link ebookLink = linkTo(methodOn(IBookController.class).getEBook(dto.getId())).withSelfRel();
            links.add(ebookLink);
        }else if(Objects.nonNull(dto.getType())){
            Link addressLink=linkTo(methodOn(ILocalAddressController.class).get(dto.getLocalAddressId()+"")).withRel("Storage Unit Address");
            links.add(addressLink);
        }
        if(isAdminOrLibrarianPermission()){
            Link addressLink=linkTo(methodOn(IImportInvoiceController.class).get(dto.getImportInvoiceId()+"")).withRel("Import Invoice");
            links.add(addressLink);
        }

        return links;

    }

    public ResponseEntity<?> generateResponse(List<BookDto> dtos){
        dtos=dtos.stream().peek(dto -> dto.add(hateoas(dto))).toList();
        return ResponseEntity.ok(BaseResponse.builder()
                .data(dtos)
                .dataSize(dtos.size())
                .dateAt(new Date())
                .message("Books")
                .build());
    }
    public ResponseEntity<?> generateResponse(BookDto dto){
        dto=dto.add(hateoas(dto));
        return ResponseEntity.ok(BaseResponse.builder()
                .data(dto)
                .dataSize(1)
                .dateAt(new Date())
                .message("Book")
                .build());
    }
}

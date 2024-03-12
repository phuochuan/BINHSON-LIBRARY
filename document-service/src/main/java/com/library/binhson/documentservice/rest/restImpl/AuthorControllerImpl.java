package com.library.binhson.documentservice.rest.restImpl;

import com.library.binhson.documentservice.dto.AuthorDto;
import com.library.binhson.documentservice.dto.request.RequestAuthorDto;
import com.library.binhson.documentservice.dto.request.SetBookForAuthorRequest;
import com.library.binhson.documentservice.dto.response.BaseResponse;
import com.library.binhson.documentservice.entity.Author;
import com.library.binhson.documentservice.rest.IAuthorController;
import com.library.binhson.documentservice.rest.IBaseController;
import com.library.binhson.documentservice.service.common.IAuthorService;
import com.library.binhson.documentservice.ultil.HateoasUtils;
import com.library.binhson.documentservice.ultil.ResponseUtil;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthorControllerImpl implements IAuthorController {
    private final IAuthorService authorService;

    @Override
    public ResponseEntity<?> addNewAuthor(RequestAuthorDto requestAuthorDto) {
        try {
            AuthorDto newAuthor = authorService.addAuthor(requestAuthorDto);
            newAuthor.add(setHateoas( newAuthor.getId() + ""));
            return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.builder()
                    .message("Successfully")
                    .dataSize(1)
                    .dateAt(new Date())
                    .data(newAuthor)
                    .build());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BadRequestException("");
        }
    }


    @Override
    public ResponseEntity<?> get(Integer offset, Integer limit) {
        if (Objects.isNull(offset) || Objects.isNull(limit)) {
            offset = 1;
            limit = 10;
        }
        if (offset <= 0 || limit <= 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.builder()
                    .message("Offset and limit must more taller than 0.")
                    .dataSize(0)
                    .dateAt(new Date())
                    .build());
        List<AuthorDto> authors = authorService.get(offset, limit);
        return generateResponseForListAuthor(authors);
    }

    @Override
    public ResponseEntity<?> get(String id) {
        AuthorDto author=authorService.getById(id);
       return generateResponseForAuthor(author);
    }




    @Override
    public ResponseEntity<?> search(Map<String, String> map) {
        String sortStr=map.get("sort");
        List<AuthorDto> authors = authorService.search((HashMap<String, String>) map);
        Integer sort = null;
        try{
            sort=Integer.parseInt(sortStr);
        }catch (Exception ex){

        }
        if(Objects.nonNull(sort)){
            if(sort>=0)
                Collections.sort(authors, new Comparator<AuthorDto>() {
                    @Override
                    public int compare(AuthorDto o1, AuthorDto o2) {
                        return o1.getDateOfBirth().compareTo(o2.getDateOfBirth());
                        //ascending
                    }
                });
            else{
                Collections.sort(authors, new Comparator<AuthorDto>() {
                    @Override
                    public int compare(AuthorDto o1, AuthorDto o2) {
                        return o2.getDateOfBirth().compareTo(o1.getDateOfBirth());
                        //descending
                    }
                });
            }
        }
        return generateResponseForListAuthor(authors);
    }


    @Override
    public ResponseEntity<?> delete(String id) {
        authorService.deleteById(id);
        return ResponseEntity.ok("");
    }

    private   List<Link> setHateoas( String id ){
        Link selfLink01 = linkTo(methodOn(IAuthorController.class).get(id)).withSelfRel();
        List<Link> links=new ArrayList<>();
        links.add(selfLink01);
        return links;
    }

    private ResponseEntity<?> generateResponseForListAuthor(List<AuthorDto> authors) {
        authors = authors.stream().peek(authorDto -> {
                    authorDto.add(setHateoas( authorDto.getId() + ""));
                })
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.builder()
                .message("Successfully")
                .dataSize(authors.size())
                .dateAt(new Date())
                .data(authors)
                .build());
    }

    private ResponseEntity<?> generateResponseForAuthor(AuthorDto author) {
        author.add(setHateoas(author.getId()+""));
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.builder()
                .message("Successfully")
                .dataSize(1)
                .dateAt(new Date())
                .data(author)
                .build());
    }

    @Override
    public ResponseEntity<?> updateAuthor(RequestAuthorDto requestAuthorDto, Long id) {

        AuthorDto authorDto=authorService.update(requestAuthorDto,id);
        return generateResponseForAuthor(authorDto);
    }

    @Override
    public ResponseEntity<?> setBooksAuthor(SetBookForAuthorRequest setBookRequest, String id) {
        if(Objects.nonNull(setBookRequest.book_ids())) throw new BadRequestException("Ids must non null;");
        authorService.setBooks(setBookRequest, id);
        return ResponseUtil.success("Set books for author successfully.");
    }

}

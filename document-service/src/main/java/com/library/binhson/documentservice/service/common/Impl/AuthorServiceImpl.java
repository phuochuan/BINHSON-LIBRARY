package com.library.binhson.documentservice.service.common.Impl;

import com.library.binhson.documentservice.dto.AuthorDto;
import com.library.binhson.documentservice.dto.request.RequestAuthorDto;
import com.library.binhson.documentservice.dto.request.SetBookForAuthorRequest;
import com.library.binhson.documentservice.entity.Author;
import com.library.binhson.documentservice.entity.Book;
import com.library.binhson.documentservice.repository.AuthorRepository;
import com.library.binhson.documentservice.repository.BookRepository;
import com.library.binhson.documentservice.service.common.IAuthorService;
import com.library.binhson.documentservice.ultil.PageUtilObject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements IAuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<AuthorDto> get(Integer offset, Integer limit) {
        List<Author> authors = getAll();
        log.info("Author Size: " + authors.size());
        var pageUtil = new PageUtilObject(limit, offset, Arrays.asList(authors.toArray()));
        List<Author> authorList = (List<Author>) pageUtil.getData();
        return authorList.stream().map(author -> modelMapper.map(author, AuthorDto.class)).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "authors", allEntries = true)
    public AuthorDto addAuthor(RequestAuthorDto requestAuthorDto) {
        if (!validAuthorDto(requestAuthorDto))
            throw new BadRequestException("");
        var author = Author.builder()
                .name(requestAuthorDto.name())
                .biography(requestAuthorDto.biography())
                .dateOfBirth(requestAuthorDto.date_of_birth())
                .dateOfDie(requestAuthorDto.date_of_die())
                .build();
        var savedAuthor = authorRepository.save(author);
        return modelMapper.map(savedAuthor, AuthorDto.class);
    }

    @Override
    public AuthorDto getById(String id) {
        var authors = getAll();
        var author = authors.stream().filter(au -> au.getId().equals(Long.parseLong(id))).findFirst().get();
        return modelMapper.map(author, AuthorDto.class);
    }

    @Override
    public List<AuthorDto> search(HashMap<String, String> searchMap) {
        int offset = Integer.parseInt(searchMap.get("offset"));
        int limit = Integer.parseInt(searchMap.get("limit"));
        if (offset <= 0 || limit <= 0) {
            offset = 1;
            limit = 10;
        }
        List<Author> authors = getAll();
        String key = searchMap.get("key");
        List<AuthorDto> authorDtos;
        if (Objects.nonNull(key) && !key.isEmpty()) {
            authorDtos = authors.stream().filter(author -> {
                if (author.getName().toLowerCase().contains(key.toLowerCase().trim())
                        || author.getBiography().toLowerCase().contains(key.toLowerCase().trim())
                )
                    return true;
                Set<Book> books = author.getBooks();
                if (Objects.nonNull(books))
                    return books.stream().anyMatch(book -> book.getName().toLowerCase().contains(key.toLowerCase().trim()));

                return false;


            }).map(author -> modelMapper.map(author, AuthorDto.class)).collect(Collectors.toList());
        } else {
            authorDtos = authors.stream().map(author -> modelMapper.map(author, AuthorDto.class)).collect(Collectors.toList());
        }


        return authorDtos;
    }

    @Override
    @CacheEvict(value = "authors", allEntries = true)
    public void deleteById(String strId) {
        Long id=Long.parseLong(strId);
        authorRepository.deleteById(id);
    }

    @Override
    public AuthorDto update(RequestAuthorDto requestAuthorDto, Long authorId) {
            Author author=findById(authorId);
            if(Objects.nonNull(requestAuthorDto.name()) && !requestAuthorDto.name().trim().isEmpty())
                author.setName(requestAuthorDto.name());
            if(Objects.nonNull(requestAuthorDto.date_of_birth()))
                author.setDateOfBirth(requestAuthorDto.date_of_birth());
            if(Objects.nonNull(requestAuthorDto.biography()) && !requestAuthorDto.biography().trim().isEmpty())
                author.setBiography(requestAuthorDto.biography());
            if(Objects.nonNull(requestAuthorDto.date_of_die()))
                author.setDateOfDie(requestAuthorDto.date_of_die());
            author= authorRepository.save(author);
            return modelMapper.map(author, AuthorDto.class);
    }

    @Override
    public void setBooks(SetBookForAuthorRequest setBookRequest, String authorId) {
        List<String> errorBookIds=new ArrayList<>();
        List<String> requestBookIds=setBookRequest.book_ids();
        String id="";
        Author author=findById(Long.parseLong(authorId));
        Set<Book> books =author.getBooks();//caanr than loii

        for(int i= 0; i<setBookRequest.book_ids().size(); i++){
             id=requestBookIds.get(i);
            try{
                if(!bookRepository.existsById(id))
                {
                    errorBookIds.add(id);
                    continue;
                }
                Book book=bookRepository.findById(id).orElseThrow();
                books.add(book);
            }catch (Exception ex){
                log.error(ex.getMessage());
            }
        }
        author.setBooks(books);
        authorRepository.save(author);

    }

    private Author findById(Long authorId) {
        if(authorRepository.existsById(authorId)){
            return authorRepository.findById(authorId).orElseThrow();

        }else throw new BadRequestException("Author don't exist.");
    }

    private boolean validAuthorDto(RequestAuthorDto requestAuthorDto) {
        if (Objects.nonNull(requestAuthorDto.name())) {
            if (authorRepository.existsByName(requestAuthorDto.name())) {
                var author = authorRepository.findByName(requestAuthorDto.name()).get();
                if (author.getDateOfBirth().equals(requestAuthorDto.date_of_birth()))
                    return false;
            }
            return true;
        } else
            return false;
    }

    @Cacheable(value = "authors")
    private List<Author> getAll() {
        return authorRepository.findAll();
    }
}

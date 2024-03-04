package com.library.binhson.documentservice.service.common;

import com.library.binhson.documentservice.dto.AuthorDto;
import com.library.binhson.documentservice.dto.request.RequestAuthorDto;
import com.library.binhson.documentservice.dto.request.SetBookForAuthorRequest;

import java.util.HashMap;
import java.util.List;

public interface IAuthorService {
    List<AuthorDto> get(Integer offset, Integer limit);

    AuthorDto addAuthor(RequestAuthorDto requestAuthorDto);

    AuthorDto getById(String id);

    List<AuthorDto> search(HashMap<String, String> searchMap);

    void deleteById(String id);

    AuthorDto update(RequestAuthorDto requestAuthorDto, Long id);

    void setBooks(SetBookForAuthorRequest setBookRequest, String id);
}

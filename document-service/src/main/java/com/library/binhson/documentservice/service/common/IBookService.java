package com.library.binhson.documentservice.service.common;

import com.library.binhson.documentservice.dto.BookDto;
import com.library.binhson.documentservice.dto.request.RequestBookDto;
import com.library.binhson.documentservice.dto.request.RequestUpdateBookDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IBookService {
    List<BookDto> get(Integer offset, Integer limit);

    BookDto getById(String id);

    List<BookDto> search(Map<String, String> map);

    void deleteById(String id);

    BookDto add(RequestBookDto bookDto);

    BookDto update(RequestUpdateBookDto bookDto, String id);

    void setFileForEBook(MultipartFile ebook, String id);

    byte[] getEbook(String id);
}

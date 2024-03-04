package com.library.binhson.documentservice.service.common;

import com.library.binhson.documentservice.dto.CategoryDto;
import com.library.binhson.documentservice.dto.request.RequestCategoryDto;

import java.util.List;

public interface ICategoryService {
    List<CategoryDto> get(Integer offset, Integer limit);

    CategoryDto getById(String id);

    List<CategoryDto> search(String key);

    void delete(String id);

    CategoryDto addNewCategory(RequestCategoryDto categoryDto);

    CategoryDto update(RequestCategoryDto categoryDto, Long id);
}

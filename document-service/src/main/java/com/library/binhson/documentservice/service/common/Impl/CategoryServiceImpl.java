package com.library.binhson.documentservice.service.common.Impl;

import com.library.binhson.documentservice.dto.CategoryDto;
import com.library.binhson.documentservice.dto.request.RequestCategoryDto;
import com.library.binhson.documentservice.entity.Category;
import com.library.binhson.documentservice.repository.CategoryRepository;
import com.library.binhson.documentservice.service.common.ICategoryService;
import com.library.binhson.documentservice.ultil.PageUtilObject;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryDto> get(Integer offset, Integer limit) {
        List<Category> categories = getAll();
        PageUtilObject pageUtilObject = new PageUtilObject(
                limit,
                offset,
                categories
                        .stream()
                        .map(category -> modelMapper.map(category, CategoryDto.class))
                        .collect(Collectors.toList()));
        return (List<CategoryDto>) pageUtilObject.getData();
    }

    @Cacheable(value = "categories")
    private List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryDto getById(String id) {

        Category category = findById(Long.parseLong(id));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> search(String key) {
        List<Category> categories = getAll();
        return categories.stream().filter(category ->
                        category.getDescription().toLowerCase().contains(key.trim().toLowerCase())
                                || category.getName().toLowerCase().contains(key.toLowerCase()))
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList())
                ;
    }

    @Override
    public void delete(String id) {
        Long lgId = Long.parseLong(id);
        if (!categoryRepository.existsById(lgId))
            throw new BadRequestException("Category which have this id don't exist");
        categoryRepository.deleteById(lgId);
    }

    @Override
    public CategoryDto addNewCategory(RequestCategoryDto categoryDto) {
        if (Objects.isNull(categoryDto.name()) || categoryDto.name().trim().isEmpty())
            throw new BadRequestException("Category's name must non null and non empty ");
        var category = Category.builder().name(categoryDto.name())
                .description(categoryDto.description())
                .build();
        var saveCategory = categoryRepository.save(category);
        return modelMapper.map(saveCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto update(RequestCategoryDto dto, Long id) {
        Category category = findById(id);
        if(Objects.nonNull(dto.name()) && !dto.name().trim().isEmpty())
            category.setName(dto.name());
        if(Objects.nonNull(dto.description()))
            category.setDescription(dto.description());
        category=categoryRepository.save(category);
        return modelMapper.map(category, CategoryDto.class);
    }

    private Category findById(Long id) {
        if (!categoryRepository.existsById(id))
            throw new BadRequestException("Category which have this id don't exist");
        return categoryRepository.findById(id).orElseThrow();
    }
}

package com.library.binhson.documentservice.rest.restImpl;

import com.library.binhson.documentservice.dto.AuthorDto;
import com.library.binhson.documentservice.dto.CategoryDto;
import com.library.binhson.documentservice.dto.request.RequestCategoryDto;
import com.library.binhson.documentservice.dto.response.BaseResponse;
import com.library.binhson.documentservice.rest.IAuthorController;
import com.library.binhson.documentservice.rest.ICategoryController;
import com.library.binhson.documentservice.service.common.ICategoryService;
import com.library.binhson.documentservice.ultil.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static com.library.binhson.documentservice.ultil.ResponseUtil.response;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class CategoryController implements ICategoryController {
    private final ICategoryService categoryService;

    @Override
    public ResponseEntity<?> get(Integer offset, Integer limit) {
        if (Objects.isNull(offset) || Objects.isNull(limit)) {
            offset = 1;
            limit = 20;
        }
        List<CategoryDto> categoryDtos = categoryService.get(offset, limit);
        return generateResponse(categoryDtos);
    }



    @Override
    public ResponseEntity<?> get(String id) {
        CategoryDto categoryDto = categoryService.getById(id);
        return generateResponse(categoryDto);
    }

    @Override
    public ResponseEntity<?> search(Map<String,String> map) {
        List<CategoryDto> categoryDtos = categoryService.search(map.get("key"));
        return generateResponse(categoryDtos);
    }


    @Override
    public ResponseEntity<?> delete(String id) {
        categoryService.delete(id);
        return ResponseUtil.success("Delete successfully");
    }

    @Override
    public ResponseEntity<?> addCategory(RequestCategoryDto categoryDto) {
        CategoryDto category=categoryService.addNewCategory(categoryDto);
        return generateResponse(category);
    }

    @Override
    public ResponseEntity<?> updateCategory(RequestCategoryDto categoryDto, Long id) {
        CategoryDto category=categoryService.update(categoryDto, id);
        return generateResponse(category);
    }


    private List<Link> setHateoas(String id) {
        Link selfLink01 = linkTo(methodOn(ICategoryController.class).get(id)).withSelfRel();
        List<Link> links = new ArrayList<>();
        links.add(selfLink01);
        return links;
    }

    private ResponseEntity<?> generateResponse(List<CategoryDto> categoryDtos) {
        categoryDtos = categoryDtos.stream().peek(categoryDto -> categoryDto.add(setHateoas(categoryDto.getId() + ""))).collect(Collectors.toList());
        return response(categoryDtos, categoryDtos.size());
    }

    private ResponseEntity<?> generateResponse(CategoryDto categoryDto) {
        categoryDto = categoryDto.add(setHateoas(categoryDto.getId() + ""));
        return response(categoryDto, 1);
    }



}

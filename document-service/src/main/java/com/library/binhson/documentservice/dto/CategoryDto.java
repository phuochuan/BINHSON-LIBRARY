package com.library.binhson.documentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CategoryDto extends RepresentationModel<CategoryDto> {
    private Long id;
    private String name;
    private String description;
}

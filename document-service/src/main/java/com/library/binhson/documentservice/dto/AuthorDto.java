package com.library.binhson.documentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.library.binhson.documentservice.entity.Book;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;
import java.util.Set;
@Data
public class AuthorDto extends RepresentationModel<AuthorDto> {
    private Long id;
    private String name;
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;
    @JsonProperty("date_of_die")
    private Date dateOfDie;
    private String biography;
    private List<String> bookIds;
}

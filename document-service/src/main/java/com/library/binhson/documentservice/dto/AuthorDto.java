package com.library.binhson.documentservice.dto;

import com.library.binhson.documentservice.entity.Book;
import jakarta.persistence.ManyToMany;

import java.util.Date;
import java.util.Set;

public class AuthorDto {
    private Long id;
    private String name;
    private Date dateOfBirth;
    private Date dateOfDie;
    private String biography;
    private Set<Book> books;
}

package com.library.binhson.documentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity()
@Table(name = "tbAuthor")

@Data
@AllArgsConstructor
@NoArgsConstructor@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date dateOfBirth;
    private Date dateOfDie;
    private String biography;
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;
}

package com.library.binhson.documentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity(name = "tbBook")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "book_type", discriminatorType = DiscriminatorType.STRING)

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    private String id;
    private String name;
    @ManyToMany
    @JoinTable(name ="book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;
    private String yearOfPublish;
    private String republishTime;
    @ManyToMany
    @JoinTable(name = "book_category",
    joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;
    private Integer lent;
    @Enumerated(EnumType.STRING)
    private QualityPaper quality;
    private ImportInvoice stogreInvoince;
}

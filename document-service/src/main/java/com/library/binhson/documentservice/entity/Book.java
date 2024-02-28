package com.library.binhson.documentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity()
@Table(name = "tbBook")
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
    private Integer yearOfPublish;
    private Integer republishTime;
    @ManyToMany
    @JoinTable(name = "book_category",
    joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;
    private Integer length;
    @Enumerated(EnumType.STRING)
    private QualityPaper quality;
    @ManyToOne
    @JoinColumn(name ="import_invoice_Id")
    private ImportInvoice stogreInvoince;
}

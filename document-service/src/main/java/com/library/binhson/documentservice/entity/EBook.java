package com.library.binhson.documentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.sql.Date;
import java.util.Set;

@Data

@AllArgsConstructor
@NoArgsConstructor

@Entity()
@Table(name = "tbEBook")
@DiscriminatorValue("ebook")
public class EBook extends Book{
    private Float size;
    @Lob
    @Column(name = "ebook", columnDefinition="LONGBLOB")
    private byte[] ebook;
    private String fileName;
    private Date generatedDate;

    public EBook(String id, String name, Set<Author> authors, String yearOfPublish, String republishTime, Set<Category> categories, Integer lent, QualityPaper quality, ImportInvoice stogreInvoince, Float size, byte[] ebook, String fileName, Date generatedDate) {
        super(id, name, authors, yearOfPublish, republishTime, categories, lent, quality, stogreInvoince);
        this.size = size;
        this.ebook = ebook;
        this.fileName = fileName;
        this.generatedDate = generatedDate;
    }
}

package com.library.binhson.documentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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


}

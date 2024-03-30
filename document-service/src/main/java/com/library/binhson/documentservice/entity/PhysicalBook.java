package com.library.binhson.documentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity()
@Table(name = "tbPhysicalBook")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("Physical")
public class PhysicalBook extends Book{
    @ManyToOne
    @JoinColumn(name = "local_address_id")
    private LocalAddress localAddress;
    private Float thickness;
    private Float height;
    private Float weight;
    //100-0 %.
    @Enumerated(EnumType.STRING)
    private BookStatus status;
    private Float depreciation;

    public PhysicalBook(String id, String name, Set<Author> authors, Integer yearOfPublish, Integer republishTime, Set<Category> categories, Integer length, QualityPaper quality, ImportInvoice stogreInvoince, DegreeOfSignificant degree, LocalAddress localAddress, Float thickness, Float height, Float weight, BookStatus status, Float depreciation) {
        super(id, name, authors, yearOfPublish, republishTime, categories, length, quality, stogreInvoince, degree);
        this.localAddress = localAddress;
        this.thickness = thickness;
        this.height = height;
        this.weight = weight;
        this.status = status;
        this.depreciation = depreciation;
    }
}

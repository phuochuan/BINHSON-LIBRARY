package com.library.binhson.documentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity(name = "tbPhysicalBook")
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
    private Float depreciation;

    public PhysicalBook(String id, String name, Set<Author> authors, String yearOfPublish, String republishTime, Set<Category> categories, Integer lent, QualityPaper quality, ImportInvoice stogreInvoince, LocalAddress localAddress, Float thickness, Float height, Float weight, Float depreciation) {
        super(id, name, authors, yearOfPublish, republishTime, categories, lent, quality, stogreInvoince);
        this.localAddress = localAddress;
        this.thickness = thickness;
        this.height = height;
        this.weight = weight;
        this.depreciation = depreciation;
    }


}

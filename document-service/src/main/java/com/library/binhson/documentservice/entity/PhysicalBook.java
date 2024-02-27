package com.library.binhson.documentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "tbPhysicalBook")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DiscriminatorValue("Physical")
public class PhysicalBook extends Book{
    @ManyToOne
    @JoinColumn(name = "local_address_id")
    private LocalAddress localAddress;
    private Integer thickness;
    private Integer height;
    private Float weight;
    //100-0 %.
    private Float depreciation;

}

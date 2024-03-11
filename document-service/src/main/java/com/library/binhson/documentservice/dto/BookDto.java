package com.library.binhson.documentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.binhson.documentservice.entity.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BookDto extends RepresentationModel<BookDto> {
    private String id;
    private String name;
    @JsonProperty
    private Set<Long> authorIds;
    @JsonProperty
    private Integer yearOfPublish;
    @JsonProperty
    private Integer republishTime;
    private Set<Long> categoryIds;
    private Integer length;
    private QualityPaper quality;
    @JsonProperty
    private Long importInvoiceId;
    private String type;
    @JsonProperty
    private Long localAddressId;
    private Float thickness;
    private Float height;
    private Float weight;
    private Float depreciation;
    private Float size;
    private String fileName;
    private Date generatedDate;
}

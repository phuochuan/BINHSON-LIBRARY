package com.library.binhson.documentservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.binhson.documentservice.entity.*;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.Set;
@Data
public class RequestUpdateBookDto {
    private String name;
    @JsonProperty("author_ids")
    private Set<Long> authorIds;
    private Integer yearOfPublish;
    private Integer republishTime;
    @JsonProperty("category_ids")
    private Set<Long> categories;
    private Integer length;
    private QualityPaper quality;
    @JsonProperty("import_invoice_id")
    private Long  importInvoiceId;
    private Float size;
    @JsonProperty("generated_date")
    private Date generatedDate;
    @JsonProperty("local_address_id")
    private Long localAddressId;
    private Float thickness;
    private Float height;
    private Float weight;
    private Float depreciation;

}

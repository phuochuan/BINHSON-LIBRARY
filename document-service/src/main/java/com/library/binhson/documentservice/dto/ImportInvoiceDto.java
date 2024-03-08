package com.library.binhson.documentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.binhson.documentservice.entity.Librarian;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportInvoiceDto extends RepresentationModel<ImportInvoiceDto> {
    private Long id;
    private String origin;
    @JsonProperty("import_warehouse_date")
    private Date importWarehouseDate;
    private Float cost;
    @JsonProperty("librarian_id")
    private String librarianId;
}

package com.library.binhson.documentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "tbImportInvoice")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ImportInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String origin;
    private Date importWarehouseDate;
    private Float cost;
    @ManyToOne
    @JoinColumn(name = "librarian_id")
    private Librarian librarian;
}

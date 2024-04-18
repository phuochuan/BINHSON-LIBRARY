package com.library.binhson.paymentservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbInvoiceLine")
@Entity
public class InvoiceLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String bookId;
    private int quantity;
    private int rent;
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice Invoice;
}

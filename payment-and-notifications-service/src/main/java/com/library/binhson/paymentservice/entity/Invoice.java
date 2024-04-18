package com.library.binhson.paymentservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.joda.time.DateTime;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbInvoice")
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;
    @Column(unique = true)
    private long borrowingSessionId;
    private Float total;
    @OneToMany(mappedBy = "Invoice")
    private List<InvoiceLine> invoiceLine;
    @Column(length = 20000)
    private String description;
    private Boolean isCompleted;
    private DateTime payAt;
    @Column(unique = true)
    private String paymentId;

}

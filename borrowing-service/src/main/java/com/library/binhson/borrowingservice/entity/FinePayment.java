package com.library.binhson.borrowingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity()
@Table(name = "tbFinePayment")
public class FinePayment {
    @EmbeddedId
    private FinePaymentId id;

    @ManyToOne
    @JoinColumn(name = "penalty_id")
    @MapsId("fineId")
    private Penalty penalty;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    @MapsId("paymentId")
    private PaymentHistory paymentHistory;
}

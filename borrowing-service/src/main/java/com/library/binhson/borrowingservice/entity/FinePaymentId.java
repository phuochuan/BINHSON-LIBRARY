package com.library.binhson.borrowingservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class FinePaymentId implements Serializable {
    @Column(name = "payment_id")
    private Long paymentId;
    @Column(name = "fine_id")
    private Long fineId;
}

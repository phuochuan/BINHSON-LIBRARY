package com.library.binhson.borrowingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity()
@Table(name = "tbPenalty")
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "payment_type_id")
    @ManyToOne
    private PaymentType paymentType;
    private DateTime timeAt;
    private Double charge;
    @ManyToOne
    @JoinColumn(name = "borrowing_session_id")
    private BorrowingSession borrowingSession;
}

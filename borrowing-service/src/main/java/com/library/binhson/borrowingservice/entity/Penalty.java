package com.library.binhson.borrowingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity()
@Table(name = "tbPenalty")
public class Penalty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double fine;
    @Column(length = 1000000000)
    private String reason;
    @JoinColumn(name = "borrowing_session_id")
    @ManyToOne
    private BorrowingSession borrowingSession;
    private PenaltyStatus status;
}

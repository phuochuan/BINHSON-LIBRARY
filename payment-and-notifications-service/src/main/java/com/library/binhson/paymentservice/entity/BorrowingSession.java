package com.library.binhson.paymentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
@Entity
@Table(name = "tbBorrowingSession")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

@NamedQuery(
        name = "BorrowingSession.findToDonNotCompleteAndGoingEndDate",
        query = "SELECT bs FROM BorrowingSession bs WHERE bs.completed = false AND FUNCTION('DATEDIFF', bs.estimateBookReturnDate, CURRENT_DATE) = 1"
)

@NamedQuery(name = "BorrowingSession.findByOverdue",
query = "SELECT bs FROM BorrowingSession bs WHERE bs.completed = false AND FUNCTION('DATEDIFF', bs.estimateBookReturnDate, CURRENT_DATE) = 0")
public class BorrowingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private User member;
    private DateTime startDate;
    private DateTime estimateBookReturnDate;
    private boolean completed;
}

package com.library.binhson.paymentservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
        query = "SELECT bs FROM BorrowingSession bs WHERE bs.completed = false AND bs.estimateBookReturnDate = :endDate"
)

@NamedQuery(
        name = "BorrowingSession.findByOverdue",
        query = "SELECT bs FROM BorrowingSession bs WHERE bs.completed = false AND bs.estimateBookReturnDate < :currentDate"
)



@JsonIgnoreProperties(ignoreUnknown = true)
public class BorrowingSession {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private User member;
    private DateTime startDate;
    private DateTime estimateBookReturnDate;
    private boolean completed;
}

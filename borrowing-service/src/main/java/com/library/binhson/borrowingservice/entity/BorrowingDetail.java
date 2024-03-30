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
@Table(name = "tbBorrowingSession")
public class BorrowingDetail {
    @EmbeddedId
    private  BorrowingDetailId borrowingDetailId;

    @ManyToOne
    @JoinColumn(name = "borrowing_session_id")
    @MapsId("borrowingSessionId")
    private BorrowingSession borrowingSession;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @MapsId("bookId")
    private Book book;
}

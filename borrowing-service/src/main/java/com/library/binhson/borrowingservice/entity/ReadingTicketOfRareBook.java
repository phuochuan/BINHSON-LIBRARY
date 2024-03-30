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
@Table(name = "tbReadingTicketOfRareBook")
public class ReadingTicketOfRareBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "book_id")
    @ManyToOne
    private Book book;
    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member member;
    @JoinColumn(name = "read_session_id")
    @ManyToOne
    private ReadSession readSession;
    private DateTime dateTime;

}

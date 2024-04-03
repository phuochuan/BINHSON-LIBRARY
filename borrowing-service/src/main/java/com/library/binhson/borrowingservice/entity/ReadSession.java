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
@Table(name = "tbReadSession")
public class ReadSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private DateTime timeOfStarting;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "librarian_id")
    private  Librarian librarian;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private ReadingRoom readingRoom;

}

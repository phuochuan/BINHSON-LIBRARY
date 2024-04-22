package com.library.binhson.paymentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "tbEvent")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    public EventType eventType;
    @Column(length = 20000)
    private String description;
    private String urlImg;
    private DateTime dateAt;

//    @Enumerated(EnumType.STRING)
//    private EventStatus status;


}

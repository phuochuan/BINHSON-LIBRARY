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
@Table(name = "tbReadingRoom")
public class ReadingRoom {
    @Id
    private String id;
    private String name;
}

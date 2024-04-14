package com.library.binhson.borrowingservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReadingRoom {
    @Id
    private String id;
    private String name;
}

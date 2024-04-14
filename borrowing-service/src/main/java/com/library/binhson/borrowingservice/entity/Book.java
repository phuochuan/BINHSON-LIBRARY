package com.library.binhson.borrowingservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

@Entity
@Table(name = "tbBook")

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    @Id
    private String id;
    private String name;
    private BookStatus status;
    private DegreeOfSignificant degree;

}

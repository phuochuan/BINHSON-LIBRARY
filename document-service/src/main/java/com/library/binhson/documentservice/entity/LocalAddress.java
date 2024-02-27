package com.library.binhson.documentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity()
@Table(name = "tbLocalAddress")
public class LocalAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer row;
    private Integer column;
    private Integer width;
    private Integer height;
    @ManyToOne
    @JoinColumn(name = "shelf_id")
    private Shelf shelf;
}

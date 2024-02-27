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
    private Integer shelfRow;
    private Integer shelfColumn;
    private Integer boxWidth;
    private Integer boxHeight;
    @ManyToOne
    @JoinColumn(name = "shelf_id")
    private Shelf shelf;
}

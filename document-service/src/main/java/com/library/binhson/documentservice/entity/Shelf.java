package com.library.binhson.documentservice.entity;

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
@Table(name = "tbShelf")
public class Shelf {
    @Id
    private String shelfId;
    private Integer totalRow;
    private Integer totalColumn;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}

package com.library.binhson.documentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity(name = "tbShelf")
public class Shelf {
    @Id
    private String shelfId;
    private Integer totalRow;
    private Integer totalColumn;
    private Room room;
}

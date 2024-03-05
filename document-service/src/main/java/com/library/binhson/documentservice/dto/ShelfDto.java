package com.library.binhson.documentservice.dto;

import com.library.binhson.documentservice.entity.Room;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShelfDto extends RepresentationModel<ShelfDto> {
    private String shelfId;
    private Integer totalRow;
    private Integer totalColumn;
    private Integer roomId;
}

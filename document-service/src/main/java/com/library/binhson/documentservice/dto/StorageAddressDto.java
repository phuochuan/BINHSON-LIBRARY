package com.library.binhson.documentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.binhson.documentservice.entity.Shelf;
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
public class StorageAddressDto extends RepresentationModel<StorageAddressDto> {
    private Long id;
    @JsonProperty(namespace = "shelf_row")
    private Integer shelfRow;
    @JsonProperty(namespace = "shelf_column")
    private Integer shelfColumn;
    @JsonProperty(namespace = "box_width")
    private Integer boxWidth;
    @JsonProperty(namespace = "box_height")
    private Integer boxHeight;
    @JsonProperty(namespace = "shelf_id")
    private String shelfId;
}

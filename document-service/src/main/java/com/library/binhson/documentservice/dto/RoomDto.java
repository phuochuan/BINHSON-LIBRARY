package com.library.binhson.documentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDto extends RepresentationModel<RoomDto> {
    private Integer id;
    private String name;
    private String floor;
}

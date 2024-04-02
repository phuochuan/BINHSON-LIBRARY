package com.library.binhson.borrowingservice.dto;

import com.library.binhson.borrowingservice.entity.BookStatus;
import com.library.binhson.borrowingservice.entity.DegreeOfSignificant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class BookDto {
    private String id;
    private String name;
    private BookStatus status;
    private DegreeOfSignificant degree;

}

package com.library.binhson.borrowingservice.dto;

import com.library.binhson.borrowingservice.entity.BorrowingType;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingSessionDto extends RepresentationModel<BorrowingSessionDto> {
    private UserDto member;
    private UserDto librarian;
    private DateTime startDate;
    private DateTime estimateBookReturnDate;
    private BorrowingType borrowingType;
    private List<BookDto> bookDtos;
}

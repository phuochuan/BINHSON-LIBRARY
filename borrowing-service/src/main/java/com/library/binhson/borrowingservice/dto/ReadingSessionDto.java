package com.library.binhson.borrowingservice.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class ReadingSessionDto {
    private Long id;
    private DateTime timeOfStarting;
    private UserDto member;
    private UserDto librarian;
    private ReadingRoomDto readingRoom;
}

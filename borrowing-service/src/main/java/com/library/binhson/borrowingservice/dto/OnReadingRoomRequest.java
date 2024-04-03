package com.library.binhson.borrowingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OnReadingRoomRequest {
    private String memberId;
    private String roomId;
}

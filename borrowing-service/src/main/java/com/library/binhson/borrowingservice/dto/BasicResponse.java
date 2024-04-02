package com.library.binhson.borrowingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicResponse {
    private String message;
    private Date dateAt;
    private Integer dataSize;
    private Object data;
}

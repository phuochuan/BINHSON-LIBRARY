package com.library.binhson.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseResponse {
    private Date dateAt;
    private String message;
    private Object data;
}

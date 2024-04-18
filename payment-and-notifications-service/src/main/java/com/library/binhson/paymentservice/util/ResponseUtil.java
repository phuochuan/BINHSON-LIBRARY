package com.library.binhson.paymentservice.util;

import com.library.binhson.paymentservice.dto.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class ResponseUtil {

    public static ResponseEntity<?> response(Object data, int size){
        return ResponseEntity.ok(BaseResponse.builder().dateAt(new Date())
                .data(data).size(size).build());
    }
}

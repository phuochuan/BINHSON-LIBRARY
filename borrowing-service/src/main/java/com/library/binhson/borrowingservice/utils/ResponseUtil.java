package com.library.binhson.borrowingservice.utils;

import com.library.binhson.borrowingservice.dto.BasicResponse;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class ResponseUtil {
    public static ResponseEntity<?> success(String message){
        return ResponseEntity.ok("\" message \" : \""+message+"\"");
    }

    public static ResponseEntity<?> response(Object oj, int size) {
        return ResponseEntity.ok(BasicResponse.builder().dateAt(new Date())
                .dataSize(size)
                .message("Success")
                .data(oj)
                .build());
    }
}

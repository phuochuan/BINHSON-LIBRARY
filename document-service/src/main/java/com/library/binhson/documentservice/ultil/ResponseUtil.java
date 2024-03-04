package com.library.binhson.documentservice.ultil;

import com.library.binhson.documentservice.dto.response.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class ResponseUtil {
    public static ResponseEntity<?> success(String message){
        return ResponseEntity.ok("\" message \" : \""+message+"\"");
    }

    public static ResponseEntity<?> response(Object oj, int size) {
        return ResponseEntity.ok(BaseResponse.builder().dateAt(new Date())
                .dataSize(size)
                .message("Success")
                .data(oj)
                .build());
    }
}

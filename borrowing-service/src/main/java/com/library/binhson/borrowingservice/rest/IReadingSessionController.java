package com.library.binhson.borrowingservice.rest;

import com.library.binhson.borrowingservice.dto.OnReadingRoomRequest;
import jakarta.ws.rs.core.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/borrowing-service/reading")
public interface IReadingSessionController extends IBaseController {
    @PostMapping({"/", ""})
    public ResponseEntity<?> savingHistory(
                                           @RequestBody OnReadingRoomRequest onReading);
}

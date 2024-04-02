package com.library.binhson.borrowingservice.rest;

import jakarta.ws.rs.core.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/borrowing-service/reading")
public interface IReadingSessionController extends IBaseController {
    @PostMapping({"/", ""})
    public ResponseEntity<?> savingHistory(@RequestParam MultipartFile file);
}

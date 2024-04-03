package com.library.binhson.borrowingservice.service;

import com.library.binhson.borrowingservice.dto.DataPage;
import com.library.binhson.borrowingservice.dto.OnReadingRoomRequest;
import com.library.binhson.borrowingservice.dto.ReadingSessionDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface IReadingSessionService {
    DataPage get(int offset, int limit);

    ReadingSessionDto get(String id);

    DataPage search(HashMap<String, String> map);

    void deleteById(String id);

    ReadingSessionDto createASession(OnReadingRoomRequest onReading);

}

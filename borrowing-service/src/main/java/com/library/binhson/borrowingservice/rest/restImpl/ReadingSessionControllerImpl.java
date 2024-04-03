package com.library.binhson.borrowingservice.rest.restImpl;

import com.library.binhson.borrowingservice.dto.DataPage;
import com.library.binhson.borrowingservice.dto.OnReadingRoomRequest;
import com.library.binhson.borrowingservice.dto.ReadingRoomDto;
import com.library.binhson.borrowingservice.dto.ReadingSessionDto;
import com.library.binhson.borrowingservice.rest.IReadingSessionController;
import com.library.binhson.borrowingservice.service.IReadingSessionService;
import jakarta.ws.rs.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.library.binhson.borrowingservice.utils.ResponseUtil.response;

@RestController
@AllArgsConstructor
public class ReadingSessionControllerImpl implements IReadingSessionController {
    private final IReadingSessionService readingSessionService;
    @Override
    public ResponseEntity<?> get(int offset, int limit) {
        DataPage dataPage=readingSessionService.get(offset,limit);
        return generateResponse(dataPage);
    }

    private ResponseEntity<?> generateResponse(DataPage dataPage) {
        List<Object> objects=dataPage.getData();
        var result=objects.stream()//
                .map(object -> (ReadingRoomDto) object)
                .toList();
        return response(result, result.size());
    }

    @Override
    public ResponseEntity<?> get(String id) {
        ReadingSessionDto dto=readingSessionService.get(id) ;
        return generateResponse(dto);
    }

    private ResponseEntity<?> generateResponse(ReadingSessionDto dto) {
        return response(dto,1);
    }

    @Override
    public ResponseEntity<?> search(HashMap<String, String> map) {
        DataPage dataPage=readingSessionService.search(map);
        return generateResponse(dataPage);
    }

    @Override
    public void delete(String id) {
        readingSessionService.deleteById(id);
    }


    @Override
    public ResponseEntity<?> savingHistory( OnReadingRoomRequest onReading) {
        ReadingSessionDto dto=readingSessionService.createASession(onReading);
        return generateResponse(dto);
    }
}

package com.library.binhson.borrowingservice.rest.restImpl;

import com.library.binhson.borrowingservice.dto.BorrowingSessionDto;
import com.library.binhson.borrowingservice.dto.BorrowingSessionRequest;
import com.library.binhson.borrowingservice.dto.DataPage;
import com.library.binhson.borrowingservice.dto.RareBookReadingDto;
import com.library.binhson.borrowingservice.entity.BorrowingSession;
import com.library.binhson.borrowingservice.rest.IBorrowingSessionController;
import com.library.binhson.borrowingservice.service.IBorrowingSessionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.library.binhson.borrowingservice.utils.ResponseUtil.response;

@RestController
@AllArgsConstructor
public class BorrowingSessionControllerImpl implements IBorrowingSessionController {
    private final IBorrowingSessionService borrowingSessionService;
    @Override
    public ResponseEntity<?> get(int offset, int limit) {
        DataPage dataPage=borrowingSessionService.get(offset, limit);
        return generateResponse(dataPage);
    }

    private ResponseEntity<?> generateResponse(DataPage dataPage) {
        List<Object> dtos=dataPage.getData();
        dtos =dtos.stream()
                .map(object -> (BorrowingSessionDto) object)
                .peek(bs->bs.add(hastoas(bs)))
                .collect(Collectors.toList());
        return response(dtos,dataPage.getFoundValueCount());

    }

    private List<Link> hastoas(BorrowingSessionDto bs) {
        List<Link> links=new ArrayList<>();
        //todo

        return links;
    }


    @Override
    public ResponseEntity<?> get(String id) {
        BorrowingSessionDto dto=borrowingSessionService.get(id);
        return generateResponse(dto);
    }

    private ResponseEntity<?> generateResponse(BorrowingSessionDto dto) {
        return response(dto, 1);
    }

    @Override
    public ResponseEntity<?> search(HashMap<String, String> map) {
        DataPage dataPage=borrowingSessionService.search(map);
        return generateResponse(dataPage);
    }

    @Override
    public void delete(String id) {
        borrowingSessionService.deleteById(id);
    }


    @Override
    public ResponseEntity<?> reservation(BorrowingSessionRequest request) {
        BorrowingSessionDto dto=borrowingSessionService.addNewSession(request.getBookIds(),request.getMemberId());
        return generateResponse(dto);
    }


    @Override
    public ResponseEntity<?> renew(Long id) {
        BorrowingSessionDto dto=borrowingSessionService.renewSession(id);
        return generateResponse(dto);
    }
}

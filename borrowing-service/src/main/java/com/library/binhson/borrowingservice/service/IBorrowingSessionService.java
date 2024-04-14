package com.library.binhson.borrowingservice.service;

import com.library.binhson.borrowingservice.dto.BorrowingSessionDto;
import com.library.binhson.borrowingservice.dto.DataPage;

import java.util.HashMap;
import java.util.List;

public interface IBorrowingSessionService {
    DataPage get(int offset, int limit);

    BorrowingSessionDto get(String id);

    DataPage search(HashMap<String, String> map);

    void deleteById(String id);

    BorrowingSessionDto addNewSession(List<String> bookIds, String memberId);

    BorrowingSessionDto renewSession(Long id);

    BorrowingSessionDto addNewSession(List<String> bookIds);
}

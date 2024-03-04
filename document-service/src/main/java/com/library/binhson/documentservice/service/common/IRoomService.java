package com.library.binhson.documentservice.service.common;

import com.library.binhson.documentservice.dto.RoomDto;
import com.library.binhson.documentservice.dto.request.RequestRoomDto;

import java.util.List;

public interface IRoomService {
    List<RoomDto> get(Integer offset, Integer limit);

    RoomDto getById(String id);

    List<RoomDto> search(Integer offset, Integer limit, String key);

    void delete(String id);

    RoomDto addNewRoom(RequestRoomDto dto);

    RoomDto update(RequestRoomDto dto, Integer id);
}

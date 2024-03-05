package com.library.binhson.documentservice.service.common;

import com.library.binhson.documentservice.dto.ShelfDto;
import com.library.binhson.documentservice.dto.request.RequestShelfDto;

import java.util.List;

public interface IShelfService {
    List<ShelfDto> get(Integer offset, Integer limit);

    ShelfDto get(String id);

    List<ShelfDto> search(Integer offset, Integer limit, String key);

    void delete(String id);

    ShelfDto add(RequestShelfDto dto);

    ShelfDto update(RequestShelfDto dto, String id);
}

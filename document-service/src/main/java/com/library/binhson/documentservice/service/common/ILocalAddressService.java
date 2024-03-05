package com.library.binhson.documentservice.service.common;

import com.library.binhson.documentservice.dto.StorageAddressDto;

import java.util.List;

public interface ILocalAddressService {
    List<StorageAddressDto> get(Integer offset, Integer limit);

    StorageAddressDto get(String id);

    List<StorageAddressDto> search(Integer offset, Integer limit, String key);

    void delete(String id);

    StorageAddressDto add(StorageAddressDto dto);

    StorageAddressDto update(StorageAddressDto dto, Long id);
}

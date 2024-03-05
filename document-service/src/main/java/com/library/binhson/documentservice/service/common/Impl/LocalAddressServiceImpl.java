package com.library.binhson.documentservice.service.common.Impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.binhson.documentservice.dto.StorageAddressDto;
import com.library.binhson.documentservice.entity.LocalAddress;
import com.library.binhson.documentservice.entity.Shelf;
import com.library.binhson.documentservice.repository.LocalAddressRepository;
import com.library.binhson.documentservice.repository.ShelfRepository;
import com.library.binhson.documentservice.service.common.ILocalAddressService;
import com.library.binhson.documentservice.ultil.PageUtilObject;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocalAddressServiceImpl implements ILocalAddressService {
    private final LocalAddressRepository addressRepository;
    private final ShelfRepository shelfRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<StorageAddressDto> get(Integer offset, Integer limit) {
        var storageAddressDtos = getDtos();
        PageUtilObject pageUtilObject = new PageUtilObject(limit, offset, new ArrayList<>(storageAddressDtos));
        return (List<StorageAddressDto>) pageUtilObject.getData();
    }

    @Override
    public StorageAddressDto get(String id) {
        return findDtoOnCacheById(Long.parseLong(id));
    }

    @Override
    public List<StorageAddressDto> search(Integer offset, Integer limit, String key) {
        var storageAddressDtos = getDtos();
        var foundStorageAddressDtos = storageAddressDtos.stream()
                .filter(dto -> {
                    try {
                        Integer intKey = Integer.parseInt(key);
                        return dto.getBoxWidth().equals(intKey) ||
                                dto.getShelfRow().equals(intKey) ||
                                dto.getShelfColumn().equals(intKey) ||
                                dto.getId().equals(Long.parseLong(key));
                    } catch (Exception ignored) {

                    }
                    return false;
                }).findAny().stream().toList();
        PageUtilObject pageUtilObject = new PageUtilObject(limit, offset, List.of(foundStorageAddressDtos));
        return (List<StorageAddressDto>) pageUtilObject.getData();
    }

    @Override
    public void delete(String id) {
        LocalAddress localAddress = findById(Long.parseLong(id));
        addressRepository.delete(localAddress);
    }

    @Override
    public StorageAddressDto add(StorageAddressDto dto) {
        var shelf = findShelfById(dto.getShelfId());
        LocalAddress localAddress = modelMapper.map(dto, LocalAddress.class);
        localAddress.setShelf(shelf);
        var saveResult = addressRepository.save(localAddress);
        return modelMapper.map(saveResult, StorageAddressDto.class);
    }


    private Shelf findShelfById(String id) {
        return shelfRepository.findById(id).orElseThrow(() -> new BadRequestException("Shefl is incorect"));
    }

    @Override
    public StorageAddressDto update(StorageAddressDto dto, Long id) {
        var localAddress = findById(id);
        if (Objects.nonNull(dto.getShelfId())) {
            var self = findShelfById(dto.getShelfId());
            localAddress.setShelf(self);
        }
        Integer shelfRow = dto.getShelfRow();
        Integer shelfColumn = dto.getShelfColumn();
        Integer boxWidth = dto.getBoxWidth();
        Integer boxHeight = dto.getBoxHeight();
        if (Objects.nonNull(shelfRow) && shelfRow>0) {
            localAddress.setShelfRow(shelfRow);
        }
        if(Objects.nonNull(shelfColumn) && shelfColumn>0){
            localAddress.setShelfColumn(shelfColumn);
        }
        if(Objects.nonNull(boxHeight) && boxHeight>0){
            localAddress.setBoxHeight(boxHeight);
        }
        if(Objects.nonNull(boxWidth) && boxWidth>0){
            localAddress.setBoxWidth(boxWidth);
        }
        var saveAdd=addressRepository.save(localAddress);
        return modelMapper.map(saveAdd, StorageAddressDto.class);
    }

    @Cacheable(cacheNames = "storage_address")
    private List<LocalAddress> getAll() {
        return addressRepository.findAll();
    }

    private List<StorageAddressDto> getDtos() {
        List<LocalAddress> localAddresses = getAll();
        return localAddresses.stream().map(localAddress -> modelMapper.map(localAddress, StorageAddressDto.class))
                .collect(Collectors.toList());
    }

    private LocalAddress findById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new BadRequestException("Storage address which have this id don't exist. "));
    }

    private StorageAddressDto findDtoOnCacheById(Long id) {
        return getDtos().stream().filter(dto -> dto.getId().equals(id)).findFirst().orElseThrow(() -> new BadRequestException("Storage address which have this id don't exist. "));
    }


}

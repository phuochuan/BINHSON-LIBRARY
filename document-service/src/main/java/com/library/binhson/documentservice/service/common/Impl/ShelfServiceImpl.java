package com.library.binhson.documentservice.service.common.Impl;

import com.library.binhson.documentservice.dto.ShelfDto;
import com.library.binhson.documentservice.dto.request.RequestShelfDto;
import com.library.binhson.documentservice.entity.Room;
import com.library.binhson.documentservice.entity.Shelf;
import com.library.binhson.documentservice.repository.RoomRepository;
import com.library.binhson.documentservice.repository.ShelfRepository;
import com.library.binhson.documentservice.service.common.IShelfService;
import com.library.binhson.documentservice.ultil.PageUtilObject;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShelfServiceImpl implements IShelfService {
    private final ShelfRepository shelfRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<ShelfDto> get(Integer offset, Integer limit) {
        List<Shelf> shelves=getAll();
        PageUtilObject pageUtilObject=new PageUtilObject(limit,offset,shelves.stream().collect(Collectors.toList()));
        return (List<ShelfDto>) pageUtilObject.getData();
    }



    @Override
    public ShelfDto get(String id) {
        Shelf shelf=findById(id);
        return modelMapper.map(shelf, ShelfDto.class);
    }


    @Override
    public List<ShelfDto> search(Integer offset, Integer limit, String key) {
        List<Shelf> shelves=getAll().stream()
                .filter(shelf -> shelf.getShelfId().toLowerCase().contains(key.toLowerCase())
                ).toList();

        PageUtilObject pageUtilObject=new PageUtilObject(limit,offset,shelves.stream().collect(Collectors.toList()));
        return (List<ShelfDto>) pageUtilObject.getData();
    }

    @Override
    public void delete(String id) {
        Shelf shelf=findById(id);
        shelfRepository.delete(shelf);
    }

    @Override
    public ShelfDto add(RequestShelfDto dto) {
        if(!validShelf(dto))
            throw new  BadRequestException("Data is incorect.");
        Room room=findRoomById(dto);
        Shelf shelf
                =Shelf.builder()
                .totalColumn(dto.total_column())
                .totalRow(dto.total_row())
                .room(room)
                .build();
        shelf= shelfRepository.save(shelf);
        return modelMapper.map(shelf, ShelfDto.class);
    }

    private Room findRoomById(RequestShelfDto dto) {
        return roomRepository.findById(dto.room_id()).orElseThrow(()-> new BadRequestException("Room don't exist."));
    }

    private boolean validShelf(RequestShelfDto dto) {
        return Objects.nonNull(dto.room_id())
                && Objects.nonNull(dto.total_column())
                && Objects.nonNull(dto.total_row())
                && dto.total_column()>0 && dto.total_row()>0;
    }

    @Override
    public ShelfDto update(RequestShelfDto dto, String id) {
        Shelf shelf=findById(id);
        if(Objects.nonNull(dto.total_row()) && dto.total_row()>0)
            shelf.setTotalRow(dto.total_row());
        if(Objects.nonNull(dto.total_column()) && dto.total_column()>0)
            shelf.setTotalColumn(dto.total_column());
        if(Objects.nonNull(dto.room_id())) {
            Room room
                    = findRoomById(dto);
            shelf.setRoom(room);
        }
        shelf=shelfRepository.save(shelf);
        return modelMapper.map(shelf,ShelfDto.class);
    }

    private Shelf findById(String id) {
        return shelfRepository.findById(id).orElseThrow(()->  new BadRequestException("Shelf which have this id don't exist."));
    }

    @Cacheable(value = "shelves")
    private List<Shelf> getAll() {
        return shelfRepository.findAll();
    }

}

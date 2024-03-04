package com.library.binhson.documentservice.service.common.Impl;

import com.library.binhson.documentservice.dto.RoomDto;
import com.library.binhson.documentservice.dto.request.RequestRoomDto;
import com.library.binhson.documentservice.entity.Room;
import com.library.binhson.documentservice.repository.RoomRepository;
import com.library.binhson.documentservice.service.common.IRoomService;
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
public class RoomServiceImpl implements IRoomService {
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<RoomDto> get(Integer offset, Integer limit) {
        List<Room> rooms=getAll();
        PageUtilObject pageUtilObject=new PageUtilObject(limit,offset,rooms.
                stream()
                .map(room -> modelMapper.map(room, RoomDto.class))
                .collect(Collectors.toList()));
        return (List<RoomDto>) pageUtilObject.getData();
    }

    @Cacheable(value = "rooms")
    private List<Room> getAll() {
        return  roomRepository.findAll();
    }

    @Override
    public RoomDto getById(String id) {
        Room room=findById(id);
        return modelMapper.map(room, RoomDto.class);
    }

    private Room findById(String id) {
        Integer intId=Integer.parseInt(id);
        if(!roomRepository.existsById(intId))
            throw new BadRequestException("Room which have this id don't exist.");
            
        return roomRepository.findById(intId).orElseThrow();
    }

    @Override
    public List<RoomDto> search(Integer offset, Integer limit, String key) {
        List<Room> rooms=getAll();
        PageUtilObject pageUtilObject=new PageUtilObject(limit,offset,rooms.
                stream()
                .filter(room -> room.getName().toLowerCase().contains(key.trim().toLowerCase())
                        || room.getFloor().toLowerCase().contains(key.trim().toLowerCase())
                )
                .map(room -> modelMapper.map(room, RoomDto.class))
                .collect(Collectors.toList()));
        return (List<RoomDto>) pageUtilObject.getData();
    }

    @Override
    public void delete(String id) {
        Room room=findById(id);
        roomRepository.delete(room);
    }

    @Override
    public RoomDto addNewRoom(RequestRoomDto dto) {
        if(!validRoomInformation(dto))
            throw new BadRequestException("Room information must fill fully.");
        if(roomRepository.existsByNameAndFloor(dto.name(),dto.floor()))
            throw new BadRequestException("Room existed.");
        Room room=Room.builder()
                .floor(dto.floor())
                .name(dto.name())
                .build();
        room=roomRepository.save(room);
        return modelMapper.map(room, RoomDto.class);
    }

    private boolean validRoomInformation(RequestRoomDto dto) {
        return Objects.nonNull(dto.name())
                && Objects.nonNull(dto.floor())
                && !dto.name().trim().isEmpty()
                && !dto.floor().trim().isEmpty();
    }

    @Override
    public RoomDto update(RequestRoomDto dto, Integer id) {
        Room room=findById(id+"");
        if(Objects.nonNull(dto.name())
                && !dto.name().trim().isEmpty())
            room.setName(dto.name());

        if (Objects.nonNull(dto.floor())
                && !dto.floor().trim().isEmpty())
            room.setFloor(dto.floor());

        room=roomRepository.save(room);
        return modelMapper.map(room, RoomDto.class);
    }
}

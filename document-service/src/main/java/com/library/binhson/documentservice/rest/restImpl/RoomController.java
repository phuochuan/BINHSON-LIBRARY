package com.library.binhson.documentservice.rest.restImpl;

import com.library.binhson.documentservice.dto.RoomDto;
import com.library.binhson.documentservice.dto.request.RequestRoomDto;
import com.library.binhson.documentservice.rest.ICategoryController;
import com.library.binhson.documentservice.rest.IRoomController;
import com.library.binhson.documentservice.service.common.IRoomService;
import com.library.binhson.documentservice.ultil.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.library.binhson.documentservice.ultil.ResponseUtil.success;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class RoomController implements IRoomController {
    private final IRoomService roomService;
    @Override
    public ResponseEntity<?> get(Integer offset, Integer limit) {
        List<RoomDto> roomDtos=roomService.get(offset,limit);
        return generateResponse(roomDtos);
    }



    @Override
    public ResponseEntity<?> get(String id) {
       RoomDto roomDto=roomService.getById(id);
        return generateResponse(roomDto);
    }

    @Override
    public ResponseEntity<?> search(Integer offset, Integer limit, String key, Integer sort, String type) {
        List<RoomDto> roomDtos=roomService.search(offset,limit,key);
        return generateResponse(roomDtos);
    }


    @Override
    public ResponseEntity<?> delete(String id) {
        roomService.delete(id);
        return success("Delete successfully");
    }

    @Override
    public ResponseEntity<?> addNew(RequestRoomDto dto) {
        RoomDto room=roomService.addNewRoom(dto);
        return generateResponse(room);
    }

    @Override
    public ResponseEntity<?> update(RequestRoomDto dto, Integer id) {
        RoomDto room=roomService.update(dto, id);

        return generateResponse(room);
    }


    private List<Link> setHateoas(String id) {
        Link selfLink01 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ICategoryController.class).get(id)).withSelfRel();
        List<Link> links = new ArrayList<>();
        links.add(selfLink01);
        return links;
    }
    private ResponseEntity<?> generateResponse(List<RoomDto> roomDtos) {
        roomDtos=roomDtos.stream().peek(roomDto -> roomDto.add(setHateoas(roomDto.getId()+""))).toList();
        return ResponseUtil.response(roomDtos,roomDtos.size());
    }
    private ResponseEntity<?> generateResponse(RoomDto roomDto) {
        roomDto=roomDto.add(setHateoas(roomDto.getId()+""));
        return ResponseUtil.response(roomDto,1);
    }

}

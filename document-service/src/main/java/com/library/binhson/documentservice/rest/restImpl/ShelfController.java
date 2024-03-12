package com.library.binhson.documentservice.rest.restImpl;

import com.library.binhson.documentservice.dto.RoomDto;
import com.library.binhson.documentservice.dto.ShelfDto;
import com.library.binhson.documentservice.dto.request.RequestShelfDto;
import com.library.binhson.documentservice.entity.Room;
import com.library.binhson.documentservice.rest.IRoomController;
import com.library.binhson.documentservice.rest.IShelfController;
import com.library.binhson.documentservice.service.common.IShelfService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.library.binhson.documentservice.ultil.ResponseUtil.response;
import static com.library.binhson.documentservice.ultil.ResponseUtil.success;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class ShelfController implements IShelfController {
    private final IShelfService shelfService;
    @Override
    public ResponseEntity<?> get(Integer offset, Integer limit) {
        List<ShelfDto> shelfDtos=shelfService.get(offset,limit);
        return generateResponse(shelfDtos);
    }



    @Override
    public ResponseEntity<?> get(String id) {
       ShelfDto shelfDto=shelfService.get(id);
        return generateResponse(shelfDto);
    }

    @Override
    public ResponseEntity<?> search(Map<String,String> map) {
        int offset=1;
        int limit=10;
        String key="";
        try{
            key=map.get("key");
            offset=Integer.parseInt(map.get("offset"));
            limit=Integer.parseInt(map.get("limit"));
        }catch (Exception ignored){

        }
        List<ShelfDto> shelfDtos=shelfService.search(offset,limit, key);
        return generateResponse(shelfDtos);
    }

   

    @Override
    public ResponseEntity<?> delete(String id) {
        shelfService.delete(id);
        return success("Delete successfully");
    }

    @Override
    public ResponseEntity<?> addNew(RequestShelfDto dto) {
        ShelfDto shelfDto=shelfService.add(dto);
        return generateResponse(shelfDto);
    }

    @Override
    public ResponseEntity<?> update(RequestShelfDto dto, String id) {
        ShelfDto shelfDto=shelfService.update(dto,id);
        return generateResponse(shelfDto);
    }

    private List<Link> setHateoas(ShelfDto dto){
        Link selflink
                =linkTo(methodOn(IShelfController.class).get(dto.getShelfId()+"")).withSelfRel();
        Link roomLink
                =linkTo(methodOn(IRoomController.class).get(dto.getRoomId()+"")).withRel("room");
        List<Link > links=new ArrayList<>();
        links.add(selflink);
        links.add(roomLink);
        return links;
    }

    private ResponseEntity<?> generateResponse(List<ShelfDto> shelfDtos) {
        shelfDtos=shelfDtos.stream().peek(shelfDto -> shelfDto.add(setHateoas(shelfDto))).collect(Collectors.toList());
        return response(shelfDtos,shelfDtos.size());
    }
    private ResponseEntity<?> generateResponse(ShelfDto shelfDto) {
        shelfDto.add(setHateoas(shelfDto));
        return response(shelfDto,1);
    }


}

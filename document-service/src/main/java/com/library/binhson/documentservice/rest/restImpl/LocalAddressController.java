package com.library.binhson.documentservice.rest.restImpl;

import com.library.binhson.documentservice.dto.StorageAddressDto;
import com.library.binhson.documentservice.dto.request.RequestImportInvoiceDto;
import com.library.binhson.documentservice.rest.ILocalAddressController;
import com.library.binhson.documentservice.service.common.ILocalAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.*;

import static com.library.binhson.documentservice.ultil.ResponseUtil.response;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class LocalAddressController implements ILocalAddressController {
    private final ILocalAddressService addressService;
    @Override
    public ResponseEntity<?> get(Integer offset, Integer limit) {
        List<StorageAddressDto> storageAddressDtos=addressService.get(offset,limit);
        return generateResponse(storageAddressDtos);
    }

    @Override
    public ResponseEntity<?> get(String id) {
        StorageAddressDto storageAddressDto=addressService.get(id) ;
        return generateResponse(storageAddressDto);
    }

    @Override
    public ResponseEntity<?> search(HashMap<String, String> map) {
        Integer offset=1;
        Integer limit=10;
        String key="";
        try{
            key=map.get("key");
            offset=Integer.parseInt(map.get("offset"));
            limit=Integer.parseInt(map.get("limit"));
        }catch (Exception ex){

        }
        List<StorageAddressDto> storageAddressDtos=addressService.search(offset,limit, key);
        return generateResponse(storageAddressDtos);
    }



    @Override
    public ResponseEntity<?> delete(String id) {
        addressService.delete(id);
        return ResponseEntity.ok("");
    }

    @Override
    public ResponseEntity<?> addStorageAddress(StorageAddressDto dto) {
        StorageAddressDto storageAddressDto=addressService.add(dto);
        return generateResponse(storageAddressDto);
    }

    @Override
    public ResponseEntity<?> updateStorageAddress(StorageAddressDto dto, Long id) {
        StorageAddressDto storageAddressDto=addressService.update(dto,id);
        return generateResponse(storageAddressDto);
    }


    private ResponseEntity<?> generateResponse(List<StorageAddressDto> storageAddressDtos){
        storageAddressDtos=storageAddressDtos.stream()
                .peek(storageAddressDto -> storageAddressDto.add(setHateoas(storageAddressDto.getId()+"")))
                .toList();
        return response(storageAddressDtos, storageAddressDtos.size());
    }
    private ResponseEntity<?> generateResponse(StorageAddressDto storageAddressDto){
        storageAddressDto.add(setHateoas(storageAddressDto.getId()+""));
        return response(storageAddressDto, 1);
    }
    private List<Link> setHateoas(String id){
        Link link=linkTo(methodOn(ILocalAddressController.class).get(id)).withSelfRel();
        return Arrays.asList(link);
    }


}

package com.library.binhson.documentservice.rest;

import com.library.binhson.documentservice.dto.StorageAddressDto;
import com.library.binhson.documentservice.dto.request.RequestImportInvoiceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/document-service/storage-address")
public interface ILocalAddressController extends  IBaseController{


    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN','WAREHOUSE_STAFF')")
    ResponseEntity<?> addStorageAddress(@RequestBody StorageAddressDto dto);

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN','WAREHOUSE_STAFF')")
    ResponseEntity<?> updateStorageAddress(@RequestBody StorageAddressDto dto,  @PathVariable("id") Long id);


}

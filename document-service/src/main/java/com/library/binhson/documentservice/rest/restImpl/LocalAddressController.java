package com.library.binhson.documentservice.rest.restImpl;

import com.library.binhson.documentservice.dto.request.RequestImportInvoiceDto;
import com.library.binhson.documentservice.rest.ILocalAddressController;
import com.library.binhson.documentservice.service.common.ILocalAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LocalAddressController implements ILocalAddressController {
    private final ILocalAddressService localAddressService;
    @Override
    public ResponseEntity<?> get(Integer offset, Integer limit) {
        return null;
    }

    @Override
    public ResponseEntity<?> get(String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> search(Integer offset, Integer limit, String key, Integer sort, String type) {
        return null;
    }



    @Override
    public ResponseEntity<?> delete(String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> addStorageAddress(RequestImportInvoiceDto invoiceDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateStorageAddress(RequestImportInvoiceDto invoiceDto, Long id) {
        return null;
    }
}

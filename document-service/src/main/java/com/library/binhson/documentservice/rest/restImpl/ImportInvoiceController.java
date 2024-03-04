package com.library.binhson.documentservice.rest.restImpl;

import com.library.binhson.documentservice.dto.request.RequestImportInvoiceDto;
import com.library.binhson.documentservice.rest.IImportInvoiceController;
import com.library.binhson.documentservice.service.common.IImportInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImportInvoiceController implements IImportInvoiceController {
    private final IImportInvoiceService importInvoiceService;
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
    public ResponseEntity<?> addImportImportInvoice(RequestImportInvoiceDto invoiceDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateImportInvoice(RequestImportInvoiceDto invoiceDto, Long id) {
        return null;
    }
}

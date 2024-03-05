package com.library.binhson.documentservice.rest;

import com.library.binhson.documentservice.dto.request.RequestImportInvoiceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/document-service/import-invoices")
public interface IImportInvoiceController extends IBaseController {



    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN','WAREHOUSE_STAFF')")
    ResponseEntity<?> addImportImportInvoice(@RequestBody RequestImportInvoiceDto invoiceDto);

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN','WAREHOUSE_STAFF')")
    ResponseEntity<?> updateImportInvoice(@RequestBody RequestImportInvoiceDto invoiceDto,  @PathVariable("id") Long id);

}

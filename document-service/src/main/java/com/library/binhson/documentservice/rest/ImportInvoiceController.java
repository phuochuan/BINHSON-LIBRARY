package com.library.binhson.documentservice.rest;

import com.library.binhson.documentservice.dto.Request.RequestCategoryDto;
import com.library.binhson.documentservice.dto.Request.RequestImportInvoiceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/document-service/import-invoices")
public interface ImportInvoiceController extends IBaseController {



    @PostMapping("")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> addImportImportInvoice(@RequestBody RequestImportInvoiceDto invoiceDto);

    @PatchMapping("/{id}")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> updateImportInvoice(@RequestBody RequestImportInvoiceDto invoiceDto,  @PathVariable("id") Long id);

}

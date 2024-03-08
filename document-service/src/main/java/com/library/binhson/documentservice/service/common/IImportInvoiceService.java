package com.library.binhson.documentservice.service.common;

import com.library.binhson.documentservice.dto.ImportInvoiceDto;
import com.library.binhson.documentservice.dto.request.RequestImportInvoiceDto;

import java.util.List;

public interface IImportInvoiceService {
    List<ImportInvoiceDto> get(Integer offset, Integer limit);

    ImportInvoiceDto get(String id);

    void delete(String id);

    ImportInvoiceDto add(RequestImportInvoiceDto invoiceDto);

    ImportInvoiceDto update(RequestImportInvoiceDto invoiceDto, Long id);
}

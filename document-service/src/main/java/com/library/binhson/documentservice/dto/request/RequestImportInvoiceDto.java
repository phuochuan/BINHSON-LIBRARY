package com.library.binhson.documentservice.dto.request;

import java.util.Date;

public record RequestImportInvoiceDto(String origin,
                                      Date import_warehouse_date,
                                      Float cost) {
}

package com.library.binhson.documentservice.dto.Request;

import java.util.Date;

public record RequestImportInvoiceDto(String origin,
                                      Date import_warehouse_date,
                                      Float cost) {
}

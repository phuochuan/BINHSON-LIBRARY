package com.library.binhson.documentservice.dto.request;

public record RequestStorageAddressDto(
        Integer shelfRow,
        Integer shelfColumn,
        Integer boxWidth,
        Integer boxHeight) {
}

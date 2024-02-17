package com.library.binhson.userservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BorrowPermissionRequest {
    private String noDot;
    private String nationality;
    private String placeOfOrigin;
    private String placeOfResidence;
    private String personalIdentification;
    private byte[] faceId;
}

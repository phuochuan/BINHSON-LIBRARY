package com.library.binhson.borrowingservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class BorrowingDetailId implements Serializable {
    private Long borrowingSessionId;
    private String bookId;

}

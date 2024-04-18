package com.library.binhson.paymentservice.dto;

import com.library.binhson.paymentservice.entity.EventType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class EventDto {
    private long id;
    public EventType eventType;
    private String description;
    private String urlImg;
    private DateTime dateAt;
}

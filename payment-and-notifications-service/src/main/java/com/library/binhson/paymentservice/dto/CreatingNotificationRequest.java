package com.library.binhson.paymentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.binhson.paymentservice.entity.EventType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatingNotificationRequest {
    @JsonProperty("event_type")
    public EventType eventType;
    @JsonProperty("event_description")
    private String eventDescription;
    @JsonProperty("user_ids")
    private List<String> userIds;
    @JsonProperty("first_content")
    private String firstContent;
    private String parameter;
    @JsonProperty("last_content")
    private String lastContent;
    @JsonProperty("img_url")
    private String imgUrl;
}

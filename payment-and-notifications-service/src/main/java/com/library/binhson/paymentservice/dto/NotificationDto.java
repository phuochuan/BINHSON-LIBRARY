package com.library.binhson.paymentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.binhson.paymentservice.entity.Event;
import com.library.binhson.paymentservice.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class NotificationDto extends RepresentationModel<NotificationDto> {

    private long id;
    @JsonProperty("event_id")
    private Long eventId;
    @JsonProperty("user_id")
    private String userId;
    private String content;
    @JsonProperty("img_url")
    private String imgUrl;
    @JsonProperty("is_seen")
    private boolean isSeen;
}

package com.library.binhson.documentservice.dto.Request;

import com.library.binhson.documentservice.entity.Room;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record RequestShelfDto( Integer total_row,
         Integer total_column,
         Integer room_id) {
}

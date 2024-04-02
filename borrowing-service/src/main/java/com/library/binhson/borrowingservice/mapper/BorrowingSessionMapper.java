package com.library.binhson.borrowingservice.mapper;

import com.library.binhson.borrowingservice.dto.BorrowingSessionDto;
import com.library.binhson.borrowingservice.entity.BorrowingSession;
import jakarta.ws.rs.NotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.transaction.annotation.Transactional;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Transactional(rollbackFor = {Exception.class, NotFoundException.class})
public class BorrowingSessionMapper {
    BorrowingSessionMapper INSTANCE= Mappers.getMapper(BorrowingSessionMapper.class);

   //todo
}

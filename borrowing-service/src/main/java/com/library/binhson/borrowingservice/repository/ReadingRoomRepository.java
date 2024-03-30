package com.library.binhson.borrowingservice.repository;

import com.library.binhson.borrowingservice.entity.ReadingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingRoomRepository extends JpaRepository<ReadingRoom, Long> {
}

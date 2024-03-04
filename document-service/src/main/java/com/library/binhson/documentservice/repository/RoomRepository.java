package com.library.binhson.documentservice.repository;

import com.library.binhson.documentservice.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    boolean existsByNameAndFloor(String name, String floor);
}

package com.library.binhson.borrowingservice.repository;

import com.library.binhson.borrowingservice.entity.ReadSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadSessionRepository extends JpaRepository<ReadSession, Long> {
}

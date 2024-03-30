package com.library.binhson.borrowingservice.repository;

import com.library.binhson.borrowingservice.entity.BorrowingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowingSessionRepository extends JpaRepository<BorrowingSession, Long> {
}

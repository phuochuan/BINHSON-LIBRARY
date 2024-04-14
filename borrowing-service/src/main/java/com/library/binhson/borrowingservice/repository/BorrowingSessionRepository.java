package com.library.binhson.borrowingservice.repository;

import com.library.binhson.borrowingservice.entity.BorrowingSession;
import com.library.binhson.borrowingservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingSessionRepository extends JpaRepository<BorrowingSession, Long> {
    List<BorrowingSession> findAllByMember(Member member);
}

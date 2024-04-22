package com.library.binhson.paymentservice.repository;

import com.library.binhson.paymentservice.entity.BorrowingSession;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingSessionRepository extends JpaRepository<BorrowingSession, Long> {
//    List<BorrowingSession> findByCompleted(boolean b);

    List<BorrowingSession> findToDonNotCompleteAndGoingEndDate(DateTime endDate);

    List<BorrowingSession> findByOverdue(DateTime currentDate);
}

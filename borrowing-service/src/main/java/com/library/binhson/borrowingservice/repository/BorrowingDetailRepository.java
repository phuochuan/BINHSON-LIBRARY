package com.library.binhson.borrowingservice.repository;

import com.library.binhson.borrowingservice.entity.Book;
import com.library.binhson.borrowingservice.entity.BorrowingDetail;
import com.library.binhson.borrowingservice.entity.BorrowingDetailId;
import com.library.binhson.borrowingservice.entity.BorrowingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingDetailRepository extends JpaRepository<BorrowingDetail, BorrowingDetailId> {
    List<BorrowingDetail> findByBorrowingSession(BorrowingSession bs);
}

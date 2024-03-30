package com.library.binhson.borrowingservice.repository;

import com.library.binhson.borrowingservice.entity.BorrowingDetail;
import com.library.binhson.borrowingservice.entity.BorrowingDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowingDetailRepository extends JpaRepository<BorrowingDetail, BorrowingDetailId> {
}

package com.library.binhson.userservice.repository;

import com.library.binhson.userservice.entity.ConfirmedCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmedCodeRepository extends JpaRepository<ConfirmedCode, Long> {
}

package com.library.binhson.userservice.repository;

import com.library.binhson.userservice.entity.JsonWebToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JsonWebTokenRepository extends JpaRepository<JsonWebToken, Long> {
}

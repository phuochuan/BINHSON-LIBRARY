package com.library.binhson.userservice.repository;

import com.library.binhson.userservice.entity.Authorization;
import com.library.binhson.userservice.entity.AuthorizationPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, AuthorizationPrimaryKey> {
}

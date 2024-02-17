package com.library.binhson.userservice.repository;

import com.library.binhson.userservice.entity.CitizenIdentityCard;
import com.library.binhson.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenIdentityCardRepository extends JpaRepository<CitizenIdentityCard, String> {
    boolean existsByUser(User currentUser);
}

package com.library.binhson.userservice.repository;

import com.library.binhson.userservice.entity.ClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientDetails, String> {
}

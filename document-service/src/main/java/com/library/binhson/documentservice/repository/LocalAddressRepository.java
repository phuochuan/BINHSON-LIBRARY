package com.library.binhson.documentservice.repository;

import com.library.binhson.documentservice.entity.LocalAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalAddressRepository extends JpaRepository<LocalAddress, Long> {
}

package com.library.binhson.documentservice.repository;

import com.library.binhson.documentservice.entity.PhysicalBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicalBookRepository extends JpaRepository<PhysicalBook, String> {
}

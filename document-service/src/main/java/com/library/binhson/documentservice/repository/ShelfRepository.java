package com.library.binhson.documentservice.repository;

import com.library.binhson.documentservice.entity.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, String> {
}

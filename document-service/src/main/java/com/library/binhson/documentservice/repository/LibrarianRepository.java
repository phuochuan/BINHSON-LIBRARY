package com.library.binhson.documentservice.repository;

import com.library.binhson.documentservice.entity.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian,String> {
    Optional<Librarian> findByUsername(String librarianUserName);
}

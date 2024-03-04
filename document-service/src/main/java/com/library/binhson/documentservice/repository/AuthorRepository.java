package com.library.binhson.documentservice.repository;

import com.library.binhson.documentservice.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
    boolean existsByName(String name);

    Optional<Author> findByName(String name);
}

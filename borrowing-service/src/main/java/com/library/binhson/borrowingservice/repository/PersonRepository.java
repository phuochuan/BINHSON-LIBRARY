package com.library.binhson.borrowingservice.repository;


import com.library.binhson.borrowingservice.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {
    Optional<Person> findByUsername(String username);
}

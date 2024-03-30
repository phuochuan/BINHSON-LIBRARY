package com.library.binhson.borrowingservice.repository;

import com.library.binhson.borrowingservice.entity.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian,String > {
}

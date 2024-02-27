package com.library.binhson.documentservice.repository;

import com.library.binhson.documentservice.entity.EBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EBookRepository extends JpaRepository<EBook,String
        > {
}

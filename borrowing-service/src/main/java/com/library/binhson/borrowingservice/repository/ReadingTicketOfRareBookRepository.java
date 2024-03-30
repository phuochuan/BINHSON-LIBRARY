package com.library.binhson.borrowingservice.repository;

import com.library.binhson.borrowingservice.entity.ReadingTicketOfRareBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingTicketOfRareBookRepository extends JpaRepository<ReadingTicketOfRareBook, Long> {
}

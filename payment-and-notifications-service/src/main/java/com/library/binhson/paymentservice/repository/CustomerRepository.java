package com.library.binhson.paymentservice.repository;

import com.library.binhson.paymentservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<User, String> {
    List<User> findOnBirthDate(int day, int month);
}

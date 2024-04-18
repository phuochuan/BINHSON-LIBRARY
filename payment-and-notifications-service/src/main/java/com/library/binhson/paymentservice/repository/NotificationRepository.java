package com.library.binhson.paymentservice.repository;

import com.library.binhson.paymentservice.entity.Event;
import com.library.binhson.paymentservice.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications, Long> {
    List<Notifications> findByEvent(Event event);

    List<Notifications> findByUsername(String username);
}

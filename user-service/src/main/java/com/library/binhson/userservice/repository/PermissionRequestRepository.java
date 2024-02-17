package com.library.binhson.userservice.repository;

import com.library.binhson.userservice.entity.PermissionRequest;
import com.library.binhson.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRequestRepository extends JpaRepository<PermissionRequest, Long> {
    boolean existsByUser(User currentUser);

    List<PermissionRequest> findByUser(User currentUser);

}

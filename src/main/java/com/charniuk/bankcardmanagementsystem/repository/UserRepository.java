package com.charniuk.bankcardmanagementsystem.repository;

import com.charniuk.bankcardmanagementsystem.model.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  boolean existsByEmail(String email);
  Optional<User> findByEmail(String email);
}

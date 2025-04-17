package com.charniuk.bankcardmanagementsystem.repository;

import com.charniuk.bankcardmanagementsystem.model.CardBlockRequest;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardBlockRequestRepository extends JpaRepository<CardBlockRequest, UUID> {

}

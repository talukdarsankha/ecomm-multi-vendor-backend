package com.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyz.models.Deal;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {

}

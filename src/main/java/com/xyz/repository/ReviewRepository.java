package com.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyz.models.Review;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	

}

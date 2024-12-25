package com.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyz.models.PaymentOrder;


@Repository
public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {       

}

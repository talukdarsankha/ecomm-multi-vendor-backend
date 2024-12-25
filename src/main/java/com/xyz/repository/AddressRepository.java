package com.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyz.models.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}

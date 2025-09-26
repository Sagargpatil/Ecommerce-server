package com.sp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sp.model.Address;



public interface AddressRepository extends JpaRepository<Address, Long>{

}

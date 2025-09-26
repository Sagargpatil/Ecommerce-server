package com.sp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sp.model.OrderItem;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}

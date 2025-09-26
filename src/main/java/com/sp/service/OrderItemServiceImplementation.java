package com.sp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.model.OrderItem;
import com.sp.repository.OrderItemRepository;



@Service
public class OrderItemServiceImplementation implements OrderItemService {

	@Autowired
	private OrderItemRepository orderItemRepository;
//	public OrderItemServiceImplementation(OrderItemRepository orderItemRepository) {
//		this.orderItemRepository=orderItemRepository;
//	}
	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		
		return orderItemRepository.save(orderItem);
	}


}

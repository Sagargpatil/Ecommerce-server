package com.sp.service;

import com.sp.exception.ProductException;
import com.sp.model.Cart;
import com.sp.model.User;
import com.sp.request.AddItemRequest;

public interface CartService {

	public Cart createCart(User user);
	public String addCartItem(Long userId,AddItemRequest req)throws ProductException;
	
	public Cart findUserCart(Long userid);
}
  
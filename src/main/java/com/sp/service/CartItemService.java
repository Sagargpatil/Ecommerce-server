package com.sp.service;

import com.sp.exception.CartItemException;
import com.sp.exception.UserException;
import com.sp.model.Cart;
import com.sp.model.CartItem;
import com.sp.model.Product;

public interface CartItemService {

	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userid,Long id,CartItem cartItem)throws CartItemException,UserException;
	
	public CartItem isCartItemExist(Cart cart,Product product,String size,Long userId);
	
	public void removeCartItem(Long userId,Long cartItemId)throws CartItemException,UserException;
	
	public CartItem findCartItemById(Long cartItem)throws CartItemException;
	
}

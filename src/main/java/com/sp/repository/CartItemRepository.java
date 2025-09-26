package com.sp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sp.model.Cart;
import com.sp.model.CartItem;
import com.sp.model.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

//	@Param("paramName") connects a method parameter to the named parameter (:paramName) in the @Query string.
//
//	It’s only needed when using @Query with named parameters.

//	@Param("cart") Cart cart
//	This tells Spring to bind the method parameter to the :cart named parameter in the query.
//
//	If you skip @Param, Spring won’t know which value to inject into :cart, and you'll get an error.

	@Query("SELECT ci From CartItem ci Where ci.cart=:cart And ci.product=:product And ci.size=:size And ci.userId=:userId")
	
	public CartItem isCartItemExist(
			@Param("cart") Cart cart, 
			@Param("product") Product product,
			@Param("size") String size,
			@Param("userId") Long userId);
}

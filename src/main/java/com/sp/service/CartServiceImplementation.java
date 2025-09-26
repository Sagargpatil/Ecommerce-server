package com.sp.service;

import org.springframework.stereotype.Service;

import com.sp.exception.ProductException;
import com.sp.model.Cart;
import com.sp.model.CartItem;
import com.sp.model.Product;
import com.sp.model.User;
import com.sp.repository.CartRepository;
import com.sp.request.AddItemRequest;


@Service
public class CartServiceImplementation implements CartService {

	
	private CartRepository cartRepository;
	private CartItemService cartItemService;
	private ProductService productService;
	
	public CartServiceImplementation(
			CartRepository cartRepository,
			CartItemService cartItemService,
			ProductService productService)
	{
		this.cartRepository=cartRepository;
		this.cartItemService=cartItemService;
		this.productService=productService;
	}
	
	@Override
	public Cart createCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
		Cart createdCart=cartRepository.save(cart);
		return createdCart;
	}
	
	
	
	@Override
	public Cart findUserCart(Long userId) {
		Cart cart= cartRepository.findByUserId(userId);
		
		int totalPrice=0;
		int totalDiscountedPrice=0;
		int totalItem=0;
		
		for(CartItem cartItem :cart.getCartItems()) {
			totalPrice+=cartItem.getPrice();
			totalDiscountedPrice+=cartItem.getDiscountedPrice();
			totalItem+=cartItem.getQuantity();
			}
		
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscounte(totalPrice-totalDiscountedPrice);
		
		return cartRepository.save(cart);
	}


	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		
		Cart cart=cartRepository.findByUserId(userId);
		System.out.println(cart);
		Product product = productService.findProductById(req.getProductid());
		System.out.println(product.getId());
		CartItem isPresent=cartItemService.isCartItemExist(cart, product,req.getSize(),userId);
		
		if(isPresent==null) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);
			
			int price=req.getQuantity()*product.getDiscountedPrice();
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize());
			
			CartItem createdCartItem = cartItemService.createCartItem(cartItem);
			cart.getCartItems().add(createdCartItem);
			
		}
		return "Item Add To Cart";
	}

	
}

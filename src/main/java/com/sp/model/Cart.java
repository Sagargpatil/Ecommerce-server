package com.sp.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

//USER---->(single)CART--->(multiple)CART_ITEM---->(quntity of products))PRODUCT---->(MENS,WOMENS)CATEGORY
@Entity
public class Cart {
//id for cart
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "cart_items")
    private Set<CartItem> cartItems = new HashSet<>();

    @Column(name = "total_price")
    private double totalPrice;
    
    @Column(name="total_item")
    private int totalItem;
    
    private int totalDiscountedPrice;
    
    private int discounte;
    
	public Cart() {
		// TODO Auto-generated constructor stub
	}

	public Cart(Long id, User user, Set<CartItem> cartItems, double totalPrice, int totalItem) {
		super();
		this.id = id;
		this.user = user;
		this.cartItems = cartItems;
		this.totalPrice = totalPrice;
		this.totalItem = totalItem;
	}

	public int getTotalDiscountedPrice() {
		return totalDiscountedPrice;
	}

	public void setTotalDiscountedPrice(int totalDiscountedPrice) {
		this.totalDiscountedPrice = totalDiscountedPrice;
	}
	public int getDiscounte() {
		return discounte;
	}

	public void setDiscounte(int discounte) {
		this.discounte = discounte;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}

	
}


//✅ What the Code Represents
//This code defines a Cart entity that:
//
//Belongs to one user.
//
//Has many cart items.
//
//When the Cart is saved/deleted, so are its CartItems.
//
//If any CartItem is removed from the set, it will also be removed from the database.
//
//🔁 Summary Table
//Code Part	Meaning
//@OneToOne(fetch = FetchType.LAZY)	One cart belongs to one user, loaded lazily
//@JoinColumn(name = "user_id", nullable = false)	Foreign key column user_id, not nullable
//private User user;	Reference to the related user
//@OneToMany(...)	One cart can have many cart items
//mappedBy = "cart"	Inverse side of the relation; mapped in CartItem
//cascade = CascadeType.ALL	Propagate all operations to CartItems
//orphanRemoval = true	Remove cart items from DB when removed from set
//@Column(name = "cart_items")	❌ Invalid – remove it
//Set<CartItem> cartItems = new HashSet<>()	Collection of cart items



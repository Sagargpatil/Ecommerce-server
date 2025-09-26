package com.sp.model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
    private String firstName;
    
    private String lastName;
    
    private String password;
    
    private String email;
    
    private String role;
    
    private String mobile;
    
    @OneToMany(mappedBy="user",cascade=CascadeType.ALL)
    private List<Address> address = new ArrayList<>();
    
    @Embedded
    @ElementCollection
    @CollectionTable(name="payment_information",joinColumns = @JoinColumn(name="user_id"))
    private List<PaymentInformation> paymentInformatio= new ArrayList<>(); 
    
    @OneToMany(mappedBy="user",cascade=CascadeType.ALL)
    @JsonIgnore
    private List<Rating> rating = new ArrayList<>();
    
    
    @OneToMany(mappedBy="user",cascade=CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();
    
    
    private LocalDateTime createAt;


	public User() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public List<Address> getAddress() {
		return address;
	}


	public void setAddress(List<Address> address) {
		this.address = address;
	}


	public List<PaymentInformation> getPaymentInformatio() {
		return paymentInformatio;
	}


	public void setPaymentInformatio(List<PaymentInformation> paymentInformatio) {
		this.paymentInformatio = paymentInformatio;
	}


	public List<Rating> getRating() {
		return rating;
	}


	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}


	public List<Review> getReviews() {
		return reviews;
	}


	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}


	public LocalDateTime getCreateAt() {
		return createAt;
	}


	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}
    

}

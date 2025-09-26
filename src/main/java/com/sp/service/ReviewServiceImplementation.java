package com.sp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sp.exception.ProductException;
import com.sp.model.Product;
import com.sp.model.Review;
import com.sp.model.User;
import com.sp.repository.ProductRepository;
import com.sp.repository.ReviewRepository;
import com.sp.request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService{

	private ReviewRepository reviewRepository;
	private ProductService productService;
	private ProductRepository productRepository;
	
	public ReviewServiceImplementation(ReviewRepository reviewRepository,
			ProductService productService,ProductRepository productRepository) {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {
		Product product = productService.findProductById(req.getProductId());
		Review review = new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
		
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {
		// TODO Auto-generated method stub
		return reviewRepository.getAllProductsReview(productId);
	}

}

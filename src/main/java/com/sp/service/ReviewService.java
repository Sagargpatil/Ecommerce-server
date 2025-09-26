package com.sp.service;

import java.util.List;

import com.sp.exception.ProductException;
import com.sp.model.Review;
import com.sp.model.User;
import com.sp.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest req,User user)throws ProductException;
	public List<Review> getAllReview(Long productId);
}

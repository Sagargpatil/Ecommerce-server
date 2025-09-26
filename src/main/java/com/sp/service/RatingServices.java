package com.sp.service;

import java.util.List;

import com.sp.exception.ProductException;
import com.sp.model.Rating;
import com.sp.model.User;
import com.sp.request.RatingRequest;

public interface RatingServices {

	public Rating createRating(RatingRequest req,User user) throws ProductException;
    public List<Rating> getProductsRating(Long productId);
}

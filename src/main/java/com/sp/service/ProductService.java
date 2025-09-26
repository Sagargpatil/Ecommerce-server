package com.sp.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.sp.exception.ProductException;
import com.sp.model.Product;
import com.sp.request.CreateProductRequest;

public interface ProductService {
	
	// only for admin
	public Product createProduct(CreateProductRequest req);
	
	public String deleteProduct(Long productid) throws ProductException;

	public Product updateProduct(Long productid,Product req) throws ProductException;
	
	public List<Product> getAllProducts();
	
	
	// for user and admin both
	
	public Product findProductById(Long id) throws ProductException;
	
	public List<Product> findProductByCategory(String category);
	
	public List<Product> searchProduct(String query);
	
	public Page<Product>getAllProduct(String category,List<String>colors,List<String>sizes,Integer minPrice,
			Integer maxPrice,Integer minDiscount,String sort,String stock,Integer pageNumber,Integer pageSize);
}

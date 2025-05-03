package com.api.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.dto.request.ProductRequestDTO;
import com.api.ecommerce.entity.Product;
import com.api.ecommerce.repo.ProductRepo;
import com.api.ecommerce.response.Response;

@Service
public class ProductService {

	private ProductRepo repo;
	
	@Autowired
	private CommonService commonService;

	public ProductService(ProductRepo repo) {
		this.repo = repo;
	}

	public ResponseEntity<?> retrieveAllProducts(){
		Response<List<Product>> response = new Response<List<Product>>();

		try {
			List<Product> products = repo.findAll();
			if(products == null || products.isEmpty()) {
				response.setMessage(CommonConstant.NO_PRODUCTS_FOUND);
			} else {
				response.setMessage(CommonConstant.RETRIEVE_ALL_PRODUCTS_SUCCESS);
			}
			response.setSuccess(true);
			response.setData(products);
		} catch(Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			return ResponseEntity.internalServerError().body(response);
		}
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> retrieveProductById(Long id) {
		Response<Product> response = new Response<Product>();
		
		try {
			Product product = repo.findById(id).orElseThrow(() -> new RuntimeException(CommonConstant.NO_PRODUCT_FOUND));
			response.setMessage(CommonConstant.RETRIVE_PRODUCT_SUCCESS);
			response.setSuccess(true);
			response.setData(product);
		}catch (RuntimeException e) {
			response.setSuccess(true);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			response.setErrorTrace(e.getStackTrace().toString());
			return ResponseEntity.internalServerError().body(response);
		}
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> addProduct(ProductRequestDTO productDto) {
		
		Response<Product> response = new Response<Product>();
		try {
			Product product = commonService.convertProductRequestDTOToProduct(productDto);
			repo.save(product);
			response.setMessage(CommonConstant.PRODUCT_ADDED_SUCCESS);
			response.setSuccess(true);
			response.setData(product);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			return ResponseEntity.internalServerError().body(response);
		}
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> updateProduct(Long id, ProductRequestDTO productDto) {
		
		Response<Product> response = new Response<Product>();
		try {
			
			Product product = repo.findById(id).orElseThrow(() -> new RuntimeException(CommonConstant.NO_PRODUCT_FOUND));
			
			if(productDto.getProductName() != null && commonService.isUpdated(product.getProductName(), productDto.getProductName())) {
				product.setProductName(productDto.getProductName());
			}
			
			if(productDto.getDescription() != null && commonService.isUpdated(product.getDescription(), productDto.getDescription())) {
				product.setDescription(productDto.getDescription());
			}
			
			if(productDto.getPrice() != null && commonService.isUpdated(product.getPrice(), productDto.getPrice())) {
				product.setPrice(productDto.getPrice());
			}
			
			if(productDto.getStock() != null && commonService.isUpdated(product.getStock(), productDto.getStock())) {
				product.setStock(productDto.getStock());
			}
			
			repo.save(product);
			
			response.setMessage(CommonConstant.PRODUCT_UPDATE_SUCCESS);
			response.setSuccess(true);
			response.setData(product);
			
		} catch (RuntimeException e) {
			response.setSuccess(true);
			response.setMessage(e.getMessage());
			
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			return ResponseEntity.internalServerError().body(response);
		}
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> removeProduct(Long productId) {
		
		
		Response<Product> response = new Response<Product>();
		try {
			repo.findById(productId).orElseThrow(() -> new RuntimeException(CommonConstant.NO_PRODUCT_FOUND));
			repo.deleteById(productId);
			response.setMessage(CommonConstant.PRODUCT_DELETE_SUCCESS);
			response.setSuccess(true);
			
		} catch (RuntimeException e) {
			response.setSuccess(true);
			response.setMessage(e.getMessage());
			
		}catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			return ResponseEntity.internalServerError().body(response);
		}
		
		return ResponseEntity.ok().body(response);
	}
	
	
}

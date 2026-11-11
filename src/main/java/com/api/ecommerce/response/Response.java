package com.api.ecommerce.response;

import lombok.Data;

@Data
public class Response<T> {
	
	private boolean success;
	private String message;
	private String errorTrace;
	private T responseData;
}

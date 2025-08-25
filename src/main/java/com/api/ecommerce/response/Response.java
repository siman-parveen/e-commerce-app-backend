package com.api.ecommerce.response;

public class Response<T> {
	
	private boolean success;
	private String message;
	private String errorTrace;
	private T responseData;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
//	public T getData() {
//		return responseData;
//	}
	public void setData(T data) {
		this.responseData = data;
	}
	public String getErrorTrace() {
		return errorTrace;
	}
	public void setErrorTrace(String errorTrace) {
		this.errorTrace = errorTrace;
	}
	public T getResponseData() {
		return responseData;
	}
	
}

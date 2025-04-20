package com.api.ecommerce.common;

import org.springframework.stereotype.Service;

@Service
public class CommonService {
	
	public boolean isUpdated(Object oldValue, Object newValue) {
		return (!oldValue.equals(newValue));
	}


}

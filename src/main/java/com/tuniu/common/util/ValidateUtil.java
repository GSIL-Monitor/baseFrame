package com.tuniu.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidateUtil {
	
	public static Map<String,String> getErrors(BindingResult result){
		Map<String,String> map = new HashMap<String, String>();
		List<FieldError> list = result.getFieldErrors();
		for(FieldError error:list){
//			System.out.println("error.getFiled : " + error.getField());
//			System.out.println("error.getDefaultMessage : " + error.getDefaultMessage());
			map.put(error.getField(), error.getDefaultMessage());
		}
		return map;
	}

}

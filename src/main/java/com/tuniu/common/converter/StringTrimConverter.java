package com.tuniu.common.converter;

import org.springframework.core.convert.converter.Converter;

public class StringTrimConverter implements Converter<String, String> {

	@Override
	public String convert(String source) {
		if(!"".equals(source) && source!=null){
			return source.trim();
		}
		return null;
	}

}

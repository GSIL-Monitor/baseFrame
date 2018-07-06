package com.tuniu.common.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import com.tuniu.common.util.LogUtil;

public class DateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			
			if(source.matches("\\d{4}-\\d{1,2}-\\d{1,2}"))
				return format1.parse(source);
			else if(source.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}"))
				return format2.parse(source);
			else if(source.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
				return format3.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
			LogUtil.error(e.getLocalizedMessage());
		}
		return null;
	}

}

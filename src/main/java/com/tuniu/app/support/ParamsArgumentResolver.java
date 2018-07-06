package com.tuniu.app.support;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;
import com.tuniu.app.annotation.Params;

public class ParamsArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(Params.class)!=null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		String qryString = request.getQueryString();
		if(List.class.equals(parameter.getParameterType())){
			Type type = parameter.getGenericParameterType();//泛型类型 java.util.List<com.tuniu.Model.User>
			Type[] types = ((ParameterizedType)type).getActualTypeArguments();//泛型的参数类型 com.tuniu.Model.User
			//String clzName =  types[0].getTypeName();//need java8 support
			String clzName = types[0].toString().substring(types[0].toString().indexOf(" ")+1);
			return JSON.parseArray(qryString, Class.forName(clzName));
		}
		return JSON.parseObject(qryString, parameter.getParameterType());
	}

}

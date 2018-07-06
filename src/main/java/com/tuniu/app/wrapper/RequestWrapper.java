package com.tuniu.app.wrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 解析用base64编码后json格式的request（包装请求）
 * 例 : 			{"id":100 , "userName":"liw"}
 * base64编码 :   eyJpZCI6MTAwICwgInVzZXJOYW1lIjoibGl3In0=
 * @author liwang2
 *
 */
public class RequestWrapper extends HttpServletRequestWrapper {

	private String queryString;

	private Map<String, String[]> parameters = new HashMap<>();

	public RequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		parseJson(request);
	}

	private void parseJson(HttpServletRequest request) throws UnsupportedEncodingException {
		String method = this.getMethod();
		StringBuilder str = new StringBuilder();
		String qryStr = super.getQueryString();
		if("GET".equals(method)){
			str.append(StringUtils.isNotEmpty(qryStr) ? qryStr : "");
			queryString = URLDecoder.decode(str.toString(), "utf-8");
		}else{
			Enumeration<String> en = request.getParameterNames();
			if(en!=null && en.hasMoreElements()){
				queryString = en.nextElement();
			}
		}
		if (StringUtils.isNotEmpty(queryString) && Base64.isBase64(queryString)) {
			String filterStr = queryString.replaceAll(" ", "+");
			queryString = new String(Base64.decodeBase64(filterStr));
		}
		if (StringUtils.isNotEmpty(queryString)) {
			if(!queryString.matches("^\\[.+\\]$")){// 判断是否为数组json字符串
				JSONObject obj = JSON.parseObject(queryString);
				for (String key : obj.keySet()) {
					parameters.put(key, new String[] { obj.getString(key) });
				}
			}
		}
	}

	@Override
	public String[] getParameterValues(String name) {
		String value = getParameter(name);
		if (StringUtils.isNotEmpty(value)) {
			return new String[] { value };
		}
		return super.getParameterValues(name);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		for(Entry<String, String[]> en :parameters.entrySet()){
			String key = en.getKey();
			System.out.print("key : " + key + " = ");
			String[] values = en.getValue();
			for(String val : values){
				System.out.print(val);
			}
		System.out.println("");
		}
		return parameters;
	}
	
	public Map<String, String[]> getParameters() {
		return parameters;
	}

	@Override
	public String getQueryString() {
		return queryString;
	}

	@Override
	public String getParameter(String name) {
		String[] str = parameters.get(name);
		if(str==null){
			return super.getParameter(name);
		}
		return parameters.get(name)[0];
	}
	
}

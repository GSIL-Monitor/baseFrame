package baseFrame.test.wrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

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
		StringBuilder str = new StringBuilder();
		String qryStr = super.getQueryString();
		str.append(StringUtils.isNotEmpty(qryStr) ? qryStr : "");
		queryString = URLDecoder.decode(str.toString(), "utf-8");
		if (StringUtils.isNotEmpty(queryString) && Base64.isBase64(queryString)) {
			queryString = new String(Base64.decodeBase64(queryString));
		}
		if (StringUtils.isNotEmpty(queryString)) {
			JSONObject obj = JSON.parseObject(queryString);
			for (String key : obj.keySet()) {
				parameters.put(key, new String[] { obj.getString(key) });
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
		return parameters;
	}

	@Override
	public String getQueryString() {
		return queryString;
	}

	@Override
	public String getParameter(String name) {
		return parameters.get(name)[0];
	}

}

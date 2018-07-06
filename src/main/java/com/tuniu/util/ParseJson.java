package com.tuniu.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tuniu.common.model.BaseData;

public class ParseJson {

	public static BaseData toBaseData(String jsonStr){		
		JSONObject json = JSON.parseObject(jsonStr);
		String id = json.getString("id");
		String name = json.getString("name");
		BaseData bd = new BaseData(id, name);
		return bd;
	}
}

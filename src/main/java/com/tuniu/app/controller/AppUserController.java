package com.tuniu.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.tuniu.app.annotation.Params;
import com.tuniu.common.model.User;

@RequestMapping("/app/user")
@RestController
public class AppUserController {

	@RequestMapping("/testObj")
	public String test(@Params User user){
		System.out.println(JSON.toJSONString(user));
		return JSON.toJSONString(user,true);
	}
	
	@RequestMapping("testList")
	public String test(@Params List<User> userList){
		for(User u : userList){
			System.out.println(u);
		}
		return JSON.toJSONString(userList,true);
	}
	
	@RequestMapping("/test")
	public String test(int id,String username,String usercode){
		System.out.println(id +","+username+","+usercode);
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		user.setUsercode(usercode);
		return JSON.toJSONString(user,true);
	}
}

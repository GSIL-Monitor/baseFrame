package com.tuniu.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/chat")
@Controller
public class ChatController {

	@RequestMapping("/index")
	public String index(){
		return "chat/index";
	}
}

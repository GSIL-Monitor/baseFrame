package com.tuniu.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/interface")
@Controller
public class InterfaceController {

	@RequestMapping("/index")
	public String index(){
		return "interface/index";
	}
}

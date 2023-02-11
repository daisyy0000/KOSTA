package com.my.customer.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.control.Controller;
import com.my.customer.service.CustomerService;

public class CheckLoginedController implements Controller{
	private CustomerService service;
	public CheckLoginedController() {
		service = new CustomerService();
	}
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "http://192.168.0.20:5500");
		response.addHeader("Access-Control-Allow-Credentials", "true");//쿠키허용

		//로그인된경우 사용될 session 속성은(속성명: "logined", 값: 로그인된 고객정보)이다	
		HttpSession session = request.getSession();
		String logined = (String)session.getAttribute("logined");
		
		//로그인된경우 응답할 json 프로퍼티는 status과 logined이다
		Map<String, Object> map = new HashMap<>();
		if(logined != null) {
			map.put("status", 1);
			map.put("logined", logined);
		}else { //로그인안된 경우 응답할 json프로퍼티는 status이다
			map.put("status", 0);
		}
		
		return new ObjectMapper().writeValueAsString(map);
	}

}
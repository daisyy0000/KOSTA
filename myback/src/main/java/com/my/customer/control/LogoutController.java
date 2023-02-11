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
import com.my.customer.vo.Customer;
import com.my.exception.FindException;

public class LogoutController implements Controller {
	private CustomerService service;
	public LogoutController() {
		service = new CustomerService();
	}
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html; charset=utf-8");
											//프론트쪽 iP를 적어야함 addheader
		response.addHeader("Access-Control-Allow-Origin", "http://192.168.0.20:5500");
		response.addHeader("Access-Control-Allow-Credentials","true");
		
		HttpSession session = request.getSession();
		System.out.println("로그아웃시 sessionid: "+ session.getId());
		//세션 강제종료
		session.invalidate();
		return "";  //null로해도 관계는 없다. 
	}

}

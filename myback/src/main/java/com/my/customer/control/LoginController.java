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

public class LoginController implements Controller {
	private CustomerService service;
	public LoginController() {
		service = new CustomerService();
	}
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("application/json; charset=utf-8");
											//프론트쪽 iP를 적어야함 addheader
		response.addHeader("Access-Control-Allow-Origin", "http://192.168.0.20:5500");
		response.addHeader("Access-Control-Allow-Credentials","true");
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		HttpSession session = request.getSession();
		//이미로그인했는데 이전에 로그인한 걸 지우는..
		session.removeAttribute("logined");
		try {
			Customer c = service.login(id, pwd);
			session.setAttribute("logined", c.getId());
			System.out.println("로그인성공시, sessionid: " + session.getId());
			result = mapper.writeValueAsString(c);
			
		} catch (FindException e) {
			e.printStackTrace();
			Map<String, Object> map = new HashMap<>();
			map.put("status", -1);
			map.put("msg", e.getMessage());
			result = mapper.writeValueAsString(map);
			
		}
		return result;
	}

}

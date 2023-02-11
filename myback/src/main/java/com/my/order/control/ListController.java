package com.my.order.control;
//01.27.금
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.control.Controller;
import com.my.exception.FindException;
import com.my.order.service.OrderService;
import com.my.order.vo.OrderInfo;

public class ListController implements Controller {

	private OrderService service;

	public ListController() {
		service = new OrderService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("application/json; charset=utf-8");
		// 프론트쪽 iP를 적어야함 addheader
		response.addHeader("Access-Control-Allow-Origin", "http://192.168.0.18:5500");
		response.addHeader("Access-Control-Allow-Credentials", "true");

		HttpSession session = request.getSession();
		String logined = (String) session.getAttribute("logined");
		ObjectMapper mapper = new ObjectMapper();
		String result;

		if (logined == null) {
			Map<String, Object> map = new HashMap<>();
			// 로그인이 안되었을때
			map.put("status", 2);
			map.put("msg", "로그인하세요");
			result = mapper.writeValueAsString(map);
		} else { // 로그인이 된경우
			try {
				List<OrderInfo> list = service.findByOrderId(logined);
				result = mapper.writeValueAsString(list);
			} catch (FindException e) {
				e.printStackTrace();
				Map<String, Object> map = new HashMap<>();
				map.put("status", -1);
				map.put("msg", e.getMessage());
				result = mapper.writeValueAsString(map);

			}

		}
		return result;
	}
}
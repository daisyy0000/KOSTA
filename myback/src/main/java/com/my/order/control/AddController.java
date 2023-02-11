package com.my.order.control;

//01.27.금
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.control.Controller;
import com.my.exception.AddException;
import com.my.order.service.OrderService;
import com.my.order.vo.OrderInfo;
import com.my.order.vo.OrderLine;
import com.my.product.vo.Product;

public class AddController implements Controller {
	private OrderService service;

	public AddController() {
		service = new OrderService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("application/json; charset=utf-8");
		// 프론트쪽 iP를 적어야함 addheader
		response.addHeader("Access-Control-Allow-Origin", "http://192.168.0.18:5500");
		response.addHeader("Access-Control-Allow-Credentials", "true");

		OrderInfo orderinfo = new OrderInfo();
		HttpSession session = request.getSession();
		Map<String, Object> map = new HashMap<>();// 응답용 맵

		String logined = (String) session.getAttribute("logined");
		if (logined == null) {
			// 로그인이 안되었을때
			map.put("status", 2);
			map.put("msg", "로그인하세요");
		} else { // 로그인이 된경우

			orderinfo.setOrderId(logined); // 주문자ID

			// 주문자 카트정보 꺼내오기
			Map<String, Integer> cart = (Map) session.getAttribute("cart"); // 장바구니용 맵

			if (cart != null && cart.size() != 0) { // 장바구니 내용이 존재하는 경우에만 주문한다
				List<OrderLine> lines = new ArrayList<>();
				for (String prodNo : cart.keySet()) {
					Integer quantity = cart.get(prodNo);
					OrderLine line = new OrderLine();
					Product p = new Product();
					p.setProdNo(prodNo);
					line.setOrderP(p);
					line.setOrderQuantity(quantity);
					lines.add(line);
				}
				orderinfo.setLines(lines); // 주문상품들

				try {
					service.add(orderinfo);
					// 주문이 성공한 경우

					// 장바구니를 비우기
					session.removeAttribute("cart");
					map.put("status", 1);
				} catch (AddException e) {
					e.printStackTrace();
					map.put("status", -1);
					map.put("msg", e.getMessage());
				}
				// 장바구니가 아예 없거나, 비어있는 경우
				// 프론트단에서도 장바구니가 비어있으면 버튼이 안보이게 처리해야되지만
				// 백엔드단에서도 필터링or 유효성검사가 필요하다! 너무 프론트단에만 의존하지말기
			} else {
				map.put("status", -1);
				map.put("msg", "장바구니가 비었습니다");
			}
		}

		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(map);
		return result;

	}
}

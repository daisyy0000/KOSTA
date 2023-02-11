//01.26.목
package com.my.cart.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.control.Controller;

public class ListController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("application/json; charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "http://192.168.0.18:5500");
		response.addHeader("Access-Control-Allow-Credentials", "true");

		HttpSession session = request.getSession();
		System.out.println("장바구니목록 세션:" + session.getId());
		ObjectMapper mapper = new ObjectMapper();
		String result;
		Map<String, Integer> cart = (Map) session.getAttribute("cart");
		if (cart == null) {
			Map<String, Object> map = new HashMap<>();
			map.put("status", -1);
			map.put("msg", "장바구니가 비었습니다");
			
			result = mapper.writeValueAsString(map);

		} else {
			List<Map<String, Object>> list = new ArrayList<>();
			Set<String> keys = cart.keySet(); // key들

			for (String prodNo : keys) {
				Integer quantity = cart.get(prodNo);
				Map<String, Object> subMap = new HashMap<>();
				subMap.put("prodNo", prodNo);
				subMap.put("quantity", quantity);
				list.add(subMap);
			}
			result = mapper.writeValueAsString(list);
		}
		// String result = mapper.writeValueAsString(cart);

		// Map<Product, Integer>resultMap = new HashMap<>();
		// 장바구니의 상품번호별 상품상세정보얻기
		// for(String prodNo: cart.keySet()) {
		// Product p = session.selectOne(" ", prodNo);
		// resultMap.put(p, cart.get(prodNo));
		// }
//		ObjectMapper mapper = new ObjectMapper();
//		String result = mapper.writeValueAsString(resultMap);
		return result;
	}

}
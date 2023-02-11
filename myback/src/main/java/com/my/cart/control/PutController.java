//01.26.목
package com.my.cart.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.my.control.Controller;

public class PutController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		//응답안할거니깐 text/html
		response.setContentType("text/html;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "http://192.168.0.18:5500");
		response.addHeader("Access-Control-Allow-Credentials", "true");//쿠키허용
		
		String prodNo= request.getParameter("prodNo"); //상품번호
		String quantity= request.getParameter("quantity"); //수량
		
		HttpSession session = request.getSession();
		Map<String, Integer> cart = (Map)session.getAttribute("cart"); //장바구니
		if(cart == null) {
			cart = new HashMap<>();
			session.setAttribute("cart", cart);
		}
		
		int quantityNum = Integer.parseInt(quantity);
		Integer inte = (Integer) cart.get(prodNo);
		if(inte != null) { //장바구니에 이미 상품이 있는 있다면 수량만 증가
			quantityNum += inte;
		}
		cart.put(prodNo, quantityNum);
		
		return "";
	}

}

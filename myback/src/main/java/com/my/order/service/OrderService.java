package com.my.order.service;
import java.util.List;

//01.27.금
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.order.dao.OrderDAO;
import com.my.order.dao.OrderDAOOracle;
import com.my.order.vo.OrderInfo;

public class OrderService {
	private OrderDAO dao;
	public OrderService() {
		dao = new OrderDAOOracle();
	}
	public void add(OrderInfo orderinfo) throws AddException{
		dao.insert(orderinfo);
	}

	public List<OrderInfo> findByOrderId(String id) throws FindException{
		return dao.selectByOrderId(id);
	}
}
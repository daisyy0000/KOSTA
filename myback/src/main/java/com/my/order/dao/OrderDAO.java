package com.my.order.dao;
//01.26.목
import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.order.vo.OrderInfo;

public interface OrderDAO {
	void insert(OrderInfo orderInfo) throws AddException;
	List<OrderInfo> selectByOrderId(String id) throws FindException;
}

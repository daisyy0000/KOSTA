package com.my.customer.service;

import com.my.customer.dao.CustomerDAO;
import com.my.customer.dao.CustomerDAOOracle;
import com.my.customer.vo.Customer;
import com.my.exception.FindException;

public class CustomerService {
	private CustomerDAO dao;

	public CustomerService() {
		dao = new CustomerDAOOracle();
	}

	public Customer login(String id, String pwd) throws FindException {
		Customer c = dao.selectById(id);
		if(c.getPwd().equals(pwd)) {
			c.setPwd("");
			return c;
		}else {
			throw new FindException("로그인실패");
		}
	}

	public Customer findById(String id) throws FindException {
		Customer c = dao.selectById(id);
		return c;
	}

}

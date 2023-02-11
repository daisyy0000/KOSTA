package com.my.customer.dao;

import com.my.customer.vo.Customer;
import com.my.exception.FindException;

public interface CustomerDAO {
	public Customer selectById(String id) throws FindException;
}

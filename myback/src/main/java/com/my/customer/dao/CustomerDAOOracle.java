package com.my.customer.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.my.customer.vo.Customer;
import com.my.exception.FindException;

public class CustomerDAOOracle implements CustomerDAO {
	
	// 우클릭 convert~
	private SqlSessionFactory sqlSessionFactory;

	public CustomerDAOOracle() {
		String resource = "config.xml";
		InputStream inputStream;
		// 이부분에 문제가 나면 catch로 와버려서 sqlsession이 널값을 가지고 있게된다
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Customer selectById(String id) throws FindException {

		// 그래서 요구문에서 널포인트익셉션에 빠진다
		if (sqlSessionFactory == null) {
			throw new FindException("예외발생: sqlSessionFactory가 null입니다");
		}
		
		SqlSession session = sqlSessionFactory.openSession();
		Customer c = session.selectOne("com.my.customer.CustomerMapper.selectById", id);
		System.out.println(c);
		// 널체크 필수
		if (c != null) {
			session.close();
			return c;
		} else {
			throw new FindException("아이디에 해당하는 고객이 없습니다");
		}
	}

	// main 쓰고 ctrl+스페이스바
	public static void main(String[] args) {
		CustomerDAOOracle dao = new CustomerDAOOracle();
		try {
			System.out.println(dao.selectById("id1"));
		} catch (FindException e) {
			e.printStackTrace();
		}
	}
}

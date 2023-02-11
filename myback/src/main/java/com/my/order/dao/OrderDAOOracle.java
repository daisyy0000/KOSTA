package com.my.order.dao;

//01.26.목 ~27.금
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.order.vo.OrderInfo;
import com.my.order.vo.OrderLine;

public class OrderDAOOracle implements OrderDAO {
	private SqlSessionFactory sqlSessionFactory;

	public OrderDAOOracle() {
		String resource = "config.xml";
		InputStream inputStream;
		// 이부분에 문제가 나면 catch로 와버려서 sqlsession이 널값을 가지고 있게된다
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(OrderInfo orderInfo) throws AddException {
		// 트랜잭션 단위로 처리되어야 함

		if (sqlSessionFactory == null) {
			throw new AddException("예외발생:sqlSessionFactory가 null입니다");
		}
		SqlSession session = sqlSessionFactory.openSession();

		try {
			insertInfo(session, orderInfo);

			for (OrderLine orderLine : orderInfo.getLines()) {
				orderLine.setOrderNo(orderInfo.getOrderNo());
				insertLine(session, orderLine);
			}
			session.commit();
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	private void insertInfo(SqlSession session, OrderInfo orderInfo) throws Exception {
		session.insert("com.my.order.OrderMapper.insertInfo", orderInfo);
	}

	private void insertLine(SqlSession session, OrderLine orderLine) throws Exception {

		session.insert("com.my.order.OrderMapper.insertLine", orderLine);
	}

	@Override
	public List<OrderInfo> selectByOrderId(String id) throws FindException {
		if (sqlSessionFactory == null) {
			throw new FindException("예외발생:sqlSessionFactory가 null입니다");
		}
		SqlSession session = sqlSessionFactory.openSession();
		List<OrderInfo> list = session.selectList("com.my.order.OrderMapper.selectByOrderId", id);
		return list;
	}

	public static void main(String[] args) {
		OrderDAOOracle dao = new OrderDAOOracle();
		try {
			List<OrderInfo> list = dao.selectByOrderId("id1");
			for (OrderInfo info : list) {
				int orderNo = info.getOrderNo();
				Date orderDt = info.getOrderDt();
				List<OrderLine> lines = info.getLines();
				System.out.println(orderNo + ":" + orderDt);
				for(OrderLine line : lines) {
					System.out.println(line.getOrderNo() +":"+ line.getOrderQuantity() +":"+  line.getOrderP());
				}
			}	
		} catch (FindException e) {
			e.printStackTrace();
		}

//		OrderInfo orderinfo = new OrderInfo();
//		orderinfo.setOrderId("id1"); //주문자아이디
//		List<OrderLine> lines = new ArrayList<>();
//		
//		for(int i=1; i<=2; i++) {
//			OrderLine line = new OrderLine();
//			Product p = new Product();
//			p.setProdNo("C000"+i);
//			line.setOrderP(p);
//			line.setOrderQuantity(i);
//			lines.add(line);
//			
//		}
//		orderinfo.setLines(lines);
//		try {
//			dao.insert(orderinfo);
//		} catch (AddException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	}
}
package com.my.product.service;

import java.util.List;

import com.my.dto.PageBean;
import com.my.exception.FindException;
import com.my.product.dao.ProductDAO;
import com.my.product.dao.ProductDAOOracle;
import com.my.product.vo.Product;

public class ProductService {
	//public List<Product> findAll(~)
	//public Map<String, Object> findAll(int currentPage) throws FindException{
	public PageBean<Product> findAll(int currentPage) throws FindException{
		ProductDAO dao;
		dao = new ProductDAOOracle();
		/*
		 * currentPage  startRow  endRow
		 *     1            1       3
		 *     2            4       6
		 *     3            7       9 
		 */
		int cntPerPage = 3; //한 페이지에 보여줄 최대목록수
		//상품번호(시작~끝)
		int startRow = (currentPage-1)*cntPerPage + 1;
		int endRow = currentPage*cntPerPage;
		//return dao.selectAll(startRow, endRow);
		List<Product> list = dao.selectAll(startRow, endRow);
		
		int totalCnt = dao.totalCnt(); //총상품수
		/*int cntPerPageGroup = 2; //페이지그룹의 보여줄페이지수
		
		//hint: Math.ceil(), Math.floor()
		//정수를 정수로 나누면 정수
		//둘중하나는 더블로 해줘야지 나눠야 실수값이나옴
		
		//Math.ceil: 3.0 int로 형변환하면 3
		
		int totalPage = (int)Math.ceil((double)totalCnt/cntPerPage);
		int startPage = (currentPage-1)/cntPerPageGroup*cntPerPageGroup+1;
		int endPage = startPage + cntPerPageGroup -1 ;
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("totalCnt", totalCnt);
		map.put("totalPage", totalPage);
		map.put("startPage", startPage);
		map.put("endPage", endPage);
		map.put("currentPage", currentPage);
		return map;
		*/
		PageBean<Product> pb = 
				new PageBean(currentPage, list, totalCnt);
		return pb;
	}
	
	public Product info(String prodNo) throws FindException{
		ProductDAO dao;
		dao = new ProductDAOOracle();
		return dao.selectByProdNo(prodNo);
	}
}
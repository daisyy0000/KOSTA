package com.my.product.dao;

import java.util.List;

import com.my.exception.FindException;
import com.my.product.vo.Product;

public interface ProductDAO {
	/**
	 * 상품목록을 검색한다
	 * @param startRow 시작행
	 * @param endRow 끝행
	 * @return 상품목록
	 * @throws 상품목록 검색시 FindException예외발생한다
	 */
	public List<Product> selectAll(int startRow, int endRow) throws FindException;
	
	/**
	 * 총상품수를 검색한다
	 * @return
	 * @throws FindException
	 */
	public int totalCnt() throws FindException;
	
	
	/**
	 * 상품번호에 해당하는 상품을 검색
	 * @param prodNo 상품번호
	 * @return 상품
	 * @throws FindException
	 */
	public Product selectByProdNo(String prodNo)throws FindException;
}


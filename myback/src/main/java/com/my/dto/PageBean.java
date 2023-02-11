package com.my.dto;

import java.util.List;

import com.my.product.vo.Product;

import lombok.Getter;
@Getter
public class PageBean<T> {
	//상수선언한 이유: 변하지않은값들..
	public final static int CNT_PER_PAGE=3; //한페이지에 보여줄 최대목록수 3개상품을 보여줌
 	public final static int CNT_PER_PAGE_GROUP=2; // <이전> [1], [2] <다음> 2개!
	private List<T> list;
	private int totalCnt;
	private int totalPage;
	private int startPage;
	private int endPage;
	private int currentPage;
	
	public PageBean(int currentPage, List<T>list, int totalCnt) {
		this.currentPage = currentPage;
		this.list = list;
		this.totalCnt = totalCnt;
		//시작페이지~끝페이지
		totalPage = (int)Math.ceil((double)totalCnt/CNT_PER_PAGE);
		startPage = (currentPage-1)/CNT_PER_PAGE_GROUP*CNT_PER_PAGE_GROUP+1;
		endPage = startPage + CNT_PER_PAGE_GROUP -1 ;
	}
}

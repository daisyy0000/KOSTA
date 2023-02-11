package com.my.order.vo;
//01.26.목
import com.my.product.vo.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class OrderLine {
	private int orderNo;
	//private String orderProdNo;
	private Product orderP;
	private int orderQuantity;
}
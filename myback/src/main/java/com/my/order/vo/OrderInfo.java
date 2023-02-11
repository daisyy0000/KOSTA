package com.my.order.vo;
//01.27.금
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class OrderInfo {
	private int orderNo;
	private String orderId;
	@JsonFormat(pattern = "yy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
	private Date orderDt;
	private List<OrderLine> lines;
}

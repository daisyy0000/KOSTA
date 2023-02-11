package com.my.product.vo;      //병행해도된다(서비스, dao오라클)
//백엔드 작업순서: (db설계) > vo > dao인터페이스 > 서비스 > dao오라클 > 컨트롤러 순서 
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Product {
	
	 /*
	  * PROD_NO                                   NOT NULL VARCHAR2(5)
	 PROD_NAME                                 NOT NULL VARCHAR2(30)
	 PROD_PRICE                                         NUMBER(6)
	 PROD_DETAIL                                        VARCHAR2(50)
	 PROD_MF_DT                                        DATE
	 */ 
	
	private String prodNo;
	private String prodName;
	private int prodPrice;
	private String prodDetail;
	@JsonFormat(timezone = "Asia/Seoul", pattern ="yy-MM-dd")
	private Date prodMfDt;
	//java.util.Date;! sql.date아님
	

	
	
	
	
	
}

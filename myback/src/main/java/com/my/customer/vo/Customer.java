package com.my.customer.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString  //시스오찍어보면 @~~
public class Customer {
	private String id;
	private String pwd;
	private String name;
}

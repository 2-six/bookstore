package com.laver.bookstore.service;


import com.laver.bookstore.domain.OrderDetail;
import com.laver.bookstore.domain.OrderDetailExample;

import java.util.List;

public interface IOrderDetailService {
	int addOrderDetail(OrderDetail orderDetail);
	
	List<OrderDetail> selectByExample(OrderDetailExample example);
	
	OrderDetail selectByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(OrderDetail record);
	
	int deleteByOid(Integer oid);
}

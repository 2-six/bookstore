package com.laver.bookstore.service;

import com.laver.bookstore.domain.BookOrder;
import com.laver.bookstore.domain.BookOrderExample;

import java.util.List;

public interface IBookOrderService {
	int addBookOrder(BookOrder bookOrder);
	
	int selectOid(BookOrderExample example);
	
	List<BookOrder> selectByExample(BookOrderExample example);
	
	int deleteByPrimaryKey(Integer oid);
	
	BookOrder selectByPrimaryKey(Integer oid);
	
	int update( BookOrder record);
	

}

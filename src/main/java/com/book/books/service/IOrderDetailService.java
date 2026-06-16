package com.book.books.service;

import com.book.books.domain.OrderDetail;
import com.book.books.domain.OrderDetailExample;

import java.util.List;

public interface IOrderDetailService {
    int addOrderDetail(OrderDetail orderDetail);

    List<OrderDetail> selectByExample(OrderDetailExample example);

    OrderDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrderDetail record);

    int deleteByOid(Integer oid);
}
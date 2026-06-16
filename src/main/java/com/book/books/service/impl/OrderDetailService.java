package com.book.books.service.impl;

import com.book.books.domain.OrderDetail;
import com.book.books.domain.OrderDetailExample;
import com.book.books.domain.OrderDetailExample.Criteria;
import com.book.books.mapper.OrderDetailMapper;
import com.book.books.service.IOrderDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderDetailService implements IOrderDetailService{
    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Override
    public int addOrderDetail(OrderDetail orderDetail) {
        return orderDetailMapper.insert(orderDetail);
    }

    @Override
    public List<OrderDetail> selectByExample(OrderDetailExample example) {
        return orderDetailMapper.selectByExample(example);
    }

    @Override
    public OrderDetail selectByPrimaryKey(Integer id) {
        return orderDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(OrderDetail record) {
        return orderDetailMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteByOid(Integer oid) {
        OrderDetailExample example = new OrderDetailExample();
        Criteria cri = example.createCriteria();
        cri.andOrderIdEqualTo(oid);
        return orderDetailMapper.deleteByExample(example);
    }

}
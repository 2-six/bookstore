package com.book.books.service.impl;

import com.book.books.domain.BookOrder;
import com.book.books.domain.BookOrderExample;
import com.book.books.domain.BookOrderExample.Criteria;
import com.book.books.mapper.BookOrderMapper;
import com.book.books.service.IBookOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookOrderService implements IBookOrderService{

    @Resource
    private BookOrderMapper bookOrderMapper;

    @Override
    public int addBookOrder(BookOrder bookOrder) {
        return bookOrderMapper.insert(bookOrder);
    }

    @Override
    public int selectOid(BookOrderExample example) {
        List<BookOrder> bookOrders = bookOrderMapper.selectByExample(example);
        BookOrder bookOrder=bookOrders.get(0);
        return bookOrder.getOid();
    }

    @Override
    public List<BookOrder> selectByExample(BookOrderExample example) {
        return bookOrderMapper.selectByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer oid) {
        return bookOrderMapper.deleteByPrimaryKey(oid);
    }

    @Override
    public BookOrder selectByPrimaryKey(Integer oid) {
        return bookOrderMapper.selectByPrimaryKey(oid);
    }

    @Override
    public int update(BookOrder record) {
        System.out.println(record);
        BookOrder bookOrder = bookOrderMapper.selectByPrimaryKey(record.getOid());
        if(record.getOname()!=null&&!record.getOname().equals("")){
            bookOrder.setOname(record.getOname());
        }
        if(record.getAdress()!=null&&!record.getAdress().equals("")){
            bookOrder.setAdress(record.getAdress());
        }
        if(record.getStatus()!=null&&!record.getStatus().equals("")){
            bookOrder.setStatus(record.getStatus());
        }
        BookOrderExample example = new BookOrderExample();
        Criteria cri = example.createCriteria();
        cri.andOidEqualTo(record.getOid());
        return bookOrderMapper.updateByExample(bookOrder, example);
    }

}
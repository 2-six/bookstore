package com.book.books.service.impl;

import com.book.books.domain.Book;
import com.book.books.domain.BookExample;
import com.book.books.domain.BookExample.Criteria;
import com.book.books.mapper.BookMapper;
import com.book.books.service.IBookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookService implements IBookService{

    @Resource
    private BookMapper bookMapper;

    @Override
    public int addBook(Book book) {
        return bookMapper.insert(book);
    }

    @Override
    public List<Book> findAllBook() {
        BookExample example=new BookExample();
        List<Book> books=bookMapper.selectByExample(example);
        return books;
    }

    @Override
    public int delById(Integer bid) {
        return bookMapper.deleteByPrimaryKey(bid);
    }

    @Override
    public Book findById(Integer bid) {
        return bookMapper.selectByPrimaryKey(bid);
    }

    @Override
    public int modifyBook(Book book) {
        BookExample example=new BookExample();
        Criteria cri = example.createCriteria();
        cri.andBidEqualTo(book.getBid());
        return bookMapper.updateByExampleSelective(book, example);
    }

    @Override
    public Set<String> bookType() {
        Set<String> bts=new HashSet<String>();
        BookExample example=new BookExample();
        List<Book> books=bookMapper.selectByExample(example);
        for(Book book:books){
            bts.add(book.getType());
        }
        return bts;
    }

    @Override
    public List<Book> selectByExample(BookExample example) {
        return bookMapper.selectByExample(example);
    }

    @Override
    public boolean checkStore(Integer bid, Integer number){
        int store=bookMapper.selectByPrimaryKey(bid).getStore();
        if (store-number>=0){
            return true;
        }
        return false;
    }

    @Override
    public int reduceStore(Integer bid, Integer number) {
        Book book = bookMapper.selectByPrimaryKey(bid);
        book.setStore(book.getStore()-number);
        return bookMapper.updateByPrimaryKey(book);
    }

}
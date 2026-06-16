package com.book.books.service;

import com.book.books.domain.Book;
import com.book.books.domain.BookExample;

import java.util.List;
import java.util.Set;


public interface IBookService {
    int addBook(Book book);

    List<Book> findAllBook();

    int delById(Integer bid);

    Book findById(Integer bid);

    int modifyBook(Book book);

    Set<String> bookType();

    List<Book> selectByExample(BookExample example);

    boolean checkStore(Integer bid, Integer number);

    int reduceStore(Integer bid,Integer number);
}
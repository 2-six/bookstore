package com.book.books.service;

import com.book.books.domain.Book;
import com.book.books.domain.Cart;

public interface ICartService {
    Cart addGoodsInCart(Book book ,int number,Cart cart);

    Cart removeGoodsFromCart(Book book,Cart cart);

    Cart calTotalPrice(Cart cart);
}
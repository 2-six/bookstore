package com.book.books.service.impl;

import com.book.books.domain.Book;
import com.book.books.domain.Cart;
import com.book.books.service.ICartService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

@Service
public class CartService implements ICartService {

    @Override
    public Cart addGoodsInCart(Book book, int number, Cart cart) {
        HashMap<Book, Integer> goods = cart.getGoods();
        if (goods.containsKey(book)) {
            goods.put(book, goods.get(book) + number);
        } else {
            goods.put(book, number);
        }
        //重新计算购物车的总金额
        return calTotalPrice(cart);
    }

    @Override
    public Cart removeGoodsFromCart(Book book, Cart cart) {
        HashMap<Book, Integer> goods = cart.getGoods();
        goods.remove(book);
        cart.setGoods(goods);
        //重新计算购物车的总金额
        return calTotalPrice(cart);
    }

    @Override
    public Cart calTotalPrice(Cart cart) {
        HashMap<Book, Integer> goods = cart.getGoods();
        double sum = 0.0;
        Set<Book> keys = goods.keySet();
        Iterator<Book> it = keys.iterator();
        while (it.hasNext()) {
            Book i = it.next();
            sum += Double.parseDouble(i.getPirce()) * goods.get(i);
        }
        cart.setTotalPrice(sum);
        return cart;
    }

}
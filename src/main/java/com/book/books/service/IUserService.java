package com.book.books.service;

import com.book.books.domain.User;

import java.util.List;

public interface IUserService {

    boolean userReg(User user);

    User userLogin(User user);

    List<User> findAllUser();

    boolean userModify(User user);

    User findUserById(Integer uid);

    int deleteById(Integer uid);

    int addUser(User user);

    boolean updatePwd(Integer uid,String password,String newPassword);
}
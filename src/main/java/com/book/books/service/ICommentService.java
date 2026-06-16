package com.book.books.service;

import com.book.books.domain.Comment;

import java.util.List;

public interface ICommentService {
    int insert(Comment record);

    List<Comment> selectBybid(Integer bid);
}
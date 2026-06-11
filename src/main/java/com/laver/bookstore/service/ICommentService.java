package com.laver.bookstore.service;

import com.laver.bookstore.domain.Comment;

import java.util.List;

public interface ICommentService {
	int insert(Comment record);
	
	List<Comment> selectBybid(Integer bid);
}

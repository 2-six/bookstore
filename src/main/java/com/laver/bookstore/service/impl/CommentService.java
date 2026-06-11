package com.laver.bookstore.service.impl;

import com.laver.bookstore.domain.Comment;
import com.laver.bookstore.domain.CommentExample;
import com.laver.bookstore.domain.CommentExample.Criteria;
import com.laver.bookstore.mapper.CommentMapper;
import com.laver.bookstore.service.ICommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentService implements ICommentService{
	
	@Resource
	private CommentMapper commentMapper;

	@Override
	public int insert(Comment record) {
		// TODO Auto-generated method stub
		return commentMapper.insert(record);
	}

	@Override
	public List<Comment> selectBybid(Integer bid) {
		// TODO Auto-generated method stub
		CommentExample example = new CommentExample();
		Criteria cri = example.createCriteria();
		cri.andBidEqualTo(bid);
		return commentMapper.selectByExample(example);
	}


}

package com.book.books.service.impl;

import com.book.books.domain.Comment;
import com.book.books.domain.CommentExample;
import com.book.books.domain.CommentExample.Criteria;
import com.book.books.mapper.CommentMapper;
import com.book.books.service.ICommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentService implements ICommentService{

    @Resource
    private CommentMapper commentMapper;

    @Override
    public int insert(Comment record) {
        return commentMapper.insert(record);
    }

    @Override
    public List<Comment> selectBybid(Integer bid) {
        CommentExample example = new CommentExample();
        Criteria cri = example.createCriteria();
        cri.andBidEqualTo(bid);
        return commentMapper.selectByExample(example);
    }
}
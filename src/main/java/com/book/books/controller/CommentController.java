package com.book.books.controller;

import com.book.books.domain.Book;
import com.book.books.domain.Comment;
import com.book.books.domain.OrderDetail;
import com.book.books.service.IBookService;
import com.book.books.service.ICommentService;
import com.book.books.service.IOrderDetailService;
import com.book.books.util.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class CommentController {
    @Resource
    private IBookService bookService;
    @Resource
    private ICommentService commentService;
    @Resource
    private IOrderDetailService orderDetailService;

    @RequestMapping("/commentPage")
    public String commentPage(Integer odid,Integer bid,Model model){
        Book book = bookService.findById(bid);
        model.addAttribute("book", book);
        model.addAttribute("odid", odid);
        return "front/comment";
    }
    @RequestMapping("/addComment")
    public ModelAndView addComment(Integer uid,Integer odid,Integer bid,String uname,String content){
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String dateStr=sdf.format(date);
        Comment comment = new Comment();
        comment.setBid(bid);
        comment.setUname(uname);
        comment.setDate(dateStr);
        comment.setContent(content);
        commentService.insert(comment);
        OrderDetail orderDetail =orderDetailService.selectByPrimaryKey(odid);
        orderDetail.setId(odid);
        orderDetail.setStatus(Constant.EN_COMMENT);
        orderDetailService.updateByPrimaryKey(orderDetail);
        return new ModelAndView("redirect:/userOrder.do?uid="+uid);
    }
}
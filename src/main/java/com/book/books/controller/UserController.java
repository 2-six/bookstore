package com.book.books.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.book.books.domain.*;
import com.book.books.service.IBookOrderService;
import com.book.books.service.IBookService;
import com.book.books.service.IOrderDetailService;
import com.book.books.service.IUserService;
import com.book.books.util.Constant;
import com.book.books.util.MD5Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.awt.print.Book;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    @Resource
    private IUserService userService;
    @Resource
    private IBookService bookService;
    @Resource
    private IBookOrderService bookOrderService;
    @Resource
    private IOrderDetailService orderDetailService;

    @RequestMapping("backLoginPage")
    public String loginPage(){
        return "manage/login";
    }

    /**
     * 管理员后台登录（已修复：对接数据库 + MD5 + 角色判断）
     */
    @RequestMapping(value = "backLogin.do")
    public String login(HttpSession session, String username, String password) {
        System.out.println("前端传入账号："+username);
        System.out.println("前端传入密码明文："+password);
        // 非空校验
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            System.out.println("账号或密码为空");
            return "manage/login";
        }
        // 密码MD5加密，和普通用户登录规则保持一致
        String md5Pwd = MD5Utils.encode(password);
        System.out.println("加密后的密码："+md5Pwd);
        User user = new User();
        user.setUname(username);
        user.setPassword(md5Pwd);
        // 调用业务层查询数据库用户
        User loginUser = userService.userLogin(user);
        System.out.println("数据库查询返回用户："+loginUser);

        // 账号密码错误，返回登录页
        if (loginUser == null) {
            System.out.println("查询不到该用户，账号密码错误");
            return "manage/login";
        }

        // 去除空格、转小写，兼容数据库多余空格/大小写问题
        String role = loginUser.getRole() == null ? "" : loginUser.getRole().trim().toLowerCase();
        System.out.println("用户角色："+role);
        // 判断是否为管理员
        if ("admin".equals(role)) {
            session.setAttribute("user", loginUser);
            session.setAttribute("admin", "admin");
            System.out.println("管理员登录成功，跳转后台");
            return "manage/index";
        } else {
            // 普通用户禁止登录后台
            System.out.println("是普通用户，禁止登录后台");
            return "manage/login";
        }
    }

    @ResponseBody
    @RequestMapping("/userReg")
    public String userReg(String userName,String passWord){
        User user=new User();
        user.setUname(userName);
        String md5Pwd = MD5Utils.encode(passWord);
        user.setPassword(md5Pwd);
        if(userService.userReg(user)){
            return "success";
        }
        return "false";
    }

    @ResponseBody
    @RequestMapping("/loginCheck")
    public String loginCheck(String userName,String passWord,HttpSession session){
        if(userName==null||userName.equals("")){
            return "nameEmpty";
        }
        if(passWord==null||passWord.equals("")){
            return "pwdEmpty";
        }
        User user=new User();
        user.setUname(userName);
        String md5Pwd = MD5Utils.encode(passWord);
        user.setPassword(md5Pwd);
        if(userService.userLogin(user).getUid()!=null){
            User sessionUser=userService.userLogin(user);
            sessionUser.setPassword("");
            session.setAttribute("user",sessionUser);
            return "success";
        }
        return "false";
    }

    @RequestMapping("/manaUser")
    public String manaUser(Integer pageNum,Model model){
        if(pageNum!=null){
            PageHelper.startPage(pageNum, Constant.MU_PAGE_SIZE);
        }else{
            PageHelper.startPage(1, Constant.MU_PAGE_SIZE);
        }
        List<User> users = userService.findAllUser();
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("users", users);
        return "manage/user";
    }

    @RequestMapping("/modifyUserPage")
    public String modifyUserPage(Model model,Integer uid){
        User user = userService.findUserById(uid);
        model.addAttribute("user", user);
        return "manage/user-modify";
    }

    @RequestMapping("/modifyUser")
    public ModelAndView manaUser(Integer uid,String userName,String gender,String email,String phone,String address){
        User user=new User();
        user.setUid(uid);
        user.setUname(userName);
        user.setGender(gender);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAdress(address);
        userService.userModify(user);
        return new ModelAndView("redirect:/manaUser.do");
    }

    @RequestMapping("/delUser")
    public ModelAndView deleteUser(Integer uid){
        userService.deleteById(uid);
        return new ModelAndView("redirect:/manaUser.do");
    }

    @RequestMapping("/addUser")
    public ModelAndView addUser(String userName,String passWord,String gender,String email,String phone,String address){
        User user = new User();
        user.setUname(userName);
        user.setPassword(passWord);
        user.setGender(gender);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAdress(address);
        userService.addUser(user);
        return new ModelAndView("redirect:/manaUser.do");
    }

    @RequestMapping("/updateUserPage")
    public String updateUserPage(Model model,HttpSession session){
        User sessionUser= (User) session.getAttribute("user");
        User user=userService.findUserById(sessionUser.getUid());
        model.addAttribute("user",user);
        return "front/update";
    }

    @RequestMapping("/updateUser")
    public ModelAndView updateUser(Integer uid,String uname,String gender,String email,String phone,String address){
        User user=new User();
        user.setUid(uid);
        user.setUname(uname);
        user.setGender(gender);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAdress(address);
        userService.userModify(user);
        return new ModelAndView("redirect:/index.do");
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session){
        session.removeAttribute("user");
        session.removeAttribute("cart");
        return new ModelAndView("redirect:/index.do");
    }

    @RequestMapping("/userOrder")
    public String userOrder(Model model,Integer uid,Integer pageNum){
        Set<Book> books=new HashSet<Book>();
        BookOrderExample example = new BookOrderExample();
        BookOrderExample.Criteria cri = example.createCriteria();
        cri.andUidEqualTo(uid);
        OrderDetailExample example2 = new OrderDetailExample();
        List<OrderDetail> orderDetails = orderDetailService.selectByExample(example2);
        for(OrderDetail od:orderDetails){
            Book book = bookService.findById(od.getBookId());
            books.add(book);
        }
        if(pageNum!=null){
            PageHelper.startPage(pageNum, Constant.UO_PAGE_SIZE,"date desc");
        }else{
            PageHelper.startPage(1, Constant.UO_PAGE_SIZE,"date desc");
        }
        List<BookOrder> bookOrders = bookOrderService.selectByExample(example);
        PageInfo<BookOrder> pageInfo = new PageInfo<BookOrder>(bookOrders);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("bookOrders", bookOrders);
        model.addAttribute("books", books);
        model.addAttribute("orderDetails", orderDetails);
        return "front/user-order";
    }

    @ResponseBody
    @RequestMapping("/updatePwd")
    public String updatePwd(Integer uid,String password,String newPassword){
        if(password==null||password.equals("")){
            return "pwdEmpty";
        }
        if(newPassword==null||newPassword.equals("")){
            return "newEmpty";
        }
        boolean flag = userService.updatePwd(uid, password, newPassword);
        if(flag){
            return "success";
        }
        return "false";
    }
}
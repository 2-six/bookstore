package com.book.books.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // 获得在下面代码中要用的request,response,session对象
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpSession session = servletRequest.getSession();
        // 获得用户请求的URI
        String path = servletRequest.getRequestURI();
        // 从session里取管理员标识
        String admin = (String) session.getAttribute("admin");

        // 放行无需过滤的页面
        for (int i = 0; i < Constant.NoFilter_Pages.length; i++) {
            if (path.indexOf(Constant.NoFilter_Pages[i]) > -1) {
                chain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        // 登录页面放行
        if (path.indexOf("/backLoginPage.do") > -1) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (path.indexOf("/backLogin.do") > -1) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 未登录则跳转登录页
        if (admin == null || "".equals(admin)) {
            servletResponse.sendRedirect("/backLoginPage.do");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // 重定向到控制器的首页请求
    response.sendRedirect(request.getContextPath() + "/index.do");
%>
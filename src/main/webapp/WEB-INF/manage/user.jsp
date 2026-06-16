<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<jsp:include page="../static/back-head.html"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">

	<style>
	button,
	input[type="submit"],
	input[type="button"],
	.btn,
	.btn-success,
	.btn-warning,
	.btn-info,
	.btn-default {
		background-color: #2E8B57 !important;
		color: #ffffff !important;
		border: none !important;
		border-radius: 20px !important;
		padding: 6px 16px !important;
		cursor: pointer !important;
	}
	button:hover,
	input[type="submit"]:hover,
	input[type="button"]:hover,
	.btn:hover {
		background-color: #246b44 !important;
	}
	.lefter dd em a {
		display: inline-block;
		background-color: #2E8B57 !important;
		color: #fff !important;
		border-radius: 20px !important;
		padding: 3px 12px !important;
		text-decoration: none;
	}
	.lefter dd em a:hover {
		background-color: #246b44 !important;
	}
	table a {
		display: inline-block;
		background-color: #2E8B57 !important;
		color: #fff !important;
		border-radius: 20px !important;
		padding: 2px 10px !important;
		text-decoration: none;
		margin: 0 4px;
	}
	table a:hover {
		background-color: #246b44 !important;
	}
	.pager li a {
		display: inline-block;
		background-color: #2E8B57 !important;
		color: #fff !important;
		border-radius: 20px !important;
		padding: 3px 10px !important;
		text-decoration: none;
	}
	.pager li a:hover {
		background-color: #246b44 !important;
	}
	</style>
</head>
<body onload="getNowFormatDate()">
<div id="header" class="wrap">
	<div id="logo"><img src="${pageContext.request.contextPath}/images/logo.gif" /></div>
	<div class="help"><a href="${pageContext.request.contextPath}/index.do">返回前台页面</a></div>
	<div class="navbar">
		<ul class="bar">
			<li ><a href="mana.do">首页</a></li>
			<li class="current"><a href="manaUser.do">用户</a></li>
			<li><a href="manaBook.do">商品</a></li>
			<li><a href="BookOrder.do">订单</a></li>
		</ul>
	</div>
</div>
<div id="childNav">
	<div class="welcome wrap">
		管理员您好，今天是<span id="time"></span>，欢迎回到管理后台。
	</div>
</div>
<div id="main" class="wrap">
	<div id="menu-mng" class="lefter">
		<div class="box">
			<dl>
				<dt>用户管理</dt>
				<dd><em><a href="addUserPage.do">新增</a></em><a href="manaUser.do">用户管理</a></dd>
				<dt>商品管理</dt>
				<dd><em><a href="addproductPage.do">新增</a></em><a href="manaBook.do">商品管理</a></dd>
				<dt>订单管理</dt>
				<dd><a href="BookOrder.do">订单管理</a></dd>
			</dl>
		</div>
	</div>
	<div class="main">
		<h2>用户管理</h2>
		<div class="manage">
				<table class="table table-hover">
					<tr class="active">
						<th>ID</th>
						<th>姓名</th>
						<th>性别</th>
						<th>Email</th>
						<th>手机</th>
						<th>操作</th>
					</tr>
					<c:forEach var="user" items="${users}">
						<tr>
							<td class="first w4 c">${user.uid}</td>
							<td class="w1 c">${user.uname}</td>
							<td class="w2 c">${user.gender}</td>
							<td>${user.email}</td>
							<td class="w4 c">${user.phone}</td>
							<td class="w1 c"><a href="modifyUserPage.do?uid=${user.uid}">修改</a> <a
								href="delUser.do?uid=${user.uid}">删除</a></td>
						</tr>
					</c:forEach>
				</table>
				<div class="pager">
					<ul class="clearfix">
						<c:choose>
							<c:when test="${pageInfo.hasPreviousPage}">
								<li><a href="manaUser.do?pageNum=1">首页</a></li>
								<li><a href="manaUser.do?pageNum=${pageInfo.prePage }">上一页</a></li>
							</c:when>
							<c:otherwise>
								<li><span>首页</span></li>
								<li><span>上一页</span></li>
							</c:otherwise>
						</c:choose>

						<c:forEach var="index" begin="1" end="${pageInfo.pages }">
							<li <c:if test="${index==pageInfo.pageNum}">class="current"</c:if>><a
								href="manaUser.do?pageNum=${index }">${index }</a></li>
						</c:forEach>

						<c:choose>
							<c:when test="${pageInfo.hasNextPage}">
								<li><a href="manaUser.do?pageNum=${pageInfo.nextPage }">下一页</a></li>
								<li><a href="manaUser.do?pageNum=${pageInfo.pages }">尾页</a></li>
							</c:when>
							<c:otherwise>
								<li><span>下一页</span></li>
								<li><span>尾页</span></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
			</div>
	</div>
	<div class="clear"></div>
</div>
<jsp:include page="../static/footer.html"/>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
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
	</style>
</head>
<body onload="getNowFormatDate()">
<div id="header" class="wrap">
	<div id="logo"><img src="${pageContext.request.contextPath}/images/logo.gif" /></div>
	<div class="help"><a href="${pageContext.request.contextPath}/index.do">返回前台页面</a></div>
	<div class="navbar">
		<ul class="bar">
			<li ><a href="mana.do">首页</a></li>
			<li><a href="manaUser.do">用户</a></li>
			<li><a href="manaBook.do">商品</a></li>
			<li class="current"><a href="BookOrder.do">订单</a></li>
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
		<h2>修改订单</h2>
		<div class="manage">
			<form action="orderModify.do">
				<table class="form">
					<tr>
						<td class="field">订单ID：</td>
						<td><input type="text" class="text"  id="oid" name="oid" value="${bookOrder.oid }" readonly /></td>
					</tr>
					<tr>
						<td class="field">订购人姓名：</td>
						<td><input type="text" class="text"  id="oname" name="oname" value="${bookOrder.oname }" /></td>
					</tr>
					<tr>
						<td class="field">详细地址：</td>
						<td><input type="text" class="text"  id="address" name="address" value="${bookOrder.adress }" /></td>
					</tr>
					<tr>
						<td class="field">状态：</td>
							<td><select id="status" name="status" class="form-control" style="width: 90px;">
									<option value="未确认">未确认</option>
									<option value="未发货">未发货</option>
									<option value="已发货">已发货</option>
									<option value="已签收">已签收</option>
								</select>
							</td>
						</tr>
					<tr>
						<td></td>
						<td><input class="btn btn-info" type="submit" name="submit" value="更新" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="clear"></div>
</div>
<jsp:include page="../static/footer.html"/>
</body>
</html>
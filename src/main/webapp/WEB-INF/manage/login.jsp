<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>
<head>
    <meta charset="utf-8">
    <title>BOOK HOUSE后台管理系统登录登陆界面</title>
    <meta name="viewport" content="width=device-width">
    <link href="${pageContext.request.contextPath}/css/base.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet" type="text/css">

    <style>
        button,
        input[type="submit"],
        input[type="button"],
        .button,
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
        .button:hover {
            background-color: #246b44 !important;
        }
    </style>
</head>
<body>

<div class="login">
    <form action="backLogin.do" method="post" id="form">
        <div class="logo"></div>
        <div class="login_form">
            <div class="user">
                <input class="text_value"  name="username" type="text" style="width:245px;height:50px;" />
                <input class="text_value"  name="password" type="password" style="width:245px;height:50px;"/>
            </div>
            <button class="button" id="submit" type="submit">登录</button>
        </div>

        <div id="tip"></div>

    </form>
</div>
<script>var basedir='${pageContext.request.contextPath}/ui/';</script>
<script src="${pageContext.request.contextPath}/ui/do.js"></script>
<script src="${pageContext.request.contextPath}/ui/config.js"></script>
<script>
    Do.ready('form', function() {
        $("#form").Validform({
            ajaxPost:true,
            postonce:true,
            tiptype:function(msg,o,cssctl){
                if(!o.obj.is("form")){
                    var objtip=o.obj.siblings(".Validform_checktip");
                    cssctl(objtip,o.type);
                    objtip.text(msg);
                }else{
                    var objtip=o.obj.find("#tip");
                    cssctl(objtip,o.type);
                    if(o.type==2){
                        window.location.href='index.do';
                    }else{
                        objtip.text(msg);
                    }
                }
            }
        });
    });

</script>
</body>
</html>
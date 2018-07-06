<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>SSM - 基础框架</title>
<meta charset="UTF-8" />
<meta name="Designer" content="PremiumPixels.com">
<meta name="Author" content="$hekh@r d-Ziner, CSSJUNTION.com">
<link rel="stylesheet" type="text/css" href="res/css/reset.css">
<link rel="stylesheet" type="text/css" href="res/css/structure.css">
<script src="res/plugins/jquery-2.1.3.js" type="text/javascript"></script>
</head>

<body>
<form class="box login" id="loginform" name="loginform" action="common/main/login" method="post">
	<fieldset class="boxBody">
	  <label>用户名</label>
	  <input type="text" tabindex="1" placeholder="请输入用户密码" name="username" required>
	  <label><a href="#" class="rLink" >忘记密码?</a>密　码</label>
	  <input type="password" tabindex="2" required name="password">
	  <label>验证码</label>
	  <input type="text" id="randomcode" name="randomcode" tabindex="3" style="width:135px;display:inline" required/> 
	  <img id="randomcode_img" src="${pageContext.request.contextPath}/validatecode.jsp" alt=""
			width="56" height="25" style="vertical-align:middle;"  /> 
	  <a href=javascript:randomcode_refresh()>刷新</a>	  
	</fieldset>
	<footer>
	  <label><input type="checkbox" name="rememberMe" tabindex="4">>自动登录</label>
	  <input type="reset" class="btnLogin" value="重置" tabindex="6">
	  <input type="submit" class="btnLogin" value="登录" tabindex="5">
	</footer>
</form>
<footer id="main">
  <a href="#">jqueryui + springmvc + mybatis + shiro 基础框架</a> | <a href="#">power by LiW</a>
</footer>
</body>
</html>
<script type="text/javascript">
	function randomcode_refresh(){ 
	  $("#randomcode_img").attr("src","${pageContext.request.contextPath}/validatecode.jsp?time='"+new Date()+"'");
	 }

	//登录提示方法
	function loginsubmit() {
		$("#loginform").submit();

	}	
</script>
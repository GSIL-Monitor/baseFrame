<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Insert title here</title>
	</head>
	<body>
		<form name="serForm" action="upload/fileUpload" method="post"  enctype="multipart/form-data">
			<h1>采用流的方式上传文件</h1>
			<input type="file" name="file">
			<input type="submit" value="upload"/>
			<a href="upload/download">下载1</a>  
			<a href="upload/download2">下载2</a>
		</form>
	 
		<form name="Form2" action="upload/fileUpload2" method="post"  enctype="multipart/form-data">
			<h1>采用multipart提供的file.transfer方法上传文件</h1>
			<input type="file" name="file">
			<input type="submit" value="upload"/>
		</form>
	 
		<form name="Form2" action="upload/springUpload" method="post"  enctype="multipart/form-data">
			<h1>使用spring mvc提供的类的方法上传文件</h1>
			<input type="file" name="file">
			<input type="submit" value="upload"/>
		</form>
	</body>
</html>
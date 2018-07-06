<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String hostPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>

<html>
<head>
	<base href="<%=basePath%>">
    <title>多文件上传</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
    <link href="res/css/fileUpload.css" rel="stylesheet" type="text/css"/>
    <link href="res/css/iconfont.css" rel="stylesheet" type="text/css"/>
    <script src="res/plugins/jquery-2.1.3.js" type="text/javascript"></script>
	<script type="text/javascript" src="res/js/fileUpload.js"></script>

</head>
<body>
	<div id="fileContent" class="fileUploadContent"></div>
</body>
</html>
<script type="text/javascript">
$(function(){
	$("#fileContent").initUpload({
		"uploadUrl" : "upload/multi",
		"progressUrl" : "upload/getStatus",
		"filelSavePath" : "directories",
		"showSummerProgress":true,
		//"size":1024000,
		//"maxFileNumber":5,
		"beforeUpload":function(){alert('开始upload');},
		"onUpload":function(){alert('结束upload');top.location.reload();},
		//"fileType":['pgn','jpg','docx','doc','iso']
	});
});
</script>
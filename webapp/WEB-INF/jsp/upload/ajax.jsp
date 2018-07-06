<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ajax文件上传</title>
<script type="text/javascript">
	function showStatus(){
		var intervalId=setInterval(function(){
			$.get("upload/getStatus" , {} , function(data,status){
				console.log(data);
				console.log('状态:'+status);
				var percent=data.percent;
				if(percent>=100){
					clearInterval(intervalId);
					percent=100;
				}
				$("#result").width(percent+"%");
				$("#p").html("正在上传 : " + percent+"%");
			},"json");
		},100);
	}
	function ajaxSubmit(){
		var files=document.getElementById("uploadFile").files;
		console.log("文件数量:"+files.length);
		var formData=new FormData();
		for(var i=0;i<files.length;i++){
			formData.append("file",files[i]);
		}
		formData.append("filelSavePath","ajax");
		formData.append("username","liw");
		formData.append("password","123456");
		console.log(formData);
		$.ajax({
			type:"post",
			url:"upload/multi",
			data:formData,
			processData:false,
			contentType:false,
			success:function(data){console.log(data)},
			error:function(e){}
		});
		showStatus();
	}
</script>
</head>
<body>
	<div id="uploadDiv">
		<input type="file" name="file" id="uploadFile" multiple>
		
	</div>
	<button onclick="ajaxSubmit()">ajax提交</button>
	<span id="p" style="color:#f00;font:italic bold 12px/30px arial,sans-serif;float:right"></span>
	<div id="result" style="border:black solid 0px; width: 0px;height: 10px;">
		<div style="height: 8px;background: green;position: relative; top: 1px;left: 1px;"></div>
	</div>
</body>
</html>















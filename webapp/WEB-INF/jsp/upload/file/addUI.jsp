<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ajax文件上传</title>
<script type="text/javascript">
	function fileOnChange() {
		var filepath = $("#uploadFile").val();
		var extStart = filepath.lastIndexOf(".");
		var ext = filepath.substring(extStart, filepath.length).toUpperCase();
		/*if (ext != ".XLS" && ext != ".xls" && ext != ".XLSX" && ext != ".xlsx") {
			layer.alert("只允许上传excel文件！", {
				icon : 2
			});
			$("#uploadFile").val("");
			return false;
		}*/
	}

	function showStatus(){
		var intervalId=setInterval(function(){
			$.get("file/getStatus" , {} , function(data,status){
				console.log(data);
				console.log('状态:'+status);
				var percent=data.percent;
				if(percent>=100){
					clearInterval(intervalId);
					percent=100;
				}
				$("#result").width(percent+"%");
				$("#p").html("上传进度 : " + percent+"%");
			},"json");
		},100);
	}
	function ajaxSubmit(){
		var filepath = $("#uploadFile").val();
		if(filepath == "" || filepath == null){
			layer.alert("请选择所要上传的文件！", {icon : 2 });
			return false;
		}
		
		var files=document.getElementById("uploadFile").files;
		console.log("文件数量:"+files.length);
		var formData=new FormData();
		for(var i=0;i<files.length;i++){
			formData.append("file",files[i]);
		}
		formData.append("typeId","${upload.typeId}");
		formData.append("cid","${upload.categoryId}");
		$.ajax({
			type:"post",
			url:"file/upload",
			data:formData,
			processData:false,
			contentType:false,
			success:function(data){
				console.log(data);
				layer.msg('文件上传成功', function() {				
					parent.search();
				});
				setTimeout(function (){
					parent.layer.closeAll();
				},3000)
				
			},
			error:function(e){
				console.log(e);
			}
		});
		showStatus();
	}
</script>
</head>
<body>
	<div id="uploadDiv">
		<input type="file" name="file" id="uploadFile" <c:if test="false">multiple</c:if> onchange="return fileOnChange()">
		
	</div>
	<button onclick="ajaxSubmit()">文件上传 - ajax提交</button>
	<span id="p" style="color:#f00;font:italic bold 12px/30px arial,sans-serif;float:right"></span>
	<div id="result" style="border:black solid 0px; width: 0px;height: 10px;">
		<div style="height: 8px;background: green;position: relative; top: 1px;left: 1px;"></div>
	</div>
</body>
</html>















<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<html>
<head>
<script type="text/javascript">
$(document).ready(function(){	
	$("#userForm").validate({
		rules:{
			oldpassword:{
				required:true,
				//isCorrect:true				
				remote: {
				    url: "common/user/checkPassword",     //后台处理程序
				    type: "post",               		  //数据发送方式
				    dataType: "json",           		  //接受数据格式   
				    data: {                     		  //要传递的数据
				        id: function() { return ${user.id};},
				        password:function(){ return $("#oldpassword").val();}
				    }
				}
			},
			password:{
				required:true,
				rangelength:[5,10]
			},
			confirm_password:{
				required:true,
				rangelength:[5,10],
				equalTo:"#password"
			}
		},
		messages:{
			oldpassword:{
				required:"请输入原密码",
				remote:"密码不正确"
			},
			password:{
				required:"请输入新密码",
				rangelength:"请输入长度在{0}到{1}之间的密码"
			},
			confirm_password:{
				required:"请输入确认密码",
				rangelength:"请输入长度在{0}到{1}之间的密码",
				equalTo:"两次密码输入不一致"
			}
		},
		submitHandler:function(form){
		    addOrUpdate();
		}
	});
	
	// 原密码验证   
	jQuery.validator.addMethod("isCorrect", function(value, element) {   
		$pwd = "${user.password}";		
	    return this.optional(element) || (value==$pwd);
	}, "请正确填写您的原始密码");
	
	
	
});

function addOrUpdate() {
	var url = "common/user/updatePassword";
	
	$.ajax({
		type: "POST",
		url: url,
		data: $("#userForm").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {
			window.parent.location.href = "common/user/index";
		}
	});
}

</script>
</head>

<body>
<form id="userForm" method="post">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<form:hidden id="id" path="user.id"/>
<table class="datatable">
	<tr>
		<th align="right" width="100"><span style="color: red">*</span> 原密码：</th>
		<td><input type="password" name="oldpassword" id="oldpassword"/></td>
	</tr>
	<tr>
		<th align="right" width="100"><span style="color: red">*</span> 新密码：</th>
		<td><input type="password" name="password" id="password"/></td>
	</tr>
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>确认密码：</th>
		<td><input type="password" name="confirm_password" id="confirm_password"  /></td>
	</tr>
	
	<tr>
		<th align="right">操作：</th>
		<td><input type="submit" class="blue" value="提交"><input name="reset" type="reset" class="blue" value="重置"></td>		
	</tr>
</table>
</form>
</body>
</html>

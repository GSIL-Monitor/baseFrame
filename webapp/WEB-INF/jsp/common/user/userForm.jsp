<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<html>
<head>
<script type="text/javascript">
$(document).ready(function(){	
	//回显角色多选框
	var roles = "${user.roleIds}";	
	if(roles!=""){
		var roleArr = roles.split(",");	
		for(var j=0;j<roleArr.length;j++){			
			$option = ($("#role").find('option[value='+$.trim(roleArr[j])+']'));
			$option.attr("selected",true);
		}	
	}
	//回显审批领导多选框
	var pids = "${user.pids}";	
	if(pids!=""){
		var pidArr = pids.split(",");	
		for(var j=0;j<pidArr.length;j++){			
			$option = ($("#pids").find('option[value='+$.trim(pidArr[j])+']'));
			$option.attr("selected",true);
		}	
	}
	$("#userForm").validate({
		submitHandler:function(form){
		    addOrUpdate();
		}
	});
	
});

function addOrUpdate() {
	var url = "common/user/add";
	if ($('#id').val() > 0) {
		url = "common/user/update";
	}
	
	$.ajax({
		type: "POST",
		url: url,
		data: $("#userForm").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {
			if(data.retCode=="success" || data =="Success")
				window.parent.location.href = "common/user/index";
			else{				
				$("#userError").text(data.retObj.username);
			}
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
		<th align="right" width="100"><span style="color: red">*</span>账号：</th>
		<td><form:input id="usercode"  path="user.usercode" cssClass="required"/></td>
	</tr>
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>用户名：</th>
		<td><form:input  path="user.username" cssClass="required"/>
		<%-- <form:errors path="user.username"/> --%>
		<span id="userError" style="color: red"></span>
		</td>
	</tr>
	<!-- <tr>
		<th align="right" width="100"><span style="color: red">*</span> 密码：</th>
		<td><input type="password" name="password" /></td>
	</tr>
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>确认密码：</th>
		<td><input type="password" name="confirm_password" id="confirm_password"  /></td>
	</tr> -->
	<tr>
		<th>角色：</th>
		<td>
			<form:select id="role" path="user.roleIds" items="${roleList}" itemLabel="name" itemValue="id"  multiple="true"  size="8" cssClass="required" cssStyle="width:200px"></form:select>
		</td>
	</tr>
	<tr>
		<th>审批领导：</th>
		<td>
			<form:select id="pids" path="user.pids" items="${pidList}" itemLabel="username" itemValue="id"  multiple="true"  size="8" cssClass="required" cssStyle="width:200px"></form:select>
		</td>
	</tr>
	<tr>
		<th align="right">是否锁定：</th>
		<td>
			<form:radiobutton path="user.locked" value="0" label="否"/>　
			<form:radiobutton path="user.locked" value="1" label="是"/>
		</td>
	</tr>
	<c:if test="${user.id==null }">
		<tr>
			<th align="right">默认密码：</th>
			<td>
				123456
			</td>
		</tr>
	</c:if>
	<tr>
		<th align="right">操作：</th>
		<td><input type="submit" class="blue" value="提交"><input name="reset" type="reset" class="blue" value="重置"></td>		
	</tr>
</table>
</form>
</body>
</html>

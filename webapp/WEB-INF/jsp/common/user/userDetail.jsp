<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<html>
<head>
<script type="text/javascript">
function closeWin(obj){		
	 var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	 parent.layer.close(index); //再执行关闭  
}
</script>
</head>

<body>
<table class="datatable">
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>账号：</th>
		<td>${user.usercode }</td>
	</tr>
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>用户名：</th>
		<td>${user.username }</td>
	</tr>
	
	<tr>
		<th>角色：</th>
		<td>
			<c:forEach items="${user.roles}" var="role">				
					${role.name}&nbsp;			
			</c:forEach>
		</td>
	</tr>
	<tr>
		<th>角色类型：</th>
		<td>
			<c:forEach items="${user.roles}" var="role">
				<c:if test="${role.type==1 }">基础员工&nbsp;</c:if>
				<c:if test="${role.type==2 }">经理&nbsp;</c:if>
				<c:if test="${role.type==3 }">管理员&nbsp;</c:if>
			</c:forEach>
		</td>
	</tr>
	<tr>
		<th>资源权限：</th>
		<td>
			<c:forEach items="${user.resourceList}" var="res">
				${res.name } => ${res.percode }<br/>
			</c:forEach>
		</td>
	</tr>
	<tr>
		<th align="right">状态：</th>
		<td>
			<c:if test="${user.locked==0}">启用&nbsp;</c:if>
			<c:if test="${user.locked==1}">锁定&nbsp;</c:if>
		</td>
	</tr>
	
	<tr>
		<th align="right">操作：</th>
		<td><input type="button" class="blue" value="关闭" onclick="closeWin(this)"></td>		
	</tr>
</table>
</body>
</html>

<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>授权管理</title>
<style type="text/css">
/*搜索框表格样式*/
.search {
	font-size: 12px;
	font-family: Microsoft YaHei;
}

.search td:nth-child(even) {
	text-align: left;
}

.search td:nth-child(odd) {
	text-align: right;
}

.search td input[type=text] {
	width: 100px;
}

</style>
<script type="text/javascript">
var realNameArr = new Array();
var userArr = new Array();
$(document).ready(function(){
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
});

function search() {
	$("#searchForm").attr("action", "common/user/index");
	$("#searchForm").submit();
}

function oper(id,val){
	var msg = (val==0)?"确定要【锁定】吗？":"确定要【解锁】吗？";
	var url = "common/user/" + $.trim(id) + "/lock" ;
	if(val=='del'){
		msg = "确定要【删除】吗？";
		url = "common/user/" + id + "/del" ;
	}else if(val == 'reset'){
		msg = "确定要【重置密码为'123456'】吗？";
		url = "common/user/" + id + "/reset" ;
	}
	layer.confirm(msg, {icon: 3}, function(){
        $.ajax({
            type: "POST",
            url: url,
            traditional: true,
            async: false,
            success: function(data) {
                search();
            }
        });
    });
}



</script>
</head>

<body>
<form name="form" id="searchForm" method="post" action="">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div class="accordion">
	<h3>用户列表</h3>
	<div align="right">
	   <table width="85%" class="search">
			<tr>
		    	<td>姓名：</td>
		    	<td><form:input path="dto.username"/></td>
				<td>角色：</td>
				<td>
					<form:select id="roleId" path="dto.roleId" onchange="searchResetPage()">
						<option value="">全部</option>
						<form:options items="${roleList}" itemValue="id" itemLabel="name"/>
					</form:select>
				</td>
				<td>角色类型：</td>
				<td>
					<form:select id="roleType" path="dto.roleType" onchange="searchResetPage()">
						<form:option value="" label="全部" />
						<form:option value="1" label="基础员工" />
						<form:option value="2" label="经理" />
						<form:option value="3" label="管理人员" />
					</form:select>
				</td>
				<td>
				<input type="button"   class="blue" value ="查询" onclick="searchResetPage()">
				<shiro:hasPermission name="user:add"><input type="button"   class="blue"  value ="添加" onclick="openWin('添加', 'common/user/add', 400, 600)"></shiro:hasPermission>
			    </td>
			</tr>
		</table>
	</div>
</div>
<table class="listtable" width="100%">
	<tr>
		<th width="10%">ID</th>
		<th width="10%">账号</th>
		<th width="10%">用户名</th>
		<th width="10%">角色类型</th>
		<th width="15%">角色名称</th>
		<th width="20%">审批领导</th>
		<th width="5%">状态</th>
		<th nowrap="nowrap">操作</th>
	</tr>
	<c:forEach items="${dto.dataList}" var="user">
	<tr align="center">
		<td>${user.id}</td>
		<td>${user.usercode}</td>
		<td>${user.username}</td>
		<td>
			<c:forEach items="${user.roles}" var="role">
				<c:if test="${role.type == 1 }">基础员工&nbsp; </c:if>
				<c:if test="${role.type == 2 }">经理&nbsp; </c:if>
				<c:if test="${role.type == 3 }">管理员&nbsp; </c:if>
			</c:forEach>
		</td>
		<td>
			<c:forEach items="${user.roles}" var="role">				
					${role.name}&nbsp;			
			</c:forEach>
		</td>
		<td>
			<c:forEach items="${user.pidList}" var="pid">				
					${pid.username}&nbsp;			
			</c:forEach>
		</td>	
		<td>
			<c:if test="${user.locked == 1}"><span style="color: red;">锁定</span></c:if>
			<c:if test="${user.locked == 0}"><span style="color: green;">启用</span></c:if>
		</td>
		<td nowrap="nowrap">
		<shiro:hasPermission name="user:detail"><input type="button" onclick="openWin('用户详情[${user.username}]','common/user/${user.id }/toDetail',400, 600)" class="blue" value="详情"></shiro:hasPermission>
		
		<shiro:hasPermission name="user:lock">
			<c:if test="${user.locked == 1}"><input type="button" onclick="oper(${user.id},${user.locked})" class="blue" value="启用"></c:if>
			<c:if test="${user.locked == 0}"><input type="button" onclick="oper(${user.id},${user.locked})" class="green" value="锁定"></c:if>
		</shiro:hasPermission>
		<shiro:hasPermission name="user:update"><input type="button" class="blue" value="修改" onclick="openWin('修改用户[${user.username}]','common/user/${user.id }/toUpdate',400, 600)"></shiro:hasPermission>
		<shiro:hasPermission name="user:delete"><input type="button" onclick="oper('${user.id}','del')" class="blue" value="删除"></shiro:hasPermission>
		<shiro:hasPermission name="user:setPWD"><input type="button" onclick="openWin('修改密码[${user.username}]','common/user/${user.id }/toModify',400, 200)" class="blue" value="修改密码"></shiro:hasPermission>
		<shiro:hasPermission name="user:resetPWD"><input type="button" onclick="oper('${user.id}','reset')" class="blue" value="重置密码"></shiro:hasPermission></td>
	
	</tr>
	</c:forEach>
</table>
<%@include file="/WEB-INF/jsp/common/pager.jsp" %>
</form>
</body>
</html>

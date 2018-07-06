<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>请假管理</title>
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
	$("#searchForm").attr("action", "leaveBill/index/${relID}");
	$("#searchForm").submit();
}

function del(id){
	var msg = "确定要【删除】吗？";
	var url = "leaveBill/" + $.trim(id) + "/delete" ;
	
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

function apply(relID){
	top.$("#" + relID + " .closer").click();
	var url = "common/baseData/index";
	var title = "我的业务";
	top.addCustTab(url,relID,title); 
}

</script>
</head>

<body>
<form name="form" id="searchForm" method="post" action="">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div class="accordion">
	<h3>个人请假列表</h3>
	<div align="right">
	   <table width="85%" class="search">
			<tr>
		    	
				<td>请假类型：</td>
				<td>
					<form:select id="billType" path="dto.billType" onchange="searchResetPage()">
						<option value="">全部</option>
						<form:options items="${leaveTypeList}" itemValue="id" itemLabel="value"/>
					</form:select>
				</td>
				<td>请假时间：</td>
				<td>
					<input type="text" name="beginDate" id="beginDate" onfocus="setMaxDate('endDate')">
				-
					<input type="text" name="endDate" id="endDate" onfocus="setMinDate('beginDate')">
				</td>
				<td>
				<input type="button"   class="blue" value ="查询" onclick="searchResetPage()">
				<input type="button"   class="blue"  value ="添加" onclick="openWin('添加', 'leaveBill/add/${relID}', 400, 400)">
			    </td>
			</tr>
		</table>
	</div>
</div>
<table class="listtable" width="100%">
	<tr>
		<th width="5%" nowrap="nowrap">ID</th>
		<th width="10%" nowrap="nowrap">请假类型</th>
		<th width="5%" nowrap="nowrap">请假天数</th>
		<th width="10%" nowrap="nowrap">开始时间</th>
		<th width="10%" nowrap="nowrap">结束时间</th>
		<th width="5%" nowrap="nowrap">状态</th>
		<th width="20%" nowrap="nowrap">说明</th>		
		<th width="10%" nowrap="nowrap">申请时间</th>		
		<th nowrap="nowrap">操作</th>
	</tr>
	<c:forEach items="${dto.dataList}" var="lb">
	<tr align="center">
		<td>${lb.id}</td>
		<td>${lb.baseData.value}</td>
		<td>${lb.days}</td>
		
		<td><fmt:formatDate value="${lb.beginDate}" pattern="yyyy-MM-dd HH:mm"/></td>
		<td><fmt:formatDate value="${lb.endDate}" pattern="yyyy-MM-dd HH:mm"/></td>		
		<td>
		<c:if test="${lb.delFlag==0}">初始录入</c:if>
		<c:if test="${lb.delFlag==1}">审核中</c:if>
		<c:if test="${lb.delFlag==2}">审核完成</c:if>
		</td>
		<td class='shorten30'>${lb.remark }</td>		
		<td><fmt:formatDate value="${lb.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>		
		<td nowrap="nowrap">
		<c:if test="${lb.delFlag==0}">
			<input type="button" onclick="openWin('修改[${lb.id}]','leaveBill/${lb.id}/update/${relID}',400, 400)" class="blue" value="修改">
			<input type="button" onclick="del('${lb.id}')" class="blue" value="删除">
			<input type="button" onclick="apply('${relID}')" class="blue" value="申请请假">
		</c:if>
		<c:if test="${lb.delFlag==1}">
			<input type="button" onclick="openWin('查看审核记录[${user.username}]','common/user/${lb.id }/toDetail',400, 600)" class="blue" value="查看审核记录">
		</c:if>
		<c:if test="${lb.delFlag==2}">
			<input type="button" onclick="openWin('删除[${user.username}]','common/user/${lb.id }/toDetail',400, 600)" class="blue" value="删除">
			<input type="button" onclick="openWin('查看审核记录[${user.username}]','common/user/${lb.id }/toDetail',400, 600)" class="blue" value="查看审核记录">
		</c:if>
	</tr>
	</c:forEach>
</table>
<%@include file="/WEB-INF/jsp/common/pager.jsp" %>
</form>
</body>
</html>

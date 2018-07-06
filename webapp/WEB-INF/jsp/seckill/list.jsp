<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>秒杀商品列表</title>
<script type="text/javascript">
$(document).ready(function(){
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
});
</script>
</head>
<body>
<div class="accordion">
	<h3>秒杀列表</h3>
	<div align="right">
		<table width="85%" class="search">
			<tr></tr>
		</table>
	</div>
</div>
<table class="listtable" width="100%">
	<tr>
		<th>名称</th>
		<th>库存</th>
		<th>开始时间</th>
		<th>结束时间</th>
		<th>创建时间</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${list}" var="sk">
		<tr align="center">
			<td>${sk.name }</td>
			<td>${sk.number }</td>
			<td><fmt:formatDate value="${sk.startTime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
			<td><fmt:formatDate value="${sk.endTime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
			<td><fmt:formatDate value="${sk.createTime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
			<td><input type="button" class="blue" value="详情" onclick="layer.full(openWin('秒杀详情数据', 'seckill/${sk.seckillId}/detail', 800, 600))">　
			<input type="button" class="blue" value="清空cookie" onclick="Cookies.set('userPhone', null, { path: '/baseFrame'});layer.msg('清空cookie成功！')"></td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
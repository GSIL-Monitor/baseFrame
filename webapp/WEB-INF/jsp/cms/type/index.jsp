<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>	
<title>栏目管理</title>
<script type="text/javascript">
$(document).ready(function(){
	
	tabpanel = new TabPanel({
		renderTo : 'tabs',
		border : 'none',
		active : 0,
		width:'100%',    
		height : autoHeight,
		autoResizable : true,
		items: [
			<c:forEach items="${list}" var="item" varStatus="status">
			{
				id:"${item.type}",
				title:"${item.name}",
				closable:false,
				<c:if test="${status.index!=0}">
				lazyload:true,
				</c:if> 
				html:"<iframe src=${item.url} width='100%' height='99%' scrolling='false' frameborder='0'></iframe>"
			},
			</c:forEach>
		]
	});
});//初始化

function autoHeight() {
	var pHeight =   document.documentElement.clientHeight;
	var realHeight = pHeight -10;
	return realHeight +"px";
} 
</script>
</head>
<body style="margin:0px;">	
	<div id="tabs"></div>	
</body>
</html>
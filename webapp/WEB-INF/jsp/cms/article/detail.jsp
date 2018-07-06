<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>文章</title>
<style>.font{ font-family:"微软雅黑"; font-size:15px}</style>
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
<div align="center" style="padding: 20px" class="font">
	<h1>${article.title}</h1>
	<span>作者：${article.addPerson }</span>　<span>日期：<fmt:formatDate value="${article.addTime}" pattern="yyyy-MM-dd"></fmt:formatDate></span>　<span>浏览量：${article.hits}</span>　<a href="javascript:void(0)" onclick="parent.layer.close(parent.layer.index)">关闭</a>　
	<div align="left">
		${article.content }
	</div>
</div>

</body>
</html>
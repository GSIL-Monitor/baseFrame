<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>流程管理</title>

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
/*排序样式*/
.listtable th span{background:url(res/img/line.png) 3px center no-repeat; cursor:pointer; padding-left:18px}
</style>
<script type="text/javascript">
$(document).ready(function(){
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});		
});

function showPic(id,name){
	var url = "proc/" + id + "/view";
	layer.open({
		  type: 2,
		  title: name +' - 预览',
		  area: ['800px', '600px'],
		  shade: 0.8,
		  maxmin: true,
		  //closeBtn: 0,
		  shadeClose: true,
		  content: [url]
	});
}

function deleteProcess(id){
	var url = "proc/" + id + "/delete";
	var msg = "确定要删除部署ID:【" + id + "】流程定义吗？";
	layer.confirm(msg, {icon: 3}, function(){
        $.ajax({
            type: "POST",
            url: url,
            traditional: true,
            async: false,
            success: function(data) {
            	window.location.href = "proc/index";
            }
        });
    });
}
</script>
</head>

<body>
	<!-- 流程定义列表  -->
	<form name="form" id="procDefForm" method="post" action="">		
		<div class="accordion">
			<h3>流程定义列表</h3>
			<div align="right">
				<table width="85%" class="search">
					<tr>
						<td>名称：</td>
						<td></td>						
						<td>KEY：</td>
						<td></td>
						<td>版本：</td>
						<td>
						<td>
							<input type="button" class="blue" value="查询" onclick="searchResetPage()"> 							
						</td>
					</tr>					
				</table>
			</div>
		</div>
		<table class="listtable" width="100%">
			<tr>
				<th>ID</th>
				<th>部署ID</th>	
				<th>名称</th>
				<th>KEY</th>
				<th>资源文件</th>		
				<th>资源图片</th>	
				<th>版本</th>	
				<th>操作</th>	
			</tr>
			<c:forEach items="${pdList}" var="pd">
				<tr>
					<td>${pd.id }</td>
					<td>${pd.deploymentId }</td>
					<td>${pd.name }</td>
					<td>${pd.key }</td>
					<td>${pd.resourceName }</td>
					<td>
						<a href="javascript:void(0);" onclick="showPic('${pd.deploymentId }','${pd.diagramResourceName }')">
							${pd.diagramResourceName }
						</a>
					</td>
					<td>${pd.version }</td>
					<td>
						
						<input type="button" class="blue" value="删除" onclick="deleteProcess('${pd.deploymentId }')">
					</td>
				</tr>
			</c:forEach>	
		</table>
		<%-- <%@include file="/WEB-INF/jsp/common/pager.jsp" %> --%>
	</form>
	<br/>
	<!-- 流程部署列表 -->
	<form name="procDeployForm" id="procDeployForm" method="post" action="">		
		<div class="accordion">
			<h3>流程部署列表</h3>
			<div align="right">
				<table width="85%" class="search">
					<tr>
						<td>部署ID：</td>
						<td></td>
						<td>部署名称：</td>
						<td></td>						
						<td>部署时间：</td>
						<td>
						</td>
						<td>
							<input type="button" class="blue" value="查询" onclick="searchResetPage()"> 							
						</td>
					</tr>
				</table>
			</div>
		</div>
		<table class="listtable" width="100%">
			<tr>
				<th>部署ID</th>
				<th>部署名称</th>
				<th>部署时间</th>				
			</tr>
			<c:forEach items="${deployList}" var="d">
				<tr>
					<td>${d.id }</td>
					<td>${d.name }</td>
					<td>
						<fmt:formatDate value="${d.deploymentTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
			</c:forEach>
		</table>
		<%-- <%@include file="/WEB-INF/jsp/common/pager.jsp" %> --%>
	</form>
	<br/>
	<!-- 部署流程 -->
	<form name="procDeployingForm" id="procDeployingForm" action="proc/deploy" enctype="multipart/form-data" method="post">
		<div class="accordion">
			<h3>流程部署</h3>
			<div align="right">
				<table width="100%" >
					<tr>
						<td width="150px">部署名称：</td>
						<td><input type="text" name="name"/></td>
					</tr><tr>
						<td>上传流程(.zip)：</td>
						<td><input type="file" name="file"/></td>	
					</tr><tr>					
						<td>
							<input type="submit" class="blue" value="部署流程"/> <input type="reset" class="blue" value="重置"/>							
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>

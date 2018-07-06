<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>基础数据管理</title>

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
var realNameArr = new Array();
var userArr = new Array();

$(document).ready(function(){
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
	
	$('#statusFlagSet').buttonset();
	$('#statusFlagSet').click(function() {
		search();
	});
});


function search() {
	$("#searchForm").attr("action", "common/baseData/index");
	$("#searchForm").submit();
}

function operBase(id,value){
	var msg="";
	var url = "common/baseData/" + id + "/chgFlag/" + value;
	if( 0 == value){
		msg = "确定要【启用】吗？";
	}else if( 1 == value){
		msg = "确定要【禁用】吗？";
	}else if( null == value){
		msg = "确定要【删除】吗？";
		url = "common/baseData/" + id + "/delete";
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

function showPic(url){	
	layer.open({
		  type: 2,
		  title: false,
		  area: ['512px', '384px'],
		  shade: 0.8,
		  closeBtn: 0,
		  shadeClose: true,
		  content: [url, 'yes']
	});
}
</script>
</head>

<body>
	<form name="form" id="searchForm" method="post" action="">
		<input type="hidden" name="tokenKey" value="${tokenKey}"> <input
			type="hidden" name="tokenValue" value="${tokenValue}">
		<div class="accordion">
			<h3>基础数据列表</h3>
			<div align="right">
				<table width="85%" class="search">
					<tr>
						<td>KEY：</td>
						<td><form:select id="key" path="dto.key"
								onchange="searchResetPage()">
								<option value="">全部</option>
								<form:options items="${dto.keyList}"/>
							</form:select></td>						
						<td>状态：</td>
						<td>
							<div id="statusFlagSet">								
								<form:radiobutton path="dto.delFlag" value="-1" label="全部"/>
								<form:radiobutton path="dto.delFlag" value="0" label="启用"/>
								<form:radiobutton path="dto.delFlag" value="1" label="禁用"/>
							</div>
						</td>
						<td>
							<input type="button" class="blue" value="查询" onclick="searchResetPage()"> 
							<shiro:hasPermission name="baseData:add">
								<input type="button" class="blue" value="添加" onclick="openWin('添加基础数据', 'common/baseData/add', 410, 358)">
							</shiro:hasPermission>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<table class="listtable" width="100%">
			<tr>
				<th>ID</th>
				<th>键</th>
				<th>值</th>
				<th>图标</th>
				<th>排序</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${ dto.dataList}" var="baseData">
				<tr align="center">
					<td>${baseData.id }</td>
					<td>${baseData.key }</td>
					<td >
						${baseData.value }
					</td>
					<td>
						<c:if test="${baseData.url != null and baseData.url!=''}">
						<a href="javascript:void(0);" onclick="showPic('<%=hostPath%>/upload/images/${baseData.url}')">
				        	<img style="vertical-align:middle" src="<%=hostPath%>/upload/images/${baseData.url}" width="25px" height="25px"/>
				        </a>
				        </c:if>
					</td>
					<td>${baseData.seq }</td>
					<td>
						<c:if test="${baseData.delFlag ==0}"><span style="color:#3C966E">启用</span></c:if> 
						<c:if test="${baseData.delFlag ==1}"><span style="color:#5A5A5A">禁用</span></c:if>
					</td>
					<td>
						<shiro:hasPermission name="baseData:update">
						<input type="button" onclick="openWin('修改基础数据', 'common/baseData/'+${baseData.id}+'/update', 410, 358)" class="blue" value="修改">
						</shiro:hasPermission>
						<shiro:hasPermission name="baseData:delete">
							<input type="button" onclick="operBase('${baseData.id}',null)" class="blue" value="删除">
						</shiro:hasPermission>
						<shiro:hasPermission name="baseData:chgFlag">
							<c:if test="${baseData.delFlag ==0}">
								<input type="button" onclick="operBase('${baseData.id}',1)"
									class="blue" value="禁用">
							</c:if> <c:if test="${baseData.delFlag ==1}">
								<input type="button" onclick="operBase('${baseData.id}',0)"
									class="blue" value="启用">
							</c:if>
						</shiro:hasPermission>
						</td>
				</tr>

			</c:forEach>
		</table>
		<%@include file="/WEB-INF/jsp/common/pager.jsp" %>
	</form>
</body>
</html>

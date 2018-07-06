<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>${title} - 文章管理</title>

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
	/**
	$("#searchForm").attr("action", "article/${dto.typeId}/${dto.cid}");
	$("#searchForm").submit();   
	
	var target = "article/${dto.typeId}/${dto.cid}";
	$.get(target,function(data){  		    	
        $("#iframeContent").html(data);   
    
	}); 
	*/
	var url = "article/${dto.typeId}/${dto.cid}";
	var formData = new FormData($("#searchForm")[0]);
	$.ajax({
		url: url ,
		type: 'POST',
		data: formData,
		async: false,
		cache: false,
		contentType: false,
		processData: false,
		success: function (result) {
			$("#iframeContent").html(result);  
		}
		
	});
}

function operBase(id,value){
	msg = "确定要删除【" + value + "】的文章吗？";
	url = "article/delete/" + id ;
	
	layer.confirm(msg, {icon: 3}, function(){
        $.ajax({
            type: "POST",
            url: url,
            traditional: true,
            async: false,
            success: function(data) {
            	layer.msg(data);
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
		<input type="hidden" name="typeId" value="${dto.typeId}">
		<input type="hidden" name="cid" value="${dto.cid}">	
		<div class="accordion">
			<h3>${title} - 文章列表</h3>
			<div align="right">
				<table width="85%" class="search">
					<tr>
						<td>标题：</td>
						<td></td>						
						<td>简介：</td>
						<td></td>
						<td>访问次数：</td>
						<td></td>	
						<td>添加时间：</td>
						<td></td>	
						<td>
							<input type="button" class="blue" value="查询" onclick="searchResetPage()"> 
							<input type="button" class="blue" value="发表" onclick="layer.full(openWin('发表文章', 'article/addUI/${typeId}/${cid}', 410, 358))">
						</td>
					</tr>
				</table>
			</div>
		</div>
		<table class="listtable" width="100%">
			<tr>
				<th nowrap="nowrap">标题</th>
				<th nowrap="nowrap">简介</th>
				<th nowrap="nowrap">访问次数</th>
				<th nowrap="nowrap">作者</th>
				<th nowrap="nowrap">添加时间</th>
				<th nowrap="nowrap">操作</th>
			</tr>
			<c:forEach items="${ dto.dataList}" var="data">
				<tr align="center">
					<td><a href="javascript:void(0)" onclick="layer.full(openWin('简介　${data.summary}', 'article/detail/${data.id}', 410, 358))">${data.title }</a></td>
					<td class='shorten20'>
						${data.summary }
					</td>
					<td>${data.hits}</td>
					<td>${data.addPerson }</td>
					<td>
						<fmt:formatDate value="${data.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<input type="button" onclick="layer.full(openWin('文章编辑', 'article/updateUI/' + ${data.id}, 410, 358))" class="blue" value="编辑">
						<!-- 
						<c:if test="${data.available ==0}">
								<input type="button" onclick="operBase('${data.id}',1)"
									class="blue" value="禁用">
						</c:if> 
						<c:if test="${data.available ==1}">
								<input type="button" onclick="operBase('${data.id}',0)"
									class="blue" value="启用">
							</c:if>
						 -->
						<input type="button" onclick="operBase('${data.id}','${data.title }')" class="blue" value="删除">
							
					</td>
				</tr>
			</c:forEach>
		</table>
		<%@include file="/WEB-INF/jsp/common/pager.jsp" %>
	</form>
</body>
</html>

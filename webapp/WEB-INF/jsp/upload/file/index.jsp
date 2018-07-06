<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>${title}管理</title>

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
	//iframe重新单独加载
	/*var target = "file/index/${url}";
	console.log(target);
	$.get(target,function(data){  		    	
	        $("#iframeContent").html(data);   
	});*/
	var url = "file/index/${url}";
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

function operBase(id,flag,value){
	var msg="";
	if( null == flag){
		msg = "确定要删除【" + value + "】的文件吗？";
		url = "file/delete/" + id ;
	}
	layer.confirm(msg, {icon: 3}, function(){
        $.ajax({
            type: "GET",
            url: url,
            traditional: true,
            async: false,
            success: function(data) {
            	layer.close(layer.index);
            	if(data == 'success')
            		layer.msg('磁盘文件删除成功!');
            	else if(data == 'failure')
            		layer.msg('磁盘文件不存在,删除失败!');
            	search();
            }
        });
        
    });
}

function downLoad(id,value){
	var url = "file/download2/" + id ;
	msg = "确定要下载【" + value + "】的文件吗？";
	layer.confirm(msg, {icon: 3}, function(){
		window.location.href = url;
		layer.close(layer.index);
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
			<h3>${title}列表</h3>
			<div align="right">
				<table width="85%" class="search">
					<tr>
						<td>文件名称：</td>
						<td></td>						
						<td>文件类型：</td>
						<td></td>
						<td>文件大小：</td>
						<td></td>	
						<td>具传时间：</td>
						<td></td>	
						<td>
							<input type="button" class="blue" value="查询" onclick="searchResetPage()"> 
							<input type="button" class="blue" value="上传" onclick="openWin('上传文件', 'file/addUI/${typeId}/${cid}', 410, 358)">
						</td>
					</tr>
				</table>
			</div>
		</div>
		<table class="listtable" width="100%">
			<tr>
				<th>文件名</th>
				<th>文件类型</th>
				<th>文件URL</th>
				<th>文件大小</th>
				<th>上传时间</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${ dto.dataList}" var="file">
				<tr align="center">
					<td>${file.fileName }</td>
					<td >
						${file.fileType }
					</td>
					<td>${file.url}</td>
					<td>${file.fileSizeWithUnit }</td>
					<td>
						<fmt:formatDate value="${file.uploadTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<c:if test="${file.onlineRead }">
							<input type="button" onclick="layer.full(openWin('在线阅读', '<c:url value='/pdfjs/web/viewer.html'/>?file=<c:url value='/file/stream/${file.id}'/>', 410, 358))" class="blue" value="阅读">
						</c:if>
						<input type="button" onclick="downLoad('${file.id}','${file.fileName}')" class="blue" value="下载"/>						
						<input type="button" onclick="operBase('${file.id}',null , '${file.fileName}')" class="blue" value="删除"/>
							
					</td>
				</tr>
			</c:forEach>
		</table>
		<table>
				<tr>
					<!-- <td><input type="button" onclick="operBase('下载文件', 'upload/batch',410,358)" class="blue" value="下载"></td> -->
					<td><%@include file="/WEB-INF/jsp/common/pager.jsp" %></td>
				</tr>
		</table>
		
	</form>
</body>
</html>

<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<!DOCTYPE html>
<html>
<head>
<link href="res/css/demo.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	$("#categoryForm").validate({
		submitHandler:function(form){
		    addOrUpdate();
		}
	});
	
});

function addOrUpdate() {
	var url = "cms/type/addCategoryChild";
	if ("${category.id}" > 0) {
		url = "cms/type/updateCategoryChild";
	}
	
	$.ajax({
		type: "POST",
		url: url,
		data: $("#categoryForm").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {			
			//window.parent.location.href = "common/resource/index";		
			parent.location.reload();
		}
	});
}



var setting = {
	check: {
		enable: true,
		chkStyle: "radio",
		radioType: "all"
	},
	view: {
		dblClickExpand: false
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		onClick: onClick,
		onCheck: onCheck
	}
};

var zNodes = new Array();
zNodes.push({id:0,pId:0,name:'root',open:true});
<c:forEach items="${menuList}" var="menu">
	zNodes.push({id:'${menu.id}', pId:'${menu.parentid}', name:'${menu.name}', open:true});
</c:forEach> 

function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

function onCheck(e, treeId, treeNode) {
	
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	nodes = zTree.getCheckedNodes(true),
	v = "";
	id = "";
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
		id +=nodes[i].id + ","
	}
	
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (id.length > 0 ) id = id.substring(0, id.length-1);
	
	//判断:父节点不能挂载到自己子节点
	self = $("#id").val();
	console.log("self : "+self);
	var str ='' ;	
	var node =  zTree.getNodeByParam("id", self, null);
    str = ','+getAllChildrenNodes(node,str);
	console.log(str + " | " + self + " | " + id);
	if(self!='' && str.indexOf(id)!=-1 && str.indexOf(','+id)!=-1 ){
		layer.msg('父节点不能挂载到自己子节点');return false;
	}else if(self==id){
		layer.msg('不能挂载到本节点');return false;
	}
	
	var ids = getPath(treeNode);
	var cityObj = $("#selpid");
	var IdObj = $("#parentid");
	var IdsObj = $("#parentids");
	cityObj.attr("value", v);
	IdObj.attr("value", id);
	IdsObj.attr("value", ids);
}

function showMenu() {
	var cityObj = $("#selpid");
	var cityOffset = $("#selpid").offset();
	console.log(cityOffset);
	$("#menuContext").css({ left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContext").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "selpid" || event.target.id == "menuContext" || $(event.target).parents("#menuContext").length>0)) {
		hideMenu();
	}
}

</script>
</head>

<body>
<form id="categoryForm" method="post" enctype="multipart/form-data">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<form:hidden path="category.id"/>
<form:hidden path="category.parentid"/>
<form:hidden path="category.parentids"/>
<form:hidden path="category.cid"/>
<table class="datatable">
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>&nbsp;资源名称：</th>
		<td><form:input path="category.name" cssClass="required"/></td>
	</tr>
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>&nbsp;父资源名称：</th>
		<td>
		<input id="selpid" name="selpid" type="text" readonly value="${category.pName }" onclick="showMenu();"/>
		&nbsp;<a id="menuBtn" href="#" onclick="showMenu(); return false;">select</a>
		</td>
	</tr>
	<tr>
        <th align="right" width="100">&nbsp;图标：</th>
        <td><input type="file" name="file"/>
        <c:if test="${category.icon != null}">
        	<img src="<%=hostPath%>/upload/images/${category.icon}" width="100" height="100" onclick="showPic(this)"/>
        </c:if>       
        </td>
    </tr>
	<tr>
		<th align="right"><span style="color: red">*</span>&nbsp;访问地址：</th>
		<td><form:input path="category.url" /></td>
	</tr>
	<tr>
		<th align="right">排序：</th>
		<td><form:input path="category.sortstring" cssClass="digits"/></td>
	</tr>
	<tr>
		<th align="right">是否可用：</th>
		<td>
			<form:radiobutton path="category.available" value="0" label="否"/>　
			<form:radiobutton path="category.available" value="1" label="是"/>
		</td>
	</tr>	
	<tr>
		<th align="right">操作：</th>
		<td><input type="submit" class="blue" value="提交"></td>
	</tr>
</table>
</form>
<div id="menuContext" class="menuContent" style="display:none; position: absolute;">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:180px; height: 200px;"></ul>
</div>
</body>
</html>



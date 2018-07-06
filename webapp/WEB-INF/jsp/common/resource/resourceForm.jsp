<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<!DOCTYPE html>
<html>
<head>
<link href="res/css/demo.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	$("#resourceForm").validate({
		submitHandler:function(form){
		    addOrUpdate();
		}
	});
	
});

function addOrUpdate() {
	var url = "common/resource/addChild";
	if ("${resource.id}" > 0) {
		url = "common/resource/updateChild";
	}
	
	$.ajax({
		type: "POST",
		url: url,
		data: $("#resourceForm").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {			
			window.parent.location.href = "common/resource/index";			
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
	zNodes.push({id:'${menu.id}', pId:'${menu.pid}', name:'${menu.name}', open:true});
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
	var IdObj = $("#pid");
	var IdsObj = $("#pids");
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
<form id="resourceForm" method="post">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<form:hidden path="resource.id"/>
<form:hidden path="resource.pid"/>
<form:hidden path="resource.pids"/>

<table class="datatable">
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>&nbsp;资源名称：</th>
		<td><form:input path="resource.name" cssClass="required"/></td>
	</tr>
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>&nbsp;父资源名称：</th>
		<td>
		<input id="selpid" name="selpid" type="text" readonly value="${resource.pName }" onclick="showMenu();"/>
		&nbsp;<a id="menuBtn" href="#" onclick="showMenu(); return false;">select</a>
		</td>
	</tr>
	<tr>
		<th align="right">资源类型：</th>
		<td>
			<form:radiobutton path="resource.type" value="menu" label="菜单"/>　
			<form:radiobutton path="resource.type" value="perm" label="权限"/>
		</td>
	</tr>
	<tr>
		<th align="right"><span style="color: red">*</span>&nbsp;访问地址：</th>
		<td><form:input path="resource.url" /></td>
	</tr>
	<tr>
		<th align="right">权限标识符：</th>
		<td><form:input path="resource.percode" placeholder="资源:操作:实例"/></td>
	</tr>
	<tr>
		<th align="right">排序：</th>
		<td><form:input path="resource.sortstring" cssClass="digits"/></td>
	</tr>
	<tr>
		<th align="right">是否可用：</th>
		<td>
			<form:radiobutton path="resource.available" value="0" label="否"/>　
			<form:radiobutton path="resource.available" value="1" label="是"/>
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



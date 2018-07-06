<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>资源管理</title>
<script type="text/javascript">
var resourceTree;

$(document).ready(function(){
	resourceInit();
});

function resourceInit() {	
	var setting = {
		view: {
			<shiro:hasPermission name="res:add">addHoverDom: addHoverDom,</shiro:hasPermission>
			removeHoverDom: removeHoverDom,
			selectedMulti: false,
		},
		edit: {
			enable: true,
			showRenameBtn: showEditBtn,
			renameTitle: "编辑节点",
			showRemoveBtn: showRemoveBtn,
			removeTitle: "删除节点"
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeEditName: beforeEditName,
			beforeRemove: beforeRemove,
			beforeDrag: beforeDrag
		}
	};
	
	var zNodes = new Array();
	zNodes.push({id:0,pId:0,name:'root',open:true});
	<c:forEach items="${resourceList}" var="resource">
		zNodes.push({id:'${resource.id}', pId:'${resource.pid}', name:'${resource.name}', open:true});
	</c:forEach> 
	
	resourceTree = $.fn.zTree.init($("#resourceTree"), setting, zNodes);
}

var newCount = 1;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加子节点' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		var ids = getPath(treeNode);
		console.log(ids);
		openWin('添加资源' + ' - [' + treeNode.name + ']', 'common/resource/' + treeNode.id + '/toAddChild/' + ids, 410, 358);
	});
};

function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};

function beforeEditName(treeId, treeNode) {
	resourceTree.selectNode(treeNode);
	var ids = getPath(treeNode);
	openWin('编辑资源' + ' - [' + treeNode.name + ']', 'common/resource/' + treeNode.id + '/toUpdateChild/' + ids, 410, 358);
	return false; // 不进入编辑状态
}

function beforeRemove(treeId, treeNode) {
	resourceTree.selectNode(treeNode);
	if (0 == treeNode.level) { // 根节点
		layer.alert("不允许删除根节点！~", {icon: 2});
		return false;
	}
	if (treeNode.isParent) { // 如果是父节点，则提示先删除子节点
		layer.alert("不允许直接删除父节点，请先删除子节点！~", {icon: 2});
		return false;
	}
	
	var msg = "确认删除节点 [" + treeNode.name + "] 吗？";
	layer.confirm(msg, {icon: 3}, function(index){
		layer.close(index);
	    $.ajax({
			type: "GET",
			url: "common/resource/" + treeNode.id + "/delete",
			async: false,
			success: function(data) {
				if ("Success" == data) {
					resourceTree.removeNode(treeNode);
				}else if("Failure" == data){
					layer.alert('此资源已关联角色，不允许删除', {
					    skin: 'layui-layer-lan',
					    closeBtn: 0,
					    shift: 4 //动画类型
					});
				}
			}
		});
	});
	
	return false;
}

function beforeDrag(treeId, treeNodes) {
	return false;
}

function showRemoveBtn(treeId, treeNode) {
	var flag = false;
	<shiro:hasPermission name="res:delete">flag = true</shiro:hasPermission>
	return 0 != treeNode.level && flag;
}

function showEditBtn(treeId, treeNode) {
	var flag = false;
	<shiro:hasPermission name="res:update">flag = true</shiro:hasPermission>
	return 0 != treeNode.level && flag;
}

</script>
</head>

<body>
	<ul id="resourceTree" class="ztree" style="overflow:auto;"></ul>
</body>
</html>

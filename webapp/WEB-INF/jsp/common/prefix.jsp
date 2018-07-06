<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String hostPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>

<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<style type="text/css">
.msg {
	margin-left: 3px;
	background: url("res/plugins/validation/images/unchecked.gif") no-repeat 0px 0px;
	padding-left: 16px;
	color: red
}
.left
{
text-align:left;
}
</style>
<link href="res/plugins/jquery-ui.css" rel="stylesheet" type="text/css">
<link href="res/plugins/layout/layout-default.css" rel="stylesheet" type="text/css">
<link href="res/plugins/wdScrollTab/css/TabPanel.css" rel="stylesheet" type="text/css">
<link href="res/plugins/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
<link href="res/plugins/layer/skin/layer.css" rel="stylesheet" type="text/css">
<link href="res/plugins/validation/jQuery.validate.css" rel="stylesheet" type="text/css">
<link href="res/css/bui.css" rel="stylesheet" type="text/css">

<script src="res/plugins/jquery-2.1.3.js" type="text/javascript"></script>
<script src="res/plugins/jquery-ui.js" type="text/javascript"></script>
<script src="res/js/reconnecting-websocket.min.js" type="text/javascript"></script>
<script src="res/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="res/plugins/layout/jquery.layout.js" type="text/javascript"></script>
<script src="res/plugins/wdScrollTab/src/Plugins/Fader.js" type="text/javascript"></script>
<script src="res/plugins/wdScrollTab/src/Plugins/TabPanel.js" type="text/javascript"></script>
<script src="res/plugins/wdScrollTab/src/Plugins/Math.uuid.js" type="text/javascript"></script>
<script src="res/plugins/zTree/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
<script src="res/plugins/zTree/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
<script src="res/plugins/zTree/js/jquery.ztree.exedit-3.5.js" type="text/javascript"></script>
<script src="res/plugins/layer/layer.js" type="text/javascript"></script>
<script src="res/plugins/validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="res/plugins/validation/messages_zh.js" type="text/javascript"></script>
<script src="res/plugins/jquery.base64.js" type="text/javascript"></script>
<script src="res/plugins/Base64CN.js" type="text/javascript"></script>
<script src="res/js/js.cookie.js"></script>
<script type="text/javascript">

$(function(){
	/* 显示指定长度的内容，剩余部分用“...”代替      使用方法：class='shorten数字' 数字为显示的字符长度  如class='shorten30' 则显示30个字符，其余用“...”代替*/
	$('td[class*=shorten]').each(function() {
		$(this).css("text-align","left");
		var classNames = $(this).attr("class");
		var classNameGroup = classNames.split(/[ ]+/);
		for(var key  in  classNameGroup){
				var className = classNameGroup[key];
				if (className.indexOf('shorten') !=- 1){
					var showLength = className.substring("shorten".length,className.length);
					var content = $.trim($(this).text());
					if (content.length > showLength) {
						var title = $(this).attr('title');
						if(title == undefined || title==''){
							title = $.trim($(this).text());
							$(this).attr('title',title);
						}
						content = content.substring(0, showLength);
						content += "...";
						$(this).text(content);
					}else{
						$(this).removeAttr('title');
					}
				break;
				}
		}
		
	});
});


function openWin(title, url, width, height) {
	return index=layer.open({
        type: 2,        
        shadeClose: true,
        maxmin: true,
        title: title,
        content: url,
        area: [width+'px', height+'px']
    });
}

function openContent(title, key, width, height) {
	var content = $("#" + key).html();
	return index=layer.open({
        type: 1,
        shade: false,
        maxmin: true,
        title: title,
        content: content,
        area: [width+'px', height+'px']
    });
}

//按钮置灰
function disableButton(input){
	$("input[name='"+input.name+"']").attr('disabled','disabled');
	$("input[name='"+input.name+"']").removeClass('blue');
	
};
//按钮去掉置灰样式
function enableButton(input){
	$("input[name='"+input.name+"']").removeAttr('disabled','disabled');
	$("input[name='"+input.name+"']").addClass('blue');

};

function searchResetPage(){	   
	  $('#pageNo').val(1);
	  search();
}

//根据treeNode遍历父亲节点,至null终止
function getPath(node){
	var ids = node.id;	
	while(node=node.getParentNode(ids)){
		ids = node.id + "," + ids;
	}
	/* if(ids!=0){
		ids = ids.substring(0,ids.lastIndexOf(','));
	} */
	return ids;
}

//根据treeNode遍历所有叶子节点,至null终止
function getAllChildrenNodes(treeNode,result){
    if (treeNode.isParent) {
      var childrenNodes = treeNode.children;
      if (childrenNodes) {
          for (var i = 0; i < childrenNodes.length; i++) {
              result +=  childrenNodes[i].id + ',';
              result = getAllChildrenNodes(childrenNodes[i], result);
          }
      }
  }
  return result;
}

function setMaxDate(id){
	var item = $("#" + id )
	if(item.val()==null || item.val()==''){
		WdatePicker({maxDate:'%y-{%M}-{%d}'});
	}else{
		WdatePicker({maxDate:'#F{$dp.$D(\''+id+'\')}'});
	}
}

function setMinDate(id){
	WdatePicker({minDate:'#F{$dp.$D(\''+id+'\')}',maxDate:'%y-{%M+1}-{%d}'});
}

function addCustTab(url,id,title){	
	var htmlStr = '<iframe id="'+id+'"   src="' + url + '" width="100%" height="99%" frameborder="0"></iframe>';
	tabpanel.addTab({
		 id: id,
		 title:title,
	     html: htmlStr
	 });
	
}

</script>
</head>
</html>

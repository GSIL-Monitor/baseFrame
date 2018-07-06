<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>BOSS系统</title>

<script type="text/javascript">
$().ready(function(){
	var template = jQuery.validator.format($.trim($('#template').val()));
	function addRow(){
		$(template(i++)).appendTo('.listtable');		
	}
	var i=1;
	addRow();
	$('#addRow').click(addRow);
	
	$("#checkAll").click(function(){
		var flag = this.checked;
		$('.listtable td :checkbox[name=cb]').each(function(){
			this.checked = flag;
		})
	});
	
	$("#deleteMultiRow").click(function(){
		if(valid()){
			$('.listtable td :checkbox[name=cb]').each(function(){
				if(this.checked){
					$(this).parent().parent().remove();
				}
			});
			//清空全选
			$("#checkAll").removeAttr("checked");
		}
	});
});

function valid(){
	if ($(':checkbox[name="cb"]:checked').length <= 0) {
		layer.alert("请至少选择一行！", {
			icon : 2
		});
		return false;
	}else
		return true;
}

function queryList(type){
	 var list = [];
	$('.listtable ').find('tr:gt(1)').each(function(){
		var obj = {};
		var i =  $(this).children('td').children('input[type=checkbox]').val();
		var IDS = "#id_" + i;
		var names = "#username_" + i;
		var codes = "#usercode_" + i;
		obj.id = $.trim($(IDS).val());
		obj.username = $.trim($(names).val());
		obj.usercode = $.trim($(codes).val());
		list.push(obj);

	});
	console.log(list);
	var objJson = JSON.stringify(list); 
	var	submitType = 'GET';
	var	url = 'app/user/testList';
	var	data = BASE64.encoder(objJson);
	if(type='get'){
		submitType = 'POST';
	}
	$.ajax({
		type: submitType ,
		url : url ,
		data : data,
		success:function(data){
			layer.msg(data);
		}
	});
}

function query(type,id){
	var obj = {};
	var ID = "#id_" + id;
	var name = "#username_" + id;
	var code = "#usercode_" + id;
	if($.trim($(ID).val())==''){
		layer.alert('用户ID必填');
		return false;
	}
	obj.id = $.trim($(ID).val());
	obj.username = $.trim($(name).val());
	obj.usercode = $.trim($(code).val());
	var objJson = JSON.stringify(obj); 
	var data =  BASE64.encoder(objJson);
	var	submitType = 'GET';
	var	url = 'app/user/test';
	if(type==1){
		submitType = 'GET';
	}else if(type==2){
		url = 'app/user/testObj'; 
	}else if(type==4){
		submitType = 'POST';
	}
	else if(type==5){
		submitType = 'POST';
		url = 'app/user/testObj'; 
	}
	$.ajax({
		type: submitType ,
		url : url ,
		data : data,
		success:function(data){
			layer.msg(data);
		}
	});
}

function deleteTr(nowTr){ 
	$(nowTr).parent().parent().remove(); 
}
</script>
</head>
<body>
<div>
	<table class="listtable" width="100%">
		<tr>
		<td colspan="5" style="text-align:left">
			<input type="button" class="blue" name="get" value="+添加一行" id="addRow" onclick="javascript:void(0)"/>　
			<input type="button" class="blue" name="get" value="删除选中行" id="deleteMultiRow" onclick="javascript:void(0)"/>
			<input type="button" class="blue" name="getByList" value="getByList" onclick="queryList('get')"/>　
			<input type="button" class="blue" name="postByList" value="postByList" onclick="queryList('post')"/>
		</td>
		</tr>
		<tr>
			<th><input type="checkbox" id="checkAll"/></th>
			<th>用户ID</th>
			<th>用户名称</th>
			<th>用户代码</th>
			<th>操作</th>
		</tr>
		<textarea style="display:none" id="template">
		<tr>
			<td><input name="cb" type="checkbox" id="cb_{0}" value="{0}"/></td>
			<td><input type="text" id="id_{0}"  onkeyup="if(/\D/.test(this.value)){layer.alert('只能输入数字');this.value='';}"/></td>
			<td><input type="text" id="username_{0}" /></td>
			<td><input type="text" id="usercode_{0}" /></td>
			<td>
				<input type="button" class="blue"　name="delete-{0}" value="删除" onclick='deleteTr(this);'/>　
				<input type="button" class="blue" name="get-{0}" value="get" onclick="query(1,{0})"/>　
				<input type="button" class="blue" name="getByObject-{0}" value="getByObject" onclick="query(2,{0})"/>　
				<input type="button" class="blue" name="post-{0}" value="post" onclick="query(4,{0})"/>　
				<input type="button" class="blue" name="postByObject-{0}" value="postByObject" onclick="query(5,{0})"/>　
			</td>
		</tr>
		</textarea>		
	</table>
	
</div>
</body>
</html>

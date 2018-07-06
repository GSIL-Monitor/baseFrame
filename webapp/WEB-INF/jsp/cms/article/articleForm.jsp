<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<%@ taglib uri="http://cksource.com/ckfinder" prefix="ckfinder" %>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<html>
<head>
<script type="text/javascript">
$(document).ready(function(){
	$("#articleForm").validate({
		rules:{
		    seq:{
    	  		required:false,
    	  		digits:true  
            }  
		},
		messages:{
        	seq:{required:"请输入排序数字", digits: "排序：请输入整数"	},
        },
		submitHandler:function(form){
		    addOrUpdate();
		}
	});
	
});

//重置表单
function resetForm() {   
    $('#articleForm').each(function() {
        this.reset(); //将表单的元素恢复初始化
        //清空ckeditor
        CKEDITOR.instances.content.setData('');  
    });
}

function addOrUpdate() {
	var url = "article/add";
	if ("${article.id}" > 0) {
		url = "article/update";  
	}
	 //JavaScript判空，如果确定  
    var editor_data = CKEDITOR.instances.content.getData();  
    if(editor_data==null||editor_data==""){  
        layer.alert("内容数据为空不能提交",{icon:2});
        return false;
    }
	
	//处理CKEDITOR的值
	for (instance in CKEDITOR.instances){
        CKEDITOR.instances[instance].updateElement();
	}
	
	var formData = new FormData($("#articleForm")[0]);
	$.ajax({
		url: url ,
		type: 'POST',
		data: formData,
		async: false,
		cache: false,
		contentType: false,
		processData: false,
		success: function (result) {
			if(result){
				if(result.retCode=="0"){
					$("#publish").attr("disabled",true).removeClass("blue");;
			  		var parentWin = window.parent;
			  		layer.msg(result.resMsg, function() {			
						parentWin.search();
			  		});
			  		setTimeout(function (){
						parent.layer.closeAll();
					},3000);
				}else{
					layer.alert(result.resMsg,{icon:2});
				}
			}
		}
		
	});
	
}

</script>
</head>

<body>
<form id="articleForm" method="post">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<form:hidden path="article.typeId"/>
<form:hidden path="article.cid"/>
<form:hidden path="article.id"/>
<table class="datatable">
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>&nbsp;标题：</th>
		<td>
		  <form:input id="title"  path="article.title" cssClass="required"/>
		</td>
	</tr>
	<tr>
        <th align="right" width="100">&nbsp;简介：</th>
        <td>
        	<form:input id="summary"  path="article.summary"/>
        </td>
    </tr>
    <tr>
        <th align="right" width="100"><span style="color: red">*</span>&nbsp;内容：</th>
        <td>
        	<form:textarea path="article.content" id="content" cssClass="required"/>
        </td>
    </tr>
    
	<tr>
		<th align="right">操作：</th>
		<td><input type="submit" class="blue" id="publish" value="发表">
		<input type="button" class="blue" value="重置" onclick="resetForm()">
		<input type="button" class="blue" value="关闭" onclick="parent.layer.close(parent.layer.index);">
		</td>
	</tr>
</table>
</form>
<ckfinder:setupCKEditor basePath="ckfinder/" editor="content" />
<ckeditor:replace replace="content" basePath="ckeditor/" />
</body>
</html>

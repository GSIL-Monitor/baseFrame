<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<html>
<head>
<script type="text/javascript">
$(document).ready(function(){
	$("#baseDataForm").validate({
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

function addOrUpdate() {

	var url = "common/baseData/add";
	if ("${baseData.id}" > 0) {
		url = "common/baseData/update";
	}
		
	var formData = new FormData($("#baseDataForm")[0]);
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
			  		var parentWin = window.parent;
					parentWin.search();
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
<form id="baseDataForm" method="post"  enctype="multipart/form-data" >
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<form:hidden path="baseData.url"/>
<form:hidden path="baseData.id"/>
<table class="datatable">
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>&nbsp;Key：</th>
		<td>
		  <c:if test="${baseData.key != null}">
		  	<form:select id="key" path="baseData.key" disabled="true">
              <form:options items="${baseData.keyList}" />
         	</form:select>
         	<form:hidden path="baseData.key"/>
		  </c:if>
		  <c:if test="${baseData.key eq null}">
		  	<form:select id="key" path="baseData.key">
              <form:options items="${baseData.keyList}" />
         	</form:select>
		  </c:if>
		</td>
	</tr>
	<tr>
        <th align="right" width="100"><span style="color: red">*</span>&nbsp;Value：</th>
        <td><form:input id="value"  path="baseData.value" cssClass="required"/>
        <c:if test="${msg != null}">${msg}</c:if>
        <span id="msg" style="color:red"/>
        </td>
    </tr>
    <tr>
        <th align="right" width="100"><span style="color: red">*</span>&nbsp;排序：</th>
        <td><form:input id="seq"  path="baseData.seq" class="digits"/>
        <c:if test="${msg != null}">${msg}</c:if>
        <span id="msg" style="color:red"/>
        </td>
    </tr>
    <tr>
        <th align="right" width="100">&nbsp;图标：</th>
        <td><input type="file" name="file"/>
        <c:if test="${baseData.url != null}">
        	<img src="<%=hostPath%>/upload/images/${baseData.url}" width="100" height="100" onclick="showPic(this)"/>
        </c:if>       
        </td>
    </tr>
	<tr>
		<th align="right">操作：</th>
		<td><input type="submit" class="blue" value="提交"></td>
	</tr>
</table>
</form>
</body>
</html>

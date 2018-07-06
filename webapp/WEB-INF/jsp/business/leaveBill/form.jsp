<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<html>
<head>
<script type="text/javascript">
$(document).ready(function(){	
	
	$("#leaveBill").validate({
		submitHandler:function(form){
		    addOrUpdate();
		}
	});
	
});

function addOrUpdate() {
	var url = "leaveBill/add";
	if ($('#id').val() > 0) {
		url = "leaveBill/update";
	}
	
	$.ajax({
		type: "POST",
		url: url,
		data: $("#leaveBill").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {
			if(data.retCode=="success" || data =="Success")
				window.parent.location.href = "leaveBill/index/${relID}";
		}
	});
}

</script>
</head>

<body>
<form id="leaveBill" method="post">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<form:hidden id="id" path="leaveBill.id"/>
<form:hidden id="id" path="leaveBill.userId"/>
<table class="datatable">
	<tr>
		<th>请假类型：</th>
		<td>
			<form:select id="billType" path="leaveBill.billType" items="${leaveTypeList}" itemLabel="value" itemValue="id"  cssClass="required" cssStyle="width:200px"></form:select>
		</td>
	</tr>
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>请假天数：</th>
		<td><form:input id="days"  path="leaveBill.days" cssClass="digits"/></td>
	</tr>
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>开始时间：</th>
		<td><input type="text" name="beginDate" id="beginDate" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm'})" class="required" value="<fmt:formatDate value='${leaveBill.beginDate }' pattern='yyyy-MM-dd HH:mm'/>"></td>
	</tr>
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>结束时间：</th>
		<td><input type="text" name="endDate" id="endDate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}',dateFmt:'yyyy-MM-dd HH:mm'});" class="required" value="<fmt:formatDate value='${leaveBill.endDate }' pattern='yyyy-MM-dd HH:mm'/>"></td>
	</tr>
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>说明：</th>
		<td><textarea rows="5" cols="25" name="remark">${leaveBill.remark}</textarea></td>
	</tr>
	<tr>
		<th align="right">操作：</th>
		<td><input type="submit" class="blue" value="提交"><input name="reset" type="reset" class="blue" value="重置"></td>		
	</tr>
</table>
</form>
</body>
</html>

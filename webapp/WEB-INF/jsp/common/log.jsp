<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<%
String base = request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>tail log</title>
<style>
	html,body
	{
		height:90%;
		width:99%;
	}
</style>
</head>
<body>
<form name="form" id="form" method="post" action="">
<div class="accordion">
	<h3>查询设置</h3>
	<div align="right">
	   <table class="search">
			<tr>
				<td>
				<input type="hidden" id="enableFlag" value="0" />
				<input type="hidden" id="flag" value="0" />
				<span id="showLog"></span>
				<span id="showRealTime"></span>
				<shiro:hasPermission name="realLog:print">
					<input type="button" id="enable" value="关闭打印日志" class="blue" onclick="enableControl()">
				</shiro:hasPermission>	
				<shiro:hasPermission name="realLog:enable">	    
					<input type="button" id="control" value="关闭实时监控" class="blue" onclick="swapControl()">
				</shiro:hasPermission>
				</td>			    	
				<td>
				<shiro:hasPermission name="realLog:clear">
					<input type="button" value="清空" class="blue" onclick="clearAll()">
				</shiro:hasPermission>
				</td>			    
					
			</tr>
		</table>
	</div>
</div>
</form>
    <div id="log-container" style="height: 100%; overflow-y: scroll; background: #333; color: #aaa; padding: 10px;">
        <div></div>
    </div>
</body>
<script>
	function enableControl(){
		var val = $("#enableFlag").val();//获取是否开启打印日志标识
		var url = "common/main/enable";
	    if(val==1){
	    	$("#showLog").text('打印日志开启').css('color','green');
	    	$("#enable").val("关闭打印日志");  
	    	$("#enableFlag").val(0);       	
	    }else{
	    	$("#showLog").text('打印日志关闭').css('color','red');
	    	$("#enable").val("开启打印日志");  
	    	$("#enableFlag").val(1);
	    }
	    $.ajax({
	        type: "POST",
	        url: url,
	        dataType: "json",
	        data: {"flag":val},
	        success: function(data) { 	        	
	        	layer.msg('操作打印日志成功');	        	
	        }
	    });
	}


	function swapControl(){
		var hidden_val = $("#flag").val();//获取隐藏的input的值
		var url = "common/main/startLog";
        if(hidden_val==0){
        	$("#showRealTime").text(' 实时监控关闭').css('color','red');
        	$("#control").val("启动实时监控");  
        	$("#flag").val(1);       	
        }else{
        	$("#showRealTime").text(' 实时监控开启').css('color','green');
        	$("#control").val("关闭实时监控");  
        	$("#flag").val(0);
        }
        $.ajax({
            type: "POST",
            url: url,
            dataType: "json",
            data: {"flag":hidden_val},
            complete:function(data) {            	
            	layer.msg('操作实时监控成功');
            }
        });
	}
	
	function clearAll(){
		$("#log-container div").empty();
	}
	
	function set(){	
		var url = "common/main/logRealTime";
		$.ajax({
            type: "POST",
            url: url,
            traditional: true,
            async: false,
            data: $("#form").serialize(),
    		dataType: "json",
            success: function(data) {  
            }
        });
		
	}

    $(document).ready(function() {
    	$(".accordion").each(function() {
    		$(this).accordion({
    	    	collapsible: true,
    	    	heightStyle: "content"
    	    });
    	});
    });
    var uid = "${uid}";	
   	var path = '<%=base%>'; 	
       // 指定websocket路径
       var websocket;
	if ('WebSocket' in window && uid!=null) {
		websocket = new ReconnectingWebSocket("ws://" + path + "/ws?uid="+uid);
	} else if ('MozWebSocket' in window && uid!=null) {
		websocket = new MozWebSocket("ws://" + path + "/ws"+uid);
	} else if(uid!=null){
		websocket = new SockJS("http://" + path + "/ws/sockjs"+uid);
	}
	if(uid!=null){
		websocket.onopen = function(event) {
			console.log("WebSocket:已连接");
			console.log(event);
		};
       websocket.onmessage = function(event) {
           // 接收服务端的实时日志并添加到HTML页面中
           $("#log-container div").append(event.data + "<p> </p>");
           // 滚动条滚动到最低部           
           $("#log-container").scrollTop($("#log-container div").height() - $("#log-container").height());            
       };
       websocket.onerror = function(event) {
			console.log("WebSocket:发生错误 ");
			console.log(event);
		};
		websocket.onclose = function(event) {
			console.log("WebSocket:已关闭");
			console.log(event);
		};   
	}
</script>
</body>
</html>
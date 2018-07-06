<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp"%>

<%
String base = request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>WebSocket</title>
</head>
<body onunload="closeSocket();">

<div class="ui-layout-north">
欢迎来到基于web-socket实现的聊天室!
<span style="float:right;">当前时间 ：<input id="time" disabled readonly="readonly"/></span> 
</div>
<div id="online" class="ui-layout-west"></div>
<div id="message" class="ui-layout-center"></div>
<div class="ui-layout-south">
	<input id="text"  size="100" onkeydown="keyDown(event);"/> 　
	<button class="blue" onclick="send()">发送</button><button onclick="closeSocket()">关闭</button>
	<button onclick="connect()">重连</button><button onclick="clearAll()">清屏</button>
</div>
</body>

<script>
	$(document).ready(function(){
		$("body").layout({
			resizerTip:"可调整大小",
			togglerTip_open:"关闭",
			togglerTip_closed:"打开"
			
		});
		$("#text").focus();
		connect();
	});

	var path = '<%=base%>'; 	
	// 指定websocket路径	
	var websocket = null;
	
	function keyDown(event)  {  
	    if(event.keyCode==13){  
	    	send();
	    	$("#text").focus();
	    }     
	}  
	
	function connect(){
		if(websocket!=null)
			closeSocket();
		if ('WebSocket' in window ) {
			websocket = new ReconnectingWebSocket("ws://" + path + "/websocket/chat");
		} else if ('MozWebSocket' in window ) {
			websocket = new MozWebSocket("ws://" + path + "/websocket/chat");
		} else {
			layer.msg('not support websocket');
		}
		
		websocket.onopen = function(event) {
			console.log("WebSocket:已连接");
			console.log(event);
			setConnected('');
			setMessageInnerHTML('open');
		};
	   	websocket.onmessage = function(event) {
	   		setMessageInnerHTML(event.data);
	   	};
	   	websocket.onerror = function(event) {
			console.log("WebSocket:发生错误 ");
			console.log(event);
			setMessageInnerHTML('error');
		};
		websocket.onclose = function(event) {
			//console.log("WebSocket:已关闭");
			console.log(event);
			setMessageInnerHTML('close');
		};   
		//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
		window.onbeforeunload = function(){
			websocket.close();
		}
	}
	
	function setMessageInnerHTML(message){
		var exp = /^(\d{4})\-(\d{2})\-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/ ; 
		if(exp.test(message)){
			$('#time').val(message);
		}else if(message.indexOf('login$')!=-1){
			//$("#online").text(message.substring(message.indexOf('$')+1)) ;
			$("#online").empty();
			document.getElementById('online').innerHTML += message.substring(message.indexOf('$')+1);
		}else{
			document.getElementById('message').innerHTML += message + '<br/>';
		}
	}
	
	function clearAll(){
		//document.getElementById('message').innerHTML = '';
		console.log('click clear');
		$("#message").empty();
	}
	
	function closeSocket(){
		if(websocket!=null){
			console.log("WebSocket:已关闭");
			websocket.close();
			websocket = null;
		}else{
			layer.alert('websocket已关闭');
		}
	}
	
	function send(){
		if(websocket==null){
			layer.alert('websocket已关闭');
			return;
		}
		
		var message = document.getElementById('text').value;
		if($.trim(message)!='' && $.trim(message)!=null){
			websocket.send(encodeURI(message.replace(/login/g,'_LOGIN')));
			$("#text").val('');
		}else{
			layer.alert('请输入聊天内容');
		}
	}
	
	function setConnected(status){
		$("#time").val(status);
	}
</script>
</html>
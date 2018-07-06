<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<html>
<head>	
    <title>文章管理</title>
    <style type="text/css">
	    a {
		cursor: pointer;
		color: blue;
		text-decoration: none;
		}
		a:link    {color:blue;}
		a:visited {color:blue;}
		a:hover   {color:#3174c7;}
		a:active  {color:#3174c7;}
		.ui-accordion .ui-accordion-content {	
			padding: 0.2em;		
			border-top: 0;
			overflow: auto;
		}
    </style>
    <script>
   	$(document).ready(function () {  
        $("#freshBtn").bind("click",function(){ window.location.reload(); });
        
   		$('body').layout({ 
       	applyDefaultStyles: true ,
           spacing_open:4,//边框的间隙  
           spacing_closed:4,//关闭时边框的间隙
           resizerTip:"可调整大小",//鼠标移到边框时，提示语  
           west__size:300,//pane的大小   
           resizerDragOpacity:0.1,//调整大小边框移动时的透明度
           //initClosed:true,//初始时，所有pane关闭           
       }); 
       
       $("#accordion").accordion({
    	  collapsible : true,
    	  heightStyle : "fill"
       });
       
       //初始化所有分类 - 树形结构      
       <c:forEach items="${bdList}" var="bd" varStatus="status">
       <c:if test="${bd.key != 'resource'}">
       	   var zNodes = new Array();
	       <c:forEach items="${categories}" var="dto">
	       	if("${bd.key}"=="${dto.fileType.key}"){
	  			zNodes.push({id : "${dto.category.id}" , pId : "${dto.category.parentid}" , name : "${dto.category.name}" , href : "${dto.category.url}"});
	       	}
	  		</c:forEach>
	  		
	  		resourceInit("tree_${bd.key}",zNodes);
	  		$("#expandAllBtn").bind("click", {type:"expandAll" , id:"tree_${bd.key}"}, expandNode);
	    	$("#collapseAllBtn").bind("click", {type:"collapseAll" , id:"tree_${bd.key}"}, expandNode);
	  	</c:if>
  	   </c:forEach>
       
       
   	});//初始化
   	
   	var resourceTree;
   	function resourceInit(treeId,category) {
   		var setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onNodeCreated: this.onNodeCreated,
				onClick: this.onItemClick
			}
		};
   		var zNodes = new Array();
   		zNodes.push({id:0,pId:0,name:'root',open:true});
   		for(var i=0;i<category.length;i++){
   			zNodes.push({id : category[i].id , pId : category[i].pId , name : category[i].name , href : category[i].href , open : true});
   		}
   		//console.log(zNodes);
   		resourceTree = $.fn.zTree.init($("#"+treeId), setting, zNodes);
   	}
   	
   	function expandNode(e) {
   		var id = e.data.id;
   		var zTree = $.fn.zTree.getZTreeObj(id),
   		type = e.data.type,
   		nodes = zTree.getSelectedNodes();
   		if (type.indexOf("All")<0 && nodes.length == 0) {
   			alert("请先选择一个父节点");
   		}

   		if (type == "expandAll") {
   			zTree.expandAll(true);
   		} else if (type == "collapseAll") {
   			zTree.expandAll(false);
   		} else {
   			var callbackFlag = $("#callbackTrigger").attr("checked");
   			for (var i=0, l=nodes.length; i<l; i++) {
   				zTree.setting.view.fontCss = {};
   				if (type == "expand") {
   					zTree.expandNode(nodes[i], true, null, null, callbackFlag);
   				} else if (type == "collapse") {
   					zTree.expandNode(nodes[i], false, null, null, callbackFlag);
   				} else if (type == "toggle") {
   					zTree.expandNode(nodes[i], null, null, null, callbackFlag);
   				} else if (type == "expandSon") {
   					zTree.expandNode(nodes[i], true, true, null, callbackFlag);
   				} else if (type == "collapseSon") {
   					zTree.expandNode(nodes[i], false, true, null, callbackFlag);
   				}
   			}
   		}
   	}
   	
   	function onItemClick(e, treeId, node) {		
   	    var target = node.href; // 找到节点中的href的值       
   	    if(target !=null && target !=''){	   	    	
   	    	try{  
   			    $.get(target,function(data){  		    	
   			        $("#iframeContent").html(data);   
   			    });      	
   	    	}catch(e){
   	    		console.log(e.name + ":" + e.message + ":" + e.description); 
   	    	}
   	    }
		}
    </script>
</head>
<body>
	<div class="ui-layout-west">
		<div style="background: transparent url(res/plugins/wdScrollTab/image/TabPanel/tab-content-bg.gif) repeat-x;
			border-bottom: 1px solid #8DB2E3;font-size: 12px;padding-top: 5px;padding-left: 10px;height: 22.5px;color: #23508E;">
			全部节点 -- [ <a id="expandAllBtn" href="#" title="点击后全部展开！！" onclick="return false;">展开</a> ]&nbsp;&nbsp;
			[ <a id="collapseAllBtn" href="#" title="点击后全部折叠！！" onclick="return false;">折叠</a> ]&nbsp;&nbsp;
			[ <a id="freshBtn" href="#" title="点击后菜单刷新！！" onclick="return false;">刷新</a> ]
		</div>
		<div id="accordion">
			<c:forEach items="${bdList}" var="bd" varStatus="status">
				<c:if test="${bd.key != 'resource'}">
					<h3>${bd.value }<input type="hidden" id="id" value="${bd.key }"/></h3>
					<div class="zTreeDemoBackground left">
						<ul id="tree_${bd.key}" class="ztree" style="overflow:auto;"></ul>
					</div>
				</c:if>
			</c:forEach>			
		</div>
	</div>
	<div class="ui-layout-center" id="iframeContent">		
	</div>
</body>
</html>
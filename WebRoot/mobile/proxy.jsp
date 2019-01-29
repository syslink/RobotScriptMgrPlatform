<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
		<title>机器人WEB客户端</title>
		<link rel="shortcut icon" href="<%=basePath%>/resource/img/favicon.ico"type="image/x-icon" />
		<link rel="stylesheet" href="<%=basePath%>/resource/bootstrap-3.3.6-dist/css/bootstrap.min.css" />
		<link charset="utf-8" rel="stylesheet" href="<%=basePath%>/resource/css/base-ui.css" />
		<script type="text/javascript" src="<%=basePath%>/resource/js/jquery-2.2.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/resource/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/resource/js/framework.js"></script>
		<script>
		function getCookie(name)
	 	{
		  var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
		  if(arr=document.cookie.match(reg))
		      return unescape(arr[2]);
		  else
		      return null;
	    }
	    var uid = getCookie("uid");
		function loadRobots()
		{
            $.post("<%=basePath%>/cgi/robot/list.api", {uid:uid,API_AUTH_KEY:'7215EE9C7D9DC229D2921A40E899EC5F'},
                    function(data){
		            	$("#subscriberId").empty();
		            	$("#proxyId").empty();
						var length = data.dataList.length;
						for(var i=0;i<length;i++){
						  $('#subscriberId').append("<option value='"+data.dataList[i].deviceId+"'>"+data.dataList[i].name+"</option>");
						  $('#proxyId').append("<option value='"+data.dataList[i].deviceId+"'>"+data.dataList[i].name+"</option>");
						}
              });
		}
		$(function () {
			loadRobots();
        });
		
		function setProxy(isEnable){
			var receiver = $('#subscriberId').val() + "," + $('#proxyId').val();
			var content = {};
			content.subscriber = $('#subscriberId').val();
			content.proxy = $('#proxyId').val();
			content.proxyType = $('#proxyType').val();
			content.enable = isEnable;
			$.post("http://rmp.cellbot.cn/cgi/message/send.api", 
					{action:'920',content:JSON.stringify(content),receiver:receiver,API_AUTH_KEY:'7215EE9C7D9DC229D2921A40E899EC5F'},
					  function(data){
				        
			          }
			);
		}
		
		function enableProxy(){
			setProxy(true);
		}
		
		function disableProxy(){
			setProxy(false);
		}
		</script>
	</head>
	<body style="text-align: center;">
    <iframe name = "wifiIframe" id = "wifiIframe" width="0px" height="0px"></iframe>
    <form action="http://rmp.cellbot.cn/cgi/message/send.api" target="wifiIframe" method="post" id="wifiForm">
    <input name="API_AUTH_KEY" value= "7215EE9C7D9DC229D2921A40E899EC5F" type="hidden"/>
    <input name="content" id= "content" type="hidden"/>
    <input name="receiver" id= "receiver" type="hidden"/>
    <input name="action" value="302" id= "action" type="hidden"/>
    <input name="format" id= "format" value="0" type="hidden"/>
    <div class="input-group" style="margin-top: 20px;width: 100%;">
		<span class="input-group-addon" >使用者</span>
		<select id="subscriberId"   class="form-control" style="display: inline; width: 100%;height:40px;">
		</select>
	</div>
	
    <div class="input-group" style="margin-top: 20px;width: 100%;">
		<span class="input-group-addon" >代理者</span>
		<select id="proxyId"   class="form-control" style="display: inline; width: 100%;height:40px;">
		</select>
	</div>
				 
	<div class="input-group" style="margin-top: 20px;width: 100%;">
      <span class="input-group-addon">代理部位</span>
      <select class="form-control" id="proxyType"   style="display: inline; width: 100%;height:40px;">
            <option value="Ear">耳朵</option>
            <option value="Mouth">嘴</option>
            <option value="Leg">腿</option>
            <option value="Brain">大脑</option>
            <option value="Arm">手</option>
            <option value="Eye">眼睛</option>
            <option value="CombineAction">组合动作</option>
      </select>
	</div>         
	
	<div class="panel-footer"  style="margin-top: 20px;">
	     <button type="button" class="btn btn-success btn-lg" onclick="enableProxy()"  style="width: 200px;">
	         <span class="glyphicon glyphicon-ok-circle" style="top:2px;"></span>开始代理
	     </button>
	     
	     <button type="button" class="btn btn-danger btn-lg" onclick="disableProxy()"  style="width: 200px;margin-top:40px;">
	         <span class="glyphicon glyphicon-ok-circle" style="top:2px;"></span>取消代理
	     </button>
	</div>
    </form>
    
    
	<div id="global_mask" style="display: none; position: absolute; top: 0px; left: 0px; z-index: 9999; background-color: rgb(20, 20, 20); opacity: 0.5; width: 100%; height: 100%; overflow: hidden; background-position: initial initial; background-repeat: initial initial;"></div>
	
	</body>
	
</html>

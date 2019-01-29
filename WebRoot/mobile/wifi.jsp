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
		            	$("#deviceId").empty();
						var length = data.dataList.length;
						for(var i=0;i<length;i++){
						  $('#deviceId').append("<option value='"+data.dataList[i].deviceId+"'>"+data.dataList[i].name+"</option>");
						}
              });
		}
		$(function () {
			loadRobots();
			$('#deviceId').change(function(){ 
				loadWifiList($('#deviceId').val());
			});
			loadWifiList($('#deviceId').val());
        });
		
		function changeWIFI()
		{
			 var wifi = {};
			 var ssid = $("#ssid").val().trim();
			 if(ssid==''){
				 return ;
			 }
			 wifi.ssid = ssid;
			 wifi.password = $("#password").val().trim();
			 $("#content").val(JSON.stringify(wifi));
			 $("#receiver").val($("#deviceId").val());
		     document.getElementById("wifiForm").submit();
		}
 
		
		function loadWifiList(deviceId )
		{
			showProcess('加载中......');
		    $.post("<%=basePath%>/cgi/wifi/list.api", {API_AUTH_KEY:'7215EE9C7D9DC229D2921A40E899EC5F',deviceId:deviceId},
			   function(data){
		    	hideProcess();
		    	$('#ssidList').empty();
		    	$("#ssid").val('');
		    	$("#ssidLabel").text("请选择WIFI");
		    	var length = data.dataList.length;
			    for(var i=0;i<length;i++){
			      var wifi = data.dataList[i];
				  $("#ssidList").append("<li><a style='cursor: pointer;padding: 8px 20px;' onclick=\"onSSIDSelected(this);\">"+wifi.ssid+"</a></li><li style='margin:0px;' role=\"separator\" class=\"divider\"></li>");
			    }
		    });
		 }
		
		function onSSIDSelected(item){
			$("#ssid").val($(item).text());
	    	$("#ssidLabel").text($(item).text());
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
		<span class="input-group-addon" >设备</span>
		<select id="deviceId"   class="form-control" style="display: inline; width: 100%;height:40px;">
		</select>
	</div>
				 
	<div class="input-group" style="margin-top: 20px;width: 100%;">
							      <span class="input-group-addon">SSID</span>
								  <input type="text" class="form-control cmd-base-attr" id="ssid" placeholder="SSID"
											maxlength="16" style="display: inline; width: 100%; height: 40px;" />
							       <div class="input-group-btn" style="height:40px;width: 40%;">
							       <button type="button"  style="height:40px;width: 100%;" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span id="ssidLabel">请选择WIFI</span> <span class="caret"></span></button>
							            <ul class="dropdown-menu dropdown-menu-right" style="width:100%;"id="ssidList"> </ul>
							      </div> 
	</div>
		                         
	<div class="input-group" style="margin-top: 20px;">
		<span class="input-group-addon">密码</span>
		<input type="text" class="form-control"  id="password"  maxlength="32" style="display: inline; width: 100%; height: 42px;"/>
	</div>
	
	<div class="panel-footer"  style="margin-top: 20px;">
						     <button type="button" class="btn btn-success btn-lg" onclick="changeWIFI()"  style="width: 200px;">
						      <span class="glyphicon glyphicon-ok-circle" style="top:2px;"></span>确定
						     </button>
	</div>
    </form>
    
    
	<div id="global_mask" style="display: none; position: absolute; top: 0px; left: 0px; z-index: 9999; background-color: rgb(20, 20, 20); opacity: 0.5; width: 100%; height: 100%; overflow: hidden; background-position: initial initial; background-repeat: initial initial;"></div>
	
	</body>
	
</html>

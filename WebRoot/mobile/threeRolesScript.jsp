<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/category.tld" prefix="category"%>
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
		<title>三人剧本执行</title>
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
		
		$(function () {
			loadRobots();
			loadScript();
			$('#Ttype').change(function(){ 
				loadScript();
			}) 
			$('#sid').change(function(){ 
				loadScriptRoles(($("#sid").val()));
			})
         });
		function loadRobots()
		{
            $.post("<%=basePath%>/cgi/robot/list.api", {uid:uid,API_AUTH_KEY:'7215EE9C7D9DC229D2921A40E899EC5F'},
                    function(data){
		            	$("#deviceId1").empty();
						var length = data.dataList.length;
						for(var i=0;i<length;i++){
						  $('#deviceId1').append("<option value='"+data.dataList[i].deviceId+"'>"+data.dataList[i].name+"</option>");
						  $('#deviceId2').append("<option value='"+data.dataList[i].deviceId+"'>"+data.dataList[i].name+"</option>");
						  $('#deviceId3').append("<option value='"+data.dataList[i].deviceId+"'>"+data.dataList[i].name+"</option>");
						}
              });
		}
		function  loadScriptRoles(sid){
			$.post("<%=basePath%>/cgi/script/rolelist.api", {sid:sid,API_AUTH_KEY:'7215EE9C7D9DC229D2921A40E899EC5F'},
					function(data){
			  	        hideProcess();
						var length = data.dataList.length;
						for(var i=0;i<length;i++){
						  $('#input-group'+i).find("span").text(data.dataList[i]);;
					    }
			});
			
		}
		function loadScript(){
			showProcess('加载中......');
			var type = $("#Ttype").val();
			$.post("<%=basePath%>/cgi/script/selectlist.api", {uid:uid,type:type,role:3,API_AUTH_KEY:'7215EE9C7D9DC229D2921A40E899EC5F'},
				 function(data){
					$("#sid").empty();
					var length = data.dataList.length;
					for(var i=0;i<length;i++){
					  $('#sid').append("<option value='"+data.dataList[i].sid+"'>"+data.dataList[i].title+"</option>");
					  if(i==0){
							loadScriptRoles(data.dataList[i].sid);
					  }
					}
					if(length==0){
						hideProcess();
					}
			});
		}
		function  doexecute(){
			if($('#sid').val()==null){
				return ;
			}
			showProcess('发送中......');
			var roles = "{\""+$('#input-group0').find("span").text()+"\":\""+$('#input-group0').find("select").val()
				        +"\",\""+$('#input-group1').find("span").text()+"\":\""+$('#input-group1').find("select").val()
				        +"\",\""+$('#input-group2').find("span").text()+"\":\""+$('#input-group2').find("select").val()+"\"}";
		    
			var receiver = $('#deviceId1').val() + ","+$('#deviceId2').val() + ","+$('#deviceId3').val();
			$.post("<%=basePath%>/cgi/script/rolesexecute.api", {sid:$('#sid').val(),roles:roles,receiver:receiver,API_AUTH_KEY:'7215EE9C7D9DC229D2921A40E899EC5F'},
					  function(data){
				        hideProcess();
			          }
			);
			
			
		}
		 
		function toogleState(action)
		{
			 $("#content").val(action);
			 $("#receiver").val($("#deviceId1").val()+","+$("#deviceId2").val()+","+$("#deviceId3").val());
		     document.getElementById("wifiForm").submit();
		}
		</script>
	</head>
	<body style="text-align: center;padding:20px;">

 
    <div class="alert alert-success" role="alert"> 
      <div class="input-group" id="input-group0" style="width: 100%">
				<span class="input-group-addon" >角色</span>
										
				<select id="deviceId1" class="form-control" style="display: inline; width: 100%;height:40px;">
				</select>
	  </div>
	  
	  <div class="input-group" id="input-group1" style="margin-top: 20px;width: 100%;">
				<span class="input-group-addon" >角色</span>
										
				<select  id="deviceId2" class="form-control" style="display: inline; width: 100%;height:40px;">
				 </select>
	  </div>
	  
	  <div class="input-group" id="input-group2" style="margin-top: 20px;width: 100%;">
				<span class="input-group-addon" >角色</span>
										
				<select  id="deviceId3" class="form-control" style="display: inline; width: 100%;height:40px;">
				 </select>
	  </div>
	  </div>
	   <div class="alert alert-info"> 
	   <div class="input-group" style="width: 100%;">
	   <span class="input-group-addon" >分类</span>
				  <select id="Ttype"   class="form-control" style="display: inline; width: 100%;height:40px;">
				    <category:options/>
				 </select>
	  </div>
	  
	
	 <div class="input-group" style="margin-top: 30px">
									<span class="input-group-addon" >剧本</span>
									<select  class="form-control" id="sid"    style="display: inline; width: 100%;height:40px;" >
									</select>
	 </div>
							 
	 </div>
								
	<div style="padding:5px 10px;text-align: center;margin-top: 50px;">
						     <button type="button" class="btn btn-success btn-lg" onclick="doexecute()"  style="width: 200px;">
						      <span class="glyphicon glyphicon-ok-circle" style="top:2px;"></span>执行
						     </button>
	</div>
	
	
	
	<div class="btn-group" data-toggle="buttons" style="text-align: center;margin-top: 50px;">
	  <label class="btn btn-primary btn-lg" onclick="toogleState(0)">
	    <input type="radio" autocomplete="off" />暂停
	  </label>
	  <label class="btn btn-primary btn-lg active" onclick="toogleState(1)">
	    <input type="radio"  autocomplete="off" />开始
	  </label>
	  <label class="btn btn-primary btn-lg" onclick="toogleState(-1)">
	    <input type="radio"  autocomplete="off" />取消
	  </label>
	</div>
	<p/>
	<div class="btn-group" style="margin-top: 40px;">
		<div style="padding:5px 10px;text-align: center;">
		     <a type="button" href="script.jsp" class="btn btn-success btn-lg" >
		                      单人剧本
		     </a>
		</div>
		
		<div style="padding:5px 10px;text-align: center;">
		     <a type="button" href="twoRolesScript.jsp" class="btn btn-success btn-lg" >
		                      双人剧本
		     </a>
		</div>
		
		<div style="padding:5px 10px;text-align: center;">
		     <a type="button" href="fourRolesScript.jsp" class="btn btn-success btn-lg" >
		                     四人剧本
		     </a>
		</div>
		
		<div style="padding:5px 10px;text-align: center;">
		     <a type="button" href="fiveRolesScript.jsp" class="btn btn-success btn-lg" >
		                     五人剧本
		     </a>
		</div>
	</div>
			
	<div style="padding:5px 10px;text-align: center;margin-top: 10px;">
	     <a type="button" href="controlRobot.jsp" class="btn btn-success btn-lg" >
	                      单机操控
	     </a>
	</div>
			
	<div id="global_mask" style="display: none; position: absolute; top: 0px; left: 0px; z-index: 9999; background-color: rgb(20, 20, 20); opacity: 0.5; width: 100%; height: 100%; overflow: hidden; background-position: initial initial; background-repeat: initial initial;"></div>
				<iframe name = "wifiIframe" id = "wifiIframe" width="0px" height="0px"></iframe>
	  <form action="http://rmp.cellbot.cn/cgi/message/send.api" target="wifiIframe" method="post" id="wifiForm">
	  <input name="API_AUTH_KEY" value= "7215EE9C7D9DC229D2921A40E899EC5F" type="hidden"/>
	  <input name="action" id= "action" value="902" type="hidden"/>
	  <input name="receiver" id= "receiver" type="hidden"/>
	   <input name="content" id= "content" type="hidden"/>
	 </form>			
	</body>
</html>

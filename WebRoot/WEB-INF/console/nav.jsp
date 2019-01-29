<%
	String navBasePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ request.getContextPath();  
%>
<%@ page language="java" pageEncoding="UTF-8"%>

<style>
 .btn-purple:hover,.btn-purple:active,.btn-purple:focus{
    background-color: #763DDB;
    background-image: none;
    border-color:#421198;
    color: white;
 }
 
  .btn-purple{
    background-color: #5F28C0;
    background-image: none;
    color: white;
    border-color:#421198;
 }
</style>
<div id="_main_nav" class="ui-vnav">
	<ul class="ui-nav-inner">
	
			<li  class="ui-item" id="scriptMenu">
				<a id="scriptMenuUrl" href="<%=navBasePath %>/console/script/list.action">
					<span class="glyphicon glyphicon-book" style="top:2px;"></span>&nbsp;<span class="ui-text">剧本管理</span>
				</a>
			</li>
<!-- 			<li  class="ui-item" id="userMenu"> -->
<%-- 				<a id="userMenuUrl" href="<%=navBasePath %>/console/user/list.action"> --%>
<!-- 					<span class="glyphicon glyphicon-user" style="top:2px;"></span>&nbsp;<span class="ui-text">用户管理</span> -->
<!-- 				</a> -->
<!-- 			</li> -->
			<li  class="ui-item" id="robotMenu">
				<a id="robotMenuUrl" href="<%=navBasePath %>/console/robot/list.action">
					<span class="glyphicon glyphicon-asterisk" style="top:2px;"></span>&nbsp;<span class="ui-text">所有机器人</span>
				</a>
			</li>
			<li  class="ui-item" id="sessionMenu">
				<a href="http://rmp.cellbot.cn/console/session/list.action">
					<span class="glyphicon glyphicon-signal" style="top:2px;"></span>&nbsp;<span class="ui-text">在线设备</span>
				</a>
			</li>
			<li  class="ui-item" id="exitMenu">
				<a href="<%=navBasePath %>/login.jsp">
					<span class="glyphicon glyphicon-log-out" style="top:2px;"></span>&nbsp;<span class="ui-text">退出系统</span>
				</a>
			</li>
	</ul>
	 
</div>


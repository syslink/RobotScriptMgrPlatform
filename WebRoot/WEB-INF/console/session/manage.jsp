<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/table.tld" prefix="table"%>
<%@ taglib uri="/WEB-INF/tld/function.tld" prefix="function"%>
<%
	String path = request.getContextPath();
// 	String basePath = request.getScheme() + "://"
// 	+ request.getServerName() + ":" + request.getServerPort()
// 	+ path;
	String basePath = "http://rmp.cellbot.cn/" + path;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<title>在线机器人</title>
		<%@include file="/resources.jsp"%>
		<script>
	 
		  
		  function showMessageDialog(account)
			{
			   $('#messageDialog').modal('show')
			   $('#Saccount').val(account);
			   
			}
		 function doSendMessage()
		  {
		    var message = $('#message').val();
		    var account = $('#Saccount').val();
		    var action = $('#Saction').val();
		    if($.trim(message)=='')
		    {
		       return;
		    }
		    showProcess('正在发送，请稍候......');
		    $.post("<%=basePath%>/cgi/message/send.api", {API_AUTH_KEY:'7215EE9C7D9DC229D2921A40E899EC5F',content:message,action:action,receiver:account,format:'0'},
			   function(data){
			      hideProcess();
			      if(data.code == 200){
			        showSTip("发送成功");
			        doHideDialog("messageDialog");
			      }
			      if(data.code == 404){
			        showETip("连接消息服务失败，请稍后再试");
			      }
		     });
		  }
		  
		  
		  function onImageError(obj)
			{
			    obj.src="<%=basePath%>/resource/img/icon_head_default.png";   
			}
			
          $(function () {
             $('[data-toggle="popover"]').popover();
          });
		</script>
	</head>
	<body class="web-app ui-selectable">


		<%@include file="../header.jsp"%>

		<%@include file="../nav.jsp"%>

		<div id="mainWrapper">

			<div class="lay-main-toolbar">
			</div>



			<div>
				<form action="<%=basePath%>/console/session/list.action" method="post"
					id="searchForm" style="padding: 0px;">
					<input type="hidden" name="currentPage" id="currentPage" value="1"/>
					<table style="width: 100%" class="utable">

						<thead>
							<tr class="tableHeader">
								 
								<th width="10%">
									设备号
								</th>
								<th width="8%">
									设备名称
								</th>
								<th width="10%">
									终端
								</th>
								<th width="6%">
									终端版本
								</th>
								<th width="6%">
									应用版本
								</th>
								<th width="6%">
									设备型号
								</th>
								<th width="8%">
									主机IP
								</th>
								<th width="10%">
									在线时长(秒)
								</th>
								<th width="26%">
									位置
								</th>
								<th width="10%">
									操作
								</th>
							</tr>
                            <tr>
									<td>
										<input name="deviceId" type="text" value="${cimsession.deviceId }"
											class="form-control"
											style="margin: 3px 0px;height: 32px;" />
									</td>
									
									<td>
										<input name="deviceName" type="text" value="${cimsession.deviceName }"
											class="form-control"
											style="margin: 3px 0px;height: 32px;" />
									</td>
									<td>
									</td>
									<td>
									</td>
									<td>
									<input name="clientVersion" type="text" value="${cimsession.clientVersion }"
											class="form-control"
											style="margin: 3px 0px;height: 32px;" />
									</td>
									<td>
									</td>
									<td>
									<input name="host" type="text" value="${cimsession.host }"
											class="form-control"
											style="margin: 3px 0px;height: 32px;" />
									</td>
									<td>
									</td>
									<td>
									</td>
									<td >
										<button type="submit" class="btn btn-primary btn-sm">
											<span class="glyphicon glyphicon-search"></span> 查询
										</button>
									</td>
								</tr>
						</thead>
						<tbody>
               
							<c:forEach var="cimsession" items="${page.dataList}">
							<tr style="height: 50px;">
							 
								<td>
									${cimsession.deviceId }
								</td>
								<td>
									${cimsession.deviceName }
								</td>
								<td>
									${cimsession.channel }
								</td>
								<td>
									${cimsession.systemVersion }
								</td>
								<td>
									${cimsession.clientVersion }
								</td>
								<td>
									${cimsession.deviceModel }
								</td>
								<td>
									${cimsession.host }
								</td>
								<td>
								 ${function:timeAgo(cimsession.bindTime)}
								</td>
								<td>
								  <div style="width:100%;height:50px;" tabindex="0" role="button"  data-toggle="popover" title="详情"
								   data-placement="left" 
								   data-content="longitude:${cimsession.longitude} 、 latitude:${cimsession.latitude} 、  geohash:${cimsession.geohash}">
								   ${cimsession.location }
								   </div>
								</td>
								<td>
									<div class="btn-group btn-group-xs">
										<button type="button" class="btn btn-primary" 
											style="padding: 5px;"
											onclick="showMessageDialog('${cimsession.deviceId }')">
											<span class="glyphicon glyphicon-send" style="top:2px;"></span> 发送消息
										</button>

									</div>
								</td>
							</tr>
							 </c:forEach>

						</tbody>
                          <tfoot>
							<tr>
									<td colspan="9">
										<table:page page="${page}"/>
									</td>
								</tr>
							
							</tfoot>
					</table>
				</form>

			</div>
		</div>

<div class="modal fade" id="messageDialog" tabindex="-1" role="dialog" data-backdrop="static"  >
		<div class="modal-dialog" style="width: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	                <h4 class="modal-title">发送消息</h4>
				</div>
				<div class="modal-body">
					<form role="form">
					<div class="input-group" style="margin-top: 20px;">
		        	  <span class="input-group-addon" style="padding: 6px 6px;">接收账号:</span>
					  <input type="text" class="form-control" value="system" id="Saccount" disabled="disabled" maxlength="32" style="display: inline; width: 100%; height: 42px;"/>
					</div>
					<div class="input-group" style="margin-top: 20px;">
		        	  <span class="input-group-addon" style="padding: 6px 6px;">消息类型:</span>
					  <select class="form-control" id="Saction" name="action" style="width: 100%; height: 40px;">
							<option value="100">文本消息</option>
							<option value="301">串口消息</option>
							<option value="200">动作消息</option>
					  </select>
					</div>
					<div class="input-group" style="margin-top: 20px;">
		        	  <span class="input-group-addon" style="padding: 6px 6px;">消息内容:</span>
                       <textarea rows="10" style="width: 100%; height: 200px;" id="message" name="message" class="form-control"></textarea>					
                    </div>	 
					</form>
				</div>
				<div class="modal-footer" style="padding: 5px 10px; text-align: center;">
					
					<button type="button" class="btn btn-success btn-lg" style="width: 200px;" onclick="doSendMessage()">
						<span class="glyphicon glyphicon-send" style="top:2px;"></span> 发送
					</button>
				</div>
			</div>
		</div>
</div>

		<script>
		       $('#sessionMenu').addClass('current');
		       
		</script>
	</body>
</html>

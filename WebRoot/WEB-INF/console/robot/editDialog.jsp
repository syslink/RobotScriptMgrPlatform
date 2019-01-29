	<%@ page language="java" pageEncoding="utf-8"%>
	<%
	String editBasePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
    %>
	<script type="text/javascript">
	var deviceId = '';
    function doModify()
	{
	    var name = $('#name').val();
	    if($.trim(name)=='')
	    {
	       return;
	    }
	    showProcess('正在保存，请稍候......');
	    $.post("<%=editBasePath%>/console/robot/update.action", {deviceId:deviceId,name:name},
		   function(data){
		      hideProcess();
		      if(data.code == 200)
		      {
		         showSTip("保存成功");
		         doHideDialog('EditDialog');
		         window.location.href=$('#searchForm').attr('action');
		      }
	     });
	    $.post("http://rmp.cellbot.cn/cgi/message/send.api", {API_AUTH_KEY:'7215EE9C7D9DC229D2921A40E899EC5F',content:name,action:"412",receiver:deviceId,format:'0'},
				   function(data){});
	}
		  
    function showEditDialog(deviceIdStr)
    {
        deviceId = deviceIdStr;
        doShowDialog('EditDialog');
    }
   </script>
   
   <div class="modal fade" id="EditDialog" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabele2">
	<div class="modal-dialog" style="width: 400px;" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabele2">
					修改名称
				</h4>
			</div>
			<div class="modal-body" style="padding:20px;">
	 
							   <div class="input-group" style="margin-top: 15px;width: 100%;">
								  <span class="input-group-addon" style="width: 50px;background: #fbeded">名称</span>
								  <input type="text" class="form-control" id="name"   
											maxlength="16" style="display: inline;  height: 40px;" />
								</div>
								
						</div>
						<div class="panel-footer" style="padding:5px 10px;text-align: center;">
						     <a type="button" class="btn btn-success btn-lg" onclick="doModify()"  style="width: 200px;"> 保 存</a>
						</div>
					</div>
					</div>
					</div>
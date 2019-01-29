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
		<title>小派WEB客户端</title>
		<link rel="shortcut icon" href="<%=basePath%>/resource/img/favicon.ico"type="image/x-icon" />
		<link rel="stylesheet" href="<%=basePath%>/resource/bootstrap-3.3.6-dist/css/bootstrap.min.css" />
		<link charset="utf-8" rel="stylesheet" href="<%=basePath%>/resource/css/base-ui.css" />
		<script type="text/javascript" src="<%=basePath%>/resource/js/jquery-2.2.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/resource/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/resource/js/framework.js"></script>
		<script>
		 function doAdd()
			{
				    var question = $('#question').val();
				    var answer = $('#answer').val();
				    if($.trim(question)==''||$.trim(answer)=='')
				    {
				       return;
				    }
				    showProcess('正在保存，请稍候......');
				    $.post("<%=basePath%>/console/yunsou/add.action", {question:question,answer:answer},
					   function(data){
					      hideProcess();
					      if(data.code == 200)
					      {
					         showSTip("保存成功");
					      }
				     });
			}
				  
 
		</script>
	</head>
	<body style="text-align: center;">

 
	<div class="modal-dialog" style="width:96%;margin-top: 10px;">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="myModalLabele2">
					添加云搜问答
				</h4>
			</div>
			<div class="modal-body">
	 
							<form role="form">
							    <div class="form-group" style="text-align: left;">
									<label for="Aaccount"  style="width: 50px;">
										<font color="red">*</font>问题:
									</label>
									<input type="text" class="form-control" id="question"
										maxlength="32" style="display: inline; width: 100%;height:40px;" />
								</div>
							 
								<div class="form-group"  style="text-align: left;">
									<label for="Aname"  style="width: 50px;">
										 <font color="red">*</font>答案:
									</label>
									<textarea rows="5" class="form-control" cols="30"
							               style="display: inline; width: 100%; height: 100px;" id="answer"></textarea>
								</div>
							</form>
						</div>
						<div class="panel-footer" style="padding:5px 10px;text-align: center;">
						     <button type="button" class="btn btn-success btn-lg" onclick="doAdd()"  style="width: 200px;">
						      <span class="glyphicon glyphicon-ok-circle" style="top:2px;"></span>保存
						     </button>
						</div>
					</div>
					</div>
					
					<div id="global_mask" style="display: none; position: absolute; top: 0px; left: 0px; z-index: 9999; background-color: rgb(20, 20, 20); opacity: 0.5; width: 100%; height: 100%; overflow: hidden; background-position: initial initial; background-repeat: initial initial;"></div>
					
	</body>
</html>

	<%@ page language="java" pageEncoding="utf-8"%>
	<%
	String addBasePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
    %>
	<script type="text/javascript">
    function doAdd()
	{
		    var question = $('#question').val();
		    var answer = $('#answer').val();
		    if($.trim(question)==''||$.trim(answer)=='')
		    {
		       return;
		    }
		    showProcess('正在保存，请稍候......');
		    $.post("<%=addBasePath%>/console/yunsou/add.action", {question:question,answer:answer},
			   function(data){
			      hideProcess();
			      if(data.code == 200)
			      {
			         showSTip("保存成功");
			         doHideDialog('AddDialog');
			      }
		     });
	}
		  
   
   </script>
   
   <div class="modal fade" id="AddDialog" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabele2">
	<div class="modal-dialog" style="width: 430px;" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabele2">
					添加云搜问答
				</h4>
			</div>
			<div class="modal-body">
	 
							<form role="form">
							    <div class="form-group">
									<label for="Aaccount"  style="width: 50px;">
										<font color="red">*</font>问题:
									</label>
									<input type="text" class="form-control" id="question"
										maxlength="32" style="display: inline; width: 320px;height:40px;" />
								</div>
							 
								<div class="form-group" style="margin-top: 50px;height: 100px;">
									<label for="Aname"  style="width: 50px;">
										 <font color="red">*</font>答案:
									</label>
									<textarea rows="5" class="form-control" cols="30"
							               style="display: inline; width: 320px; height: 100px;" id="answer"></textarea>
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
					</div>
	<%@ page language="java" pageEncoding="utf-8"%>
	<%
	String editBasePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
    %>
	<script type="text/javascript">
	var mSid;
    function doUpdate()
	{
		    var title = $('#Mtitle').val();
		    var type = $('#Mtype').val();
		    var open = $('#Mopen').val();
		    var description = $('#Mdesc').val();
		    if($.trim(title)=='')
		    {
		       return;
		    }
		    showProcess('正在保存，请稍候......');
		    $.post("<%=editBasePath%>/console/script/update.action", {title:title,open:open,type:type,description:description,sid:mSid},
			   function(data){
			      hideProcess();
			      if(data.code == 200)
			      {
			         showSTip("保存成功");
			         doHideDialog('EditDialog');
			         window.location.href=$('#searchForm').attr('action');
			      }
		     });
	}
		  
    function showEditDialog(sid)
    {
         showProcess('加载中，请稍候......');
		 $.post("<%=editBasePath%>/console/script/get.action", {sid:sid},
			   function(data){
			      hideProcess();
			      if(data.code == 200)
			      {
			           $('#Mtitle').val(data.data.title);
		               $('#Mtype').val(data.data.type);
		               $('#Mopen').val(data.data.open);
		               $('#Mdesc').val(data.data.description);
		               mSid = (sid);
		               doShowDialog('EditDialog');
			      }
			      
			     
		 });
    }
   </script>
   
   <div class="modal fade" id="EditDialog" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabele2">
	<div class="modal-dialog" style="width: 430px;" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabele2">
					修改剧本
				</h4>
			</div>
			<div class="modal-body">
	 
							<form role="form">
							    <div class="form-group">
									<label for="Aaccount"  style="width: 50px;">
										<font color="red">*</font>名称:
									</label>
									<input type="text" class="form-control" id="Mtitle"
										maxlength="32" style="display: inline; width: 320px;height:40px;" />
								</div>
							 
								<div class="form-group">
									<label for="Aaccount"  style="width: 50px;">
										<font color="red">*</font>分类:
									</label>
									<select name="type" class="form-control" id="Mtype" style="width: 320px;height:40px;display: inline;">
									 <category:options/>
									</select>
								</div>
								<div class="form-group">
									<label for="Aaccount"  style="width: 50px;">
										<font color="red">*</font>公开:
									</label>
									<select name="open" id="Mopen" class="form-control" style="width: 320px;height:40px;display: inline;">
									 <option value="1" >是</option>
									 <option value="0" >否</option>
									</select>
								</div>
								<div class="form-group">
									<label for="Aname"  style="width: 50px;">
										 说明:
									</label>
									<textarea rows="5" class="form-control" cols="30"
							               style="display: inline; width: 320px; height: 200px;" id="Mdesc"></textarea>
								</div>
							</form>
						</div>
						<div class="panel-footer" style="padding:5px 10px;text-align: center;">
						     <a type="button" class="btn btn-success btn-lg" onclick="doUpdate()"  style="width: 200px;"> 保 存</a>
						</div>
					</div>
					</div>
					</div>
	<%@ page language="java" pageEncoding="utf-8"%>
	<%
	String editBasePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
    %>
	<script type="text/javascript">
    function doModify()
	{
		    var name = $('#Mname').val();
		    var uid = $('#Muid').val();
		    var telephone = $('#Mtelephone').val();
		    var email = $('#Memail').val();
		    var gender = $('#Mgender').val();
		    if($.trim(name)=='')
		    {
		       return;
		    }
		    showProcess('正在保存，请稍候......');
		    $.post("<%=editBasePath%>/console/user/update.action", {gender:gender,uid:uid,name:name,telephone:telephone,email:email},
			   function(data){
			      hideProcess();
			      if(data.code == 200)
			      {
			         showSTip("保存成功");
			         doHideDialog('EditDialog');
			         window.location.href=$('#searchForm').attr('action');
			      }
			      
			      if(data.code == 404)
			      {
			         showETip("组织编号不存在");
			      }
		     });
	}
		  
    function showEditDialog(model)
    {
        $('#Muid').val(model.uid);
        $('#Maccount').val(model.account);
        $('#Memail').val(model.email);
        $('#Mname').val(model.name);
        $('#Mtelephone').val(model.telephone);
        $('#Mgender').val(model.gender);
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
					修改用户
				</h4>
			</div>
			<div class="modal-body" style="padding:20px;">
	 
							   <div class="input-group" style="width: 100%;">
								  <span class="input-group-addon" style="width: 50px;background: #fbeded">帐号</span>
								  <input type="text" class="form-control" id="Maccount"  disabled="disabled"
											maxlength="16" style="display: inline;height: 40px;" />
								  <input type="hidden" class="form-control" id="Muid"   />
								</div>
								
							   <div class="input-group" style="margin-top: 15px;width: 100%;">
								  <span class="input-group-addon" style="width: 50px;background: #fbeded">名称</span>
								  <input type="text" class="form-control" id="Mname"   
											maxlength="16" style="display: inline;  height: 40px;" />
								</div>
								
								<div class="input-group"  style="margin-top: 15px;width: 100%;">
								  <span class="input-group-addon" style="width: 50px;">性别</span>
								  <select name="gender" id="Mgender"  class="form-control"  style="height:40px;">
									 <option value="1" >男</option>
									 <option value="0" >女</option>
									</select>
									
								</div>
								
								<div class="input-group" style="margin-top: 15px;width: 100%;">
								  <span class="input-group-addon" style="width: 50px;">电话</span>
								  <input type="text" class="form-control" id="Mtelephone"   
											maxlength="16" style="display: inline;  height: 40px;" />
								</div>
								 
								<div class="input-group" style="margin-top: 15px;width: 100%;">
								  <span class="input-group-addon" style="width: 50px;">邮箱</span>
								  <input type="text" class="form-control" id="Memail"   
											maxlength="16" style="display: inline;  height: 40px;" />
								</div>
						</div>
						<div class="panel-footer" style="padding:5px 10px;text-align: center;">
						     <a type="button" class="btn btn-success btn-lg" onclick="doModify()"  style="width: 200px;"> 保 存</a>
						</div>
					</div>
					</div>
					</div>
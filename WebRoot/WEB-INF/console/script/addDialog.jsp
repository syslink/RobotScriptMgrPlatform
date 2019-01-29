	<%@ page language="java" pageEncoding="utf-8"%>
	<%
	String addBasePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
    %>
	<script type="text/javascript">
    function doAdd()
	{
		    var title = $('#Atitle').val();
		    var type = $('#Atype').val();
		    var open = $('#Aopen').val();
		    var description = $('#Adesc').val();
		    var uid = getCookie("uid");	
		    if($.trim(title)=='')
		    {
		       return;
		    }
		    showProcess('正在保存，请稍候......');
		    $.post("<%=addBasePath%>/console/script/add.action", {title:title,open:open,type:type,description:description,uid:uid},
			   function(data){
			      hideProcess();
			      if(data.code == 200)
			      {
			         showSTip("保存成功");
			         doHideDialog('AddDialog');
			         window.location.href=$('#searchForm').attr('action');
			      }
		     });
	}
    function getCookie(name)
    {
	  var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	  if(arr=document.cookie.match(reg))
	      return unescape(arr[2]);
	  else
	      return null;
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
					添加剧本
				</h4>
			</div>
			<div class="modal-body">
	 
							<form role="form">
							    <div class="form-group">
									<label for="Aaccount"  style="width: 50px;">
										<font color="red">*</font>名称:
									</label>
									<input type="text" class="form-control" id="Atitle"
										maxlength="32" style="display: inline; width: 320px;height:40px;" />
								</div>
							 
								<div class="form-group">
									<label for="Aaccount"  style="width: 50px;">
										<font color="red">*</font>分类:
									</label>
									<select name="type" id="Atype" style="width: 320px;height:40px;display: inline;" class="form-control" >
									 <category:options/>
									</select>
								</div>
								<div class="form-group">
									<label for="Aaccount"  style="width: 50px;">
										<font color="red">*</font>公开:
									</label>
									<select name="open" id="Aopen" style="width: 320px;height:40px;display: inline;" class="form-control" >
									 <option value="1" >是</option>
									 <option value="0" >否</option>
									</select>
								</div>
								<div class="form-group">
									<label for="Aname"  style="width: 50px;">
										 说明:
									</label>
									<textarea rows="5" class="form-control" cols="30"
							               style="display: inline; width: 320px; height: 200px;" id="Adesc"></textarea>
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
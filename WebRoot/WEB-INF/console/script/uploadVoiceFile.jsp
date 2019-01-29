	<%@ page language="java" pageEncoding="utf-8"%>
	<%
	String importBasePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
    %>
	<script type="text/javascript">
    function doImport()
	{
		    var xlsFile = $('#excelFile').val();
		    if($.trim(xlsFile)=='')
		    {
		       return;
		    }
		    if(!(xlsFile.lastIndexOf('.mp3')==xlsFile.length-4)
		    		&&!(xlsFile.lastIndexOf('.wav')==xlsFile.length-4)) 
			{
			   showETip("请上传mp3或者wav文件");
			   return  ;
			}
		    showProcess('正在上传，请稍候......');
		    document.getElementById('importForm').submit();
	}
		  
    function onUploadCallback(code,key)
    {
        hideProcess();
        if(code==200)
        {
           showSTip("上传成功");
           doHideDialog('UploadDialog');
           $('#file').val(key);
        }
        if(code==403)
        {
           showETip("请上传5M以内大小的文件");
        }
        if(code==500)
        {
           showSTip("上传失败，请重试");
        }
       
    }
    $(function () {
    	var date = new Date();
    	date.setDate(date.getDate() + 10);
    	document.cookie = "fileType=music" + ";expires=" + date.toGMTString();
    });

     $(document).ready(function(){ 
         $("#excelFile").change(function (){
           $("#filePath").val($(this).val());
         });
    });
   </script>
<div class="modal fade" id="UploadDialog" tabindex="-1" role="dialog" >
	<div class="modal-dialog" style="width: 480px;" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabele2">
					上传音频文件
				</h4>
			</div>
			<div class="modal-body">
							<form id="importForm" action="<%=importBasePath%>/console/file/upload.action" method="post" target="importFrame" enctype="multipart/form-data" >
								<div class="alert alert-success">
									<strong><span class="glyphicon glyphicon-info-sign" style="top:2px;" ></span></strong> 请上传mp3或者wav文件,5M以内
								</div> 
								<div style="margin: 50px auto;">
								    <input type="file" id="excelFile" name = "file" style="display:none;" />
									<input class="form-control" id="filePath" disabled="disabled" type="text" style="height:40px;width:100%;">  
									<button type="button" class="btn btn-primary"  style="padding: 5px;height: 40px;margin-top: -40px;float:right;" onclick="$('input[id=excelFile]').click();">
										<span class="glyphicon glyphicon-music" ></span> 选择文件
									</button>
								</div>
							</form>
						</div>
						<iframe width="0px" height="0px" id="importFrame" name = "importFrame" style="display: none;"> </iframe>
						<div class="panel-footer" style="padding:5px 10px;text-align: center;">
						     <a type="button" class="btn btn-success btn-lg" onclick="doImport()"  style="width: 200px;">上传</a>
						</div>
					</div>
					</div>
					</div>
					
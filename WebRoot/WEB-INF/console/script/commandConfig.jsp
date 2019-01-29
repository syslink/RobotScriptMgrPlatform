<%@ page language="java" pageEncoding="utf-8"%>
<%
        String cmdBasePath = request.getScheme() + "://"
                        + request.getServerName() + ":" + request.getServerPort()
                        + request.getContextPath();

%>
<script type="text/javascript">
    var cmdList;
    var CURRENT_SID;
    var CURRENT_CID = '';
    var attrDomain;
    var parentIdDic = new Array();
    function getCookie(name)
	  {
		  var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
		  if(arr=document.cookie.match(reg))
		      return unescape(arr[2]);
		  else
		      return null;
	  }
	var uid = getCookie("uid");
    function doSaveCommand()
        {
                var action = $('#Caction').val();
                    var role = $('#Crole').val();
                    var parent = $('#parent').val();
                    var delayTime = $('#delayTime').val();
                    var prepareTime = $('#prepareTime').val();
                    var content = {};
                    var vaild = true;

                    $(".cmd-base-attr").each(function(i,n){
                             if($.trim($(n).val())=='')
                             {
                               $(n).parent().fadeOut().fadeIn();
                               vaild = false ;
                               return false ;
                             }
                         });

                        if(!vaild)return;

                    $(attrDomain).find(".cmd-attr").each(function(i,n){
                             var obj = $(n);
                             if($.trim(obj.val())=='')
                             {
                               obj.parent().fadeOut().fadeIn();
                               vaild = false ;
                               return false ;
                             }
                             content[obj.attr("name")] = obj.val();
                         });


                    if(!vaild) return;

                    showProcess('正在保存，请稍候......');

                    var url = "<%=cmdBasePath%>/console/command/update.action";
                    if(CURRENT_CID==''){
                      url =  "<%=cmdBasePath%>/console/command/add.action"
                    }
                    $.post(url, {delayTime:delayTime,prepareTime:prepareTime,sid:CURRENT_SID,action:action,cid:CURRENT_CID,role:role,content:JSON.stringify(content),parent:parent},
                           function(data){
                              hideProcess();
                              if(data.code==200)
                              {
                                  showSTip("保存成功");
                                  var cmd = data.data;

                                  if(CURRENT_CID==''){
                                        $("#cmdCreateView").hide();
                                appendCommandToListView($('#commandList').children().length,cmd);
                              }else
                              {
                                $("#"+CURRENT_CID).attr("parent",cmd.parent);
                                $("#"+CURRENT_CID).attr("role",cmd.role);
                                $("#"+CURRENT_CID).attr("action",cmd.action);
                                $("#"+CURRENT_CID).attr("content",cmd.content);

                                $("#"+CURRENT_CID).attr("preparetime",cmd.prepareTime);
                                $("#"+CURRENT_CID).attr("delaytime",cmd.delayTime);

                                $("#"+CURRENT_CID).find(".role").text(cmd.role);
                                $("#"+CURRENT_CID).find(".action").text(getAttrName(cmd.action));
                                $("#"+CURRENT_CID).find(".prentIndex").text(getPrentIndex(cmd.sort));

                              }

                                  if($("#roleList").find("a:contains("+cmd.role+")").length==0){
                                        $("#roleList").append("<li><a style='cursor: pointer;padding: 8px 20px;' onclick=\"onRoleSelected(this);\">"+cmd.role+"</a></li><li style='margin:0px;' role=\"separator\" class=\"divider\"></li>");
                                          }
                              }
                     });
        }


    function  doDeleteMenu()
    {
            var account = $('#MenuAccount').val();
            var id =  $("#deleteMenuButton").attr("gid");
            var setting = {hint:"删除后无法恢复,确定删除这个菜单吗?",
                                    onConfirm:function(){
                                      $.post("<%=cmdBasePath%>/console/publicMenu/delete.action", {account:account,gid:id},
                                                          function(data){
                                                              showSTip("删除成功");
                                                      $('#'+id).fadeOut().fadeIn().fadeOut(function(){
                                                         $('#'+id).remove();
                                                      });
                                                      doHideConfirm();
                                                      $("#deleteMenuButton").fadeOut();
                                                      $("#menuCreateView").hide();
                                                      CURRENT_MENU_ID = null;
                                                      if($("#"+id).attr('type')=='0')
                                                      $("#childMenuPanel").hide();
                                                      });
                                    }};
                     doShowConfirm(setting);
    }

    function  onCommandChecked(cmd)
    {
                doShowCreateView();

                CURRENT_CID=$(cmd).attr("id");

                $("#roleName").text($(cmd).attr("role"));
                $('#Crole').val($(cmd).attr("role"));
                            $('#parent').val($(cmd).attr("parent"));
                            $('#Caction').val($(cmd).attr("action"));
                            $('#delayTime').val($(cmd).attr("delayTime"));
                            $('#prepareTime').val($(cmd).attr("prepareTime"));

                $(cmd).addClass("active");
                $(cmd).find('.delete-icon').show();
                getAttrDomain($(cmd).attr("action"));
                $('#cmdCreateView').find('.input-group').hide();
                    $('#cmdCreateView').find('.base-attr').show();
                    $('#cmdCreateView').find(attrDomain).show();

                var content = $.parseJSON($(cmd).attr("content"));

                $(attrDomain + ",.common-attr").find(".cmd-attr").each(function(i,n){
                               var obj = $(n);
                               obj.val(content[obj.attr("name")]);
                            });

                toogleTTSButton($(cmd).attr("action"));
    }

    function toogleTTSButton(action){

        if(action=='1' || action == '11'){
                $('#ttsPlayButton').show();
        }else
        {
                $('#ttsPlayButton').hide();
        }
    }

    function showCommandDialog(sid)
    {
        $("#roleList").empty();
        $("#cmdCreateView").hide();
        $("#parent").empty();
        $('#parent').append("<option value='0' >0</option>");
        $("#commandList").empty();
        CURRENT_SID = sid;
        showProcess('加载中，请稍候......');
        $.post("<%=cmdBasePath%>/console/command/list.action", {sid:sid},
                           function(data){
                           hideProcess();
                           cmdList = data.dataList;
                           parentIdDic = new Array();
                           parentIdDic["'" + 0 + "'"] = "0";
                           for(var i = 0;i<cmdList.length;i++)
                           {
                              var cmd = data.dataList[i];
                              appendCommandToListView(i,cmd);
                           }
                });

        doShowDialog('CommandDialog');
    }

    function appendCommandToListView(i,cmd){
            parentIdDic["'" + cmd.sort + "'"] = i;
        cmd.content = cmd.content.replace(/"/g, "&quot;").replace(/'/g, "&apos;")
        var line = "<div onclick='onCommandChecked(this);' title='" + cmd.content + "' class='list-group-item command_item' style='padding-right: 5px;cursor: pointer;border-radius: 0px;border-left: 0px;border-right: 0px;' prepareTime='"
                           + cmd.prepareTime+"' delayTime='"+cmd.delayTime+"' sort='"+cmd.sort+"' parent='"+cmd.parent+"'   content='"+cmd.content+"' id='"+cmd.cid+"'  role='"+cmd.role+"'   action='"+cmd.action+"'  ><button type='button'  onclick ='doDelete
Command(this)'   class='btn btn-danger btn-xs  delete-icon' style='position: absolute;left: 0px;top: -1px;height: 42px;width: 42px;border-radius: 0px;display:none;font-size:15px;'><span class='glyphicon glyphicon-trash' aria-hidden='true'></span> </button>"
                           + "<span class='role'>"+cmd.role+"</span>"
                           + "<span style='font-weight: normal;background-color: #4b379e;' class='action badge'>"+getAttrName(cmd.action)+"</span>"
                           + "<span class='badge'>" + (i+1) + "->" + (parentIdDic["'" + cmd.parent + "'"] + 1) + "</span>"
                           + "<span class='badge'>(p=" + cmd.prepareTime + ",d=" + cmd.delayTime + ")</span>"
                           + "</div>";
                $("#commandList").append(line);
                $('#parent').append("<option value=\""+cmd.sort+"\" >"+(i+1)+"</option>");
                $('#parent').val(cmd.parent);
                if($("#roleList").find("a:contains("+cmd.role+")").length==0){
                $("#roleList").append("<li><a style='cursor: pointer;padding: 8px 20px;' onclick=\"onRoleSelected(this);\">"+cmd.role+"</a></li><li style='margin:0px;' role=\"separator\" class=\"divider\"></li>");
                }
    }


    function doDeleteCommand(cmd){
     var cid = $(cmd).parent().attr("id");
     var sort = $(cmd).parent().attr("sort");
     var parent = $(cmd).parent().attr("parent");
     var setting = {hint:"删除后无法恢复,确定删除这个剧情吗?",
                                    onConfirm:function(){
                                      showProcess('正在删除，请稍候......');
                                      $.post("<%=cmdBasePath%>/console/command/delete.action", {cid:cid,sid:CURRENT_SID,sort:sort,parent:parent},
                                                          function(data){
                                                              hideProcess();
                                                              showSTip("删除成功");
                                                      $('#'+cid).fadeOut().fadeIn().fadeOut();
                                                      doHideConfirm();
                                                      $("#cmdCreateView").fadeOut();
                                                      CURRENT_CID='';
                                                      });

                                    }};
                     doShowConfirm(setting);
    }
    function doShowCreateView()
    {
         CURRENT_CID='';
         $(".command_item").removeClass("active");
             $(".command_item").find('.delete-icon').hide();
                 $('#cmdCreateView').find('.input-group').hide();
         $('#cmdCreateView').find('.base-attr').show();
         $("#cmdCreateView").fadeIn();
         document.getElementById("addCommandForm").reset();

         var cmd = $("#commandList").children("div:last-child");
         $('#parent').val(cmd.attr("sort"));
         $("#Crole").val(cmd.attr("role"));
         $("#roleName").text(cmd.attr("role"));
         $("#Caction").val(cmd.attr("action"));
         onActionChanged(cmd.attr("action"));

         autoSelectSpearer();
    }


    function autoSelectSpearer(){

         var role = $("#Crole").val();
         $('#commandList').children().each(function(i,n){
                    var item = $(n);
                    if(item.attr("action")=='1' && item.attr("role") == role )
                        {
                           var json = JSON.parse(item.attr("content"));
                           var speaker = json.speaker;
                           $("#speaker").val(speaker);
                           return ;
                        }
                 });
    }

    $(document).ready(function(){
            $("#Caction").change(function(){
                onActionChanged($(this).val());
            });

            $("#draggableDialog").draggable({
                    handle: ".modal-header",
                    refreshPositions: false
                });

    });

    function onActionChanged(action){
        $('#cmdCreateView').find('.input-group').hide();
            $('#cmdCreateView').find('.base-attr').show();
            $('#cmdCreateView').find(getAttrDomain(action)).show();
    }
    function getAttrDomain(value){
    	var intValue = parseInt(value);
    	switch(intValue)
    	{
    	case 1:
            attrDomain = ".speak-attr";
    		break;
    	case 2:
            attrDomain = ".move-attr";
    		break;
    	case 3:
            attrDomain = ".turn-attr";
    		break;
    	case 4:
            attrDomain = ".emotion-attr";
    		break;
    	case 5:
            attrDomain = ".hand-attr";
    		break;
    	case 6:
            attrDomain = ".qa-attr";
    		break;
    	case 7:
            attrDomain = ".sing-attr";
    		break;
    	case 8:
            attrDomain = ".around-attr";
    		break;
    	case 9:
            attrDomain = ".smart-qa-attr";
    		break;
    	case 10:
            attrDomain = ".entest-attr";
    		break;
    	case 11:
          	attrDomain = ".blend-speak-attr";
    		break;
    	case 12:
            attrDomain = ".voice-attr";
    		break;
    	case 13:
    	case 14:
    	case 15:
    	case 16:
    	case 17:
    	case 18:
    	case 19:
    	case 20:
    	case 21:
            attrDomain = ".smart-qa-attr";
    		break;
    	case 22:
            attrDomain = ".position-attr";
    		break;
    	case 23:
            attrDomain = ".head-attr";
    		break;
    	case 24:
            attrDomain = ".combineActions-attr";
    		break;
    	case 25:
            attrDomain = ".dance-attr";
    		break;
    	case 26:
            attrDomain = ".insert-script-attr";
    		break;
    	case 27:
    		attrDomain = ".control-led-attr";
    		break;
    	case 28:
    		attrDomain = ".control-motor-attr";
    		break;
    	}
        toogleTTSButton(value);

       return attrDomain;
    }

    function getAttrName(value){
    	var intValue = parseInt(value);
    	var attrName = "";
    	switch(intValue)
    	{
    	case 1:
            attrName = "说话";
    		break;
    	case 2:
            attrName = "移动";
    		break;
    	case 3:
            attrName = "转身";
    		break;
    	case 4:
            attrName = "表情";
    		break;
    	case 5:
            attrName = "手臂";
    		break;
    	case 6:
            attrName = "QA";
    		break;
    	case 7:
            attrName = "唱歌";
    		break;
    	case 8:
            attrName = "转圈";
    		break;
    	case 9:
            attrName = "QA";
    		break;
    	case 10:
            attrName = "评测";
    		break;
    	case 11:
          	attrName = "混读";
    		break;
    	case 12:
            attrName = "音频";
    		break;
    	case 13:
            attrName = "移动跟踪";
    		break;
    	case 14:
            attrName = "手势识别";
    		break;
    	case 15:
            attrName = "人机对话";
    		break;
    	case 16:
            attrName = "机机对话";
    		break;
    	case 17:
            attrName = "目标追踪";
    		break;
    	case 18:
            attrName = "动作复现";
    		break;
    	case 19:
            attrName = "取消动作复现";
    		break;
    	case 20:
            attrName = "开始对战";
    		break;
    	case 21:
            attrName = "结束对战";
    		break;
    	case 22:
            attrName = "走位";
    		break;
    	case 23:
            attrName = "转头";
    		break;
    	case 24:
            attrName = "组合动作";
    		break;
    	case 25:
            attrName = "舞蹈";
    		break;
    	case 26:
            attrName = "嵌入剧本";
    		break;
    	case 27:
    		attrName = "灯光控制";
    		break;
    	case 28:
    		attrName = "电机控制";
    		break;
    	}
    	return attrName;
    }
    function onRoleSelected(item){
        $("#Crole").val($(item).text());
        $("#roleName").text($(item).text());
        autoSelectSpearer();
    }


    function onTTSPlay(){
        var speaker = $("#speaker").val();
        var text = $("#text").val();
        var volume = $("#volume").val()/10;
        var speed = $("#speed").val()/10;
        var tone = $("#tone").val();
        document.getElementById("XunfeiBridge").doTTSPlay(-10,speaker,text,volume,speed,tone);
        $(".ttsPlayButton").attr('disabled',"disabled");
    }

    function getParentIndex(index){
         var parent = 0;
         $('#commandList').children().each(function(i,n){
                    var item = $(n);

                    if(item.attr("sort")==index)
                        {
                        return item.attr("sort");
                        }
                 });
         return parent;
    }

    function onAllTTSPlay(index){
         var item = $('#commandList').children().eq(index);
         var action = item.attr("action");
             if(action=='1' || action=='11')
             {
                var json = JSON.parse(item.attr("content"));
                var speaker = json.speaker;
                var text = json.text;
                var volume = (json.volume == undefined ? 50 : json.volume)/10;
                var speed = (json.speed == undefined ? 50 : json.speed)/10;
                var tone = (json.tone == undefined ? 50 : json.tone);
                document.getElementById("XunfeiBridge").doTTSPlay(index,speaker,text,volume,speed,tone);
                $(".ttsPlayButton").attr('disabled',"disabled");
             }else
             {
                 onAllTTSPlay(index+1);
             }

    }

    function  onTTSPlayEnd(index){
        $(".ttsPlayButton").removeAttr('disabled');
        if(index>=0){
                onAllTTSPlay(index+1);
        }
    }
    function loadRobots()
	{
        $.post("<%=cmdBasePath%>/cgi/robot/list.api", {uid:uid,API_AUTH_KEY:'7215EE9C7D9DC229D2921A40E899EC5F'},
                function(data){
	            	$("#deviceId1").empty();
					var length = data.dataList.length;
					for(var i=0;i<length;i++){
					  $('#deviceId1').append("<option value='"+data.dataList[i].deviceId+"'>"+data.dataList[i].name+"</option>");
					  $('#deviceId2').append("<option value='"+data.dataList[i].deviceId+"'>"+data.dataList[i].name+"</option>");
					  $('#deviceId3').append("<option value='"+data.dataList[i].deviceId+"'>"+data.dataList[i].name+"</option>");
					  $('#deviceId4').append("<option value='"+data.dataList[i].deviceId+"'>"+data.dataList[i].name+"</option>");
					  $('#deviceId5').append("<option value='"+data.dataList[i].deviceId+"'>"+data.dataList[i].name+"</option>");
					}
          });
	}
    function  loadScriptRoles(sid){
		$.post("<%=cmdBasePath%>/cgi/script/rolelist.api", {sid:sid,API_AUTH_KEY:'7215EE9C7D9DC229D2921A40E899EC5F'},
				function(data){
		  	        hideProcess();
		  	        var roles = '';
					var length = data.dataList.length;
					for(var i=0;i<length;i++){
					  $('#input-group'+i).find("span").text(data.dataList[i]);
					  roles += data.dataList[i] + ";";
				    }
					$('#roles').val(roles);
		});
		
	}
	function loadScript(){
		showProcess('加载中......');
		var type = $("#type").val();
		var number = $("#robotNumber").val();
		$.post("<%=cmdBasePath%>/cgi/script/selectlist.api", {uid:uid,type:type,role:number,API_AUTH_KEY:'7215EE9C7D9DC229D2921A40E899EC5F'},
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
	function changeRobotNumber()
	{
		var number = $("#robotNumber").val();
		var d1 = document.getElementById('input-group0');
		var d2 = document.getElementById('input-group1');
		var d3 = document.getElementById('input-group2');
		var d4 = document.getElementById('input-group3');
		var d5 = document.getElementById('input-group4');
		d1.setAttribute("class", "input-group"); 
		d2.setAttribute("class", "input-group"); 
		d3.setAttribute("class", "input-group"); 
		d4.setAttribute("class", "input-group"); 
		d5.setAttribute("class", "input-group"); 
		d1.style.display= 'none';
		d2.style.display= 'none';
		d3.style.display= 'none';
		d4.style.display= 'none';
		d5.style.display= 'none';
		if(number >= 1)
		{
			d1.setAttribute("class", "input-group insert-script-attr"); 
			d1.style.display = '';
		}
		if(number >= 2)
		{
			d2.setAttribute("class", "input-group insert-script-attr"); 
			d2.style.display = '';
		}
		if(number >= 3)
		{
			d3.setAttribute("class", "input-group insert-script-attr"); 
			d3.style.display = '';
		}
		if(number >= 4)
		{
			d4.setAttribute("class", "input-group insert-script-attr"); 
			d4.style.display = '';
		}
		if(number >= 5)
		{
			d5.setAttribute("class", "input-group insert-script-attr"); 
			d5.style.display = '';
		}
	}
    $(function () {
		loadRobots();
		loadScript();
		$('#robotNumber').change(function(){ 
			changeRobotNumber();
			loadScript();
		}) 
		$('#type').change(function(){ 
			loadScript();
		}) 
		$('#sid').change(function(){ 
			loadScriptRoles(($("#sid").val()));
		})
     });
   </script>


   <div class="modal fade" id="CommandDialog" tabindex="-1" role="dialog" data-backdrop="static" style="overflow-y:auto;">
        <div class="modal-dialog" id="draggableDialog" style="width: 900px;"  aria-hidden="true">
                <div class="modal-content">
                        <div class="modal-header" style="cursor: move;">
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                </button>
                                <h4 class="modal-title" id="feafaecacvvvv">
                                        剧情配置
                                </h4>
                        </div>
                        <div class="modal-body" style="text-align: center;padding: 0px;">


            <div  style="width: 300px;border-right: 1px solid #e7e7eb;height: 720px;float: left;">

                        <div style="overflow: auto;height: 670px;"  id="commandList"></div>
                        <div href="javascript:onActionBarClick(0);" style="text-align: center;width: 299px;border-top: 1px solid #ddd;height: 50px;border-bottom-left-radius: 6px;background-color: #f4f5f9;bottom: 0px;position: absolute;" id="bar_basic">
                                        <button  id="addRootMenuButton" class="btn btn-success" style="font-size: 12px;margin-top: 10px;" onclick="doShowCreateView()">
                                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加剧情
                                        </button>
                        </div>
                </div>
                 <div  style="width: 75%;height:720px;border-left: 0px;position: relative;margin-left: 300px;">


                                                <div style="text-align: center; padding-left: 80px;padding-top: 10px;display: none;" id="cmdCreateView">
                                        <form style="overflow-y:auto;width:85%;height:670px;" id="addCommandForm">
                                        <input type="hidden"  id="SID" />


                                        <div class="input-group base-attr">
                                                              <span class="input-group-addon" style="width: 68px;background: #fbeded">角色</span>
                                                                  <input type="text" class="form-control cmd-base-attr" id="Crole" placeholder="角色名字"
                                                                                        maxlength="16" style="display: inline; width: 250px; height: 40px;" />
                                                              <div class="input-group-btn" style="height:40px;width: 100px;">
                                                                <button type="button"  style="height:40px;width: 100px;" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span id="roleName">Action</sp
an> <span class="caret"></span></button>
                                                                    <ul class="dropdown-menu dropdown-menu-right" id="roleList">
                                                                </ul>
                                                              </div><!-- /btn-group -->
                                                            </div><!-- /input-group -->

                                                                <div class="input-group base-attr required"  style="margin-top: 15px">
                                                                  <span class="input-group-addon" style="width: 68px;background: #fbeded">上级</span>
                                                                  <select name="parent"  class="form-control cmd-base-attr" id="parent" style="width:350px; height: 40px;display: inline;">
                                                                  </select>
                                                                </div>
                                         <div class="input-group base-attr required" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;background:#fbeded">类型</span>
                                                                        <select name="action"  class="form-control cmd-base-attr" id="Caction"   style="width:350px; height: 40px;display: inline;">
                                                                         <option  />
                                                                         <option value="1"  >说话</option>
                                                                         <option value="11" >说话[中英混合]</option>
                                                                         <option value="24" >组合动作</option>
                                                                         <option value="23" >转头</option>
                                                                         <option value="2"  >移动</option>
                                                                         <option value="22" >走位</option>
                                                                         <option value="3"  >转身</option>
                                                                         <option value="4"  >表情</option>
                                                                         <option value="7"  >唱歌</option>
                                                                         <option value="8"  >转圈</option>
                                                                         <option value="25" >舞蹈</option>
                                                                         <option value="27" >灯光</option>
                                                                         <option value="28" >电机</option>
                                                                         <option value="9"  >智能响应</option>
                                                                         <option value="5"  >手臂动作</option>
                                                                         <option value="6"  >人机问答</option>
                                                                         <option value="26" >嵌入剧本</option>

                                                                         <option value="10" >英语评测</option>
                                                                         <option value="12" >播放音频</option>

                                                                         <option value="13" >移动物体跟踪</option>
                                                                         <option value="14" >手势识别</option>
                                                                         <option value="15" >人机语音交互</option>
                                                                         <option value="16" >机机语音交互</option>
                                                                         <option value="17" >智能追踪目标</option>
                                                                         <option value="18" >复现队长动作</option>
                                                                         <option value="19" >不再复现队长动作</option>

                                                                         <option value="20" >开始对战</option>
                                                                         <option value="21" >结束对战</option>

                                                                        </select>
                                                                </div>



                                                                <div class="input-group speak-attr qa-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;background: #fbeded;">发音人</span>
                                                                        <select name="speaker" id="speaker" class="form-control cmd-attr"  style="width:350px; height: 40px;display: inline;">
                                                                         <option value="aisbabyxu"  >许小宝[普通话/童声]</option>
                                                                         <option value="nannan"  >楠楠[普通话/童声]</option>
                                                                         <option value="xiaoyan"  >小燕[普通话/青年女声]</option>
                                                                         <option value="xiaoqi"  >小琪[普通话/青年女声]</option>
                                                                         <option value="aisjinger"  >小婧[普通话/青年女声]</option>
                                                                         <option value="aisxping"  >小萍[普通话/青年女声]</option>
                                                                         <option value="aisjiuxu"  >许久[普通话/青年男声]</option>
                                                                         <option value="xiaoyu"  >小宇[普通话/青年男声]</option>
                                                                         <option value="aisduoxu"  >许多[普通话/青年男声]</option>
                                                                         <option value="vils"  >老孙[普通话/中年男声]</option>
                                                                         <option value="aisxlin"  >晓琳[台湾普通话/青年女声]</option>
                                                                         <option value="xiaoqian"  >晓倩[东北话/青年女声]</option>
                                                                         <option value="aisxrong"  >晓蓉[四川话/青年女声]</option>
                                                                         <option value="xiaokun"  >小坤[河南话/青年男声]</option>
                                                                         <option value="aisxqiang"  >小强[湖南话/青年男声]</option>
                                                                         <option value="aisxying"  >小英[湖南话/青年男声]</option>
                                                                         <option value="xiaomei"  >晓美[粤语/青年女声]</option>
                                                                         <option value="dalong"  >大龙[粤语/青年男声]</option>
                                                                         <option value="donaldduck"  >唐老鸭[普通话/卡通]</option>
                                                                         <option value="xiaowanzi"  >小丸子[普通话/卡通]</option>

                                                                         <option value="aiscatherine"  >凯瑟琳[美式英语/青年女声]</option>
                                                                         <option value="vimary"  >玛丽[美式英语/青年女声]</option>
                                                                         <option value="henry"  >亨利[美式纯英文/青年男声]</option>
                                                                         <option value="aistom"  >Tom[美式纯英文/青年男声]</option>
                                                                         <option value="mariane"  >玛丽安[法语 /青年女声]</option>
                                                                         <option value="allabent"  >阿拉本[俄语 /青年女声]</option>
                                                                         <option value="gabriela"  >加芙列拉[西班牙语  /青年女声]</option>
                                                                         <option value="abha"  >艾伯哈[印地语 /青年女声]</option>
                                                                         <option value="xiaoyun"  >小云[越南语 /青年女声]</option>
                                                                        </select>
                                                                </div>

                                                                <div class="input-group entest-attr blend-speak-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;background: #fbeded;padding: 0px;">中文发音</span>
                                                                        <select name="speaker"  class="form-control cmd-attr"  style="width:350px; height: 40px;display: inline;">
                                                                         <option value="aisbabyxu"  >许小宝[普通话/童声]</option>
                                                                         <option value="nannan"  >楠楠[普通话/童声]</option>
                                                                         <option value="xiaoyan"  >小燕[普通话/青年女声]</option>
                                                                         <option value="xiaoqi"  >小琪[普通话/青年女声]</option>
                                                                         <option value="aisjinger"  >小婧[普通话/青年女声]</option>
                                                                         <option value="aisxping"  >小萍[普通话/青年女声]</option>
                                                                         <option value="aisjiuxu"  >许久[普通话/青年男声]</option>
                                                                         <option value="aisduoxu"  >许多[普通话/青年男声]</option>
                                                                         <option value="xiaoyu"  >小宇[普通话/青年男声]</option>
                                                                         <option value="vils"  >老孙[普通话/中年男声]</option>
                                                                         <option value="aisxlin"  >晓琳[台湾普通话/青年女声]</option>
                                                                         <option value="xiaoqian"  >晓倩[东北话/青年女声]</option>
                                                                         <option value="aisxrong"  >晓蓉[四川话/青年女声]</option>
                                                                         <option value="xiaokun"  >小坤[河南话/青年男声]</option>
                                                                         <option value="aisxqiang"  >小强[湖南话/青年男声]</option>
                                                                         <option value="aisxying"  >小英[湖南话/青年男声]</option>
                                                                         <option value="xiaomei"  >晓美[粤语/青年女声]</option>
                                                                         <option value="dalong"  >大龙[粤语/青年男声]</option>
                                                                         <option value="donaldduck"  >唐老鸭[普通话/卡通]</option>
                                                                         <option value="xiaowanzi"  >小丸子[普通话/卡通]</option>
                                                                        </select>
                                                                </div>

                                                                <div class="input-group entest-attr blend-speak-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;background: #fbeded;padding: 0px;">英语发音</span>
                                                                        <select name="enspeaker"  class="form-control cmd-attr"  style="width:350px; height: 40px;display: inline;">
                                                                         <option value="aiscatherine"  >凯瑟琳[美式英语/青年女声]</option>
                                                                         <option value="vimary"  >玛丽[美式英语/青年女声]</option>
                                                                         <option value="henry"  >亨利[美式纯英文/青年男声]</option>
                                                                         <option value="aistom"  >Tom[美式纯英文/青年男声]</option>
                                                                        </select>
                                                                </div>


                                                                <div class="input-group speak-attr sing-attr entest-attr blend-speak-attr" style="margin-top: 15px">
                                                                  <span class="input-group-addon" style="width: 68px;background: #fbeded;">文字</span>
                                                                  <textarea id="text" name = "text" rows="5" class="form-control cmd-attr" cols="30" placeholder="文字内容"
                                                               style="display: inline; width: 350px; height: 120px;" id="AversionDes"></textarea>
                                                                </div>

                                                                <div class="input-group voice-attr" style="margin-top: 15px">
                                                                  <span class="input-group-addon" style="width: 68px;padding: 0px;font-size: 12px;background: #fbeded;">音频文件</span>

                                                                  <button type="button" class="btn btn-primary"  style="border-top-left-radius: 0; border-bottom-left-radius: 0;padding: 5px;height: 40px;float:right;" onclick="doShowDialog('UploadDialog')">
                                                                                <span class="glyphicon glyphicon-music" ></span> 选择文件
                                                                 </button>
                                                                 <input type="text" disabled="disabled" id="file" class="form-control cmd-attr" name="file" style="font-size: 13px;display: inline; width: 264px; height: 40px;border-top-right-radius: 0;border-
bottom-right-radius: 0;" />
                                                                </div>

                                                                <div class="input-group speak-attr blend-speak-attr" style="margin-top: 15px">
                                                                  <span class="input-group-addon" style="width: 68px;">音量</span>
                                                                  <input type="range" id="volume" class="form-control cmd-attr" name="volume"  min="0" max="100" value="50"
                                                                                 style="display: inline; width: 350px; height: 40px;" />
                                                                </div>

                                                                <div class="input-group speak-attr blend-speak-attr" style="margin-top: 15px">
                                                                  <span class="input-group-addon" style="width: 68px;">语速</span>
                                                                  <input type="range" id="speed" class="form-control cmd-attr" name="speed"  min="0" max="100" value="50"
                                                                                         style="display: inline; width: 350px; height: 40px;" />
                                                                </div>

                                                                <div class="input-group speak-attr blend-speak-attr" style="margin-top: 15px">
                                                                  <span class="input-group-addon" style="width: 68px;">语调</span>
                                                                  <input type="range" id="tone" class="form-control cmd-attr" name="tone"  min="0" max="100" value="50"
                                                                                          style="display: inline; width: 350px; height: 40px;" />
                                                                </div>
                                                                <div class="input-group position-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;background: #fbeded;">目标名称</span>
                                                                        <input type="text" class="form-control cmd-attr" name="posName"
                                                                                        maxlength="30" style="display: inline; width: 350px; height: 40px;" />
                                                                </div>
                                                                <div class="input-group position-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;background: #fbeded;">目标位置</span>
                                                                        <input type="text" class="form-control cmd-attr" name="position" placeholder="X,Y,Angle"
                                                                                        maxlength="30" style="display: inline; width: 350px; height: 40px;" />
                                                                </div>

                                                                <!-- <div class="input-group move-attr" style="margin-top: 15px">
                                                                  <span class="input-group-addon" style="width: 68px;background: #fbeded;">目标Y</span>
                                                                  <input type="number" class="form-control cmd-attr" name="y" placeholder="目标位置的坐标Y"
                                                                                        maxlength="8" style="display: inline; width: 350px; height: 40px;" />
                                                                </div> -->

                                                                <div class="input-group turn-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;background: #fbeded;">方向</span>
                                                                        <select name="orientation"  class="form-control cmd-attr"  style="width:350px; height: 40px;display: inline;">
                                                                         <option value="left"  >左</option>
                                                                         <option value="right"  >右</option>
                                                                 </select>
                                                                </div>
                                                                <div class="input-group move-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;background: #fbeded;">方向</span>
                                                                        <select name="orientation"  class="form-control cmd-attr"  style="width:350px; height: 40px;display: inline;">
                                                                         <option value="forward"  >前进</option>
                                                                         <option value="backward"  >后退</option>
                                                                 </select>
                                                                </div>
                                                                <div class="input-group head-attr" style="margin-top: 15px">
                                                                  <span class="input-group-addon" style="width: 68px;background: #fbeded;">方向</span>
                                                                  <select name="orientation"  class="form-control cmd-attr"  style="width:350px; height: 40px;display: inline;">
                                                                       <option value="up">上</option>
                                                                       <option value="down">下</option>
                                                                       <option value="left">左</option>
                                                                       <option value="right">右</option>
                                                                  </select>
                                                                </div>
                                                                <div class="input-group speak-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;font-size: 12px;background: #fbeded;padding: 6px 0px;">头部运动</span>
                                                                    <input type="text" class="form-control cmd-attr" name="headTurnList" placeholder="u:50,1000,d:50,1000,r:30,1000,l:60"
                                                                                        maxlength="100" style="display: inline; width: 350px; height: 40px;" value="0"/>
                                                                </div>
                                                                <div class="input-group speak-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;font-size: 12px;background: #fbeded;padding: 6px 0px;">腿部运动</span>
                                                                    <input type="text" class="form-control cmd-attr" name="moveList" placeholder="f:50,1000,b:50,1000,r:30,1000,l:60"
                                                                                        maxlength="100" style="display: inline; width: 350px; height: 40px;" value="0"/>
                                                                </div>

                                                                <div class="input-group hand-attr speak-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;font-size: 12px;background: #fbeded;padding: 6px 0px;">左臂运动</span>
                                                                    <input type="text" class="form-control cmd-attr" name="leftAngle" placeholder="角度1,延迟时间..."
                                                                                        maxlength="100" style="display: inline; width: 350px; height: 40px;" value="0"/>
                                                                </div>

                                                                <div class="input-group hand-attr speak-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;font-size: 12px;background: #fbeded;padding: 6px 0px;">右臂运动</span>
                                                                    <input type="text" class="form-control cmd-attr" name="rightAngle" placeholder="角度1,延迟时间..."
                                                                                        maxlength="100" style="display: inline; width: 350px; height: 40px;" value="0"/>
                                                                </div>

                                                                <div class="input-group around-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;background: #fbeded;">方向</span>
                                                                        <select name="orientation"  class="form-control cmd-attr"  style="width:350px; height: 40px;display: inline;">
                                                                         <option value="right"  >顺时针</option>
                                                                         <option value="left"  >逆时针</option>
                                                                    </select>
                                                                </div>
                                                                
                                                                <div class="input-group speak-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;font-size: 12px;background: #fbeded;padding: 6px 0px;">组合动作延迟</span>
                                                                        <input type="text" class="form-control cmd-attr" name="combineActionsDelayTime" placeholder="单位ms"
                                                                                        maxlength="100" style="display: inline; width: 350px; height: 40px;" value="0"/>
                                                                </div>

                                                                <div class="input-group combineActions-attr speak-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;background: #fbeded;">组合动作</span>
                                                                        <select name="combineActionsName"  class="form-control cmd-attr"  style="width:350px; height: 40px;display: inline;">
                                                                         <option value="none">无-none</option>
                                                                         <option value="reset">复位-reset</option>
                                                                         <option value="bye">再见-bye</option>
                                                                         <option value="please">您请-please</option>
                                                                         <option value="handshake">握手-handshake</option>
                                                                         <option value="embrace">抱抱-embrace</option>
                                                                         <option value="salute">敬礼-salute</option>
                                                                         <option value="welcome">欢迎-welcome</option>
                                                                         <option value="rest">稍息-rest</option>
                                                                         <option value="thanks">谢谢-thanks</option>
                                                                         <option value="praise">赞-praise</option>
                                                                         <option value="kiss">飞吻-kiss</option>
                                                                         <option value="shy">害羞-shy</option>
                                                                         
                                                                         <option value="bored">无聊-bored</option>
                                                                         <option value="sigh">叹气-sigh</option>
                                                                         <option value="crywail">大哭-crywail</option>
                                                                         <option value="yangko">扭秧歌-yangko</option>
                                                                         <option value="dese">嘚瑟-dese</option>
                                                                         <option value="shake">好冷-shake</option>
                                                                         <option value="perfect">太棒了-perfect</option>
                                                                         <option value="defense">别打我-defense</option>
                                                                         <option value="quiet">请安静-quiet</option>
                                                                         <option value="sad">好伤心-sad</option>
                                                                         <option value="kungfu">功夫-kungfu</option>
                                                                         <option value="sneer">鄙视你-sneer</option>
                                                                         
                                                                         <option value="laugh">太逗了-laugh</option>
                                                                         <option value="swingarm">甩胳膊-swingarm</option>
                                                                         <option value="split">劈叉-split</option>
                                                                         <option value="dance">起舞-dance</option>
                                                                         <option value="rkick">踢右腿-rkick</option>
                                                                 	    </select>
                                                                </div>								
                                                                <div class="input-group combineActions-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;font-size: 12px;background: #fbeded;padding: 6px 0px;">额外信息（选填）</span>
                                                                        <input type="text" class="form-control cmd-attr" name="extraContent" placeholder="如控制灯光、电机需要在此输入控制信息"
                                                                                        maxlength="100" style="display: inline; width: 350px; height: 40px;" value="0"/>
                                                                </div>
								<div class="input-group dance-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;background: #fbeded;">舞蹈</span>
                                                                        <select name="danceName"  class="form-control cmd-attr"  style="width:350px; height: 40px;display: inline;">
                                                                         <option value="niu">大秧歌-niu</option>
                                                                         <option value="zwpnc">匹诺曹-zwpnc</option>
                                                                         <option value="apple">小苹果-apple</option>
                                                                         <option value="waka">瓦卡-waka</option>
                                                                         <option value="dangers">杰克逊-dangers</option>
                                                                         <option value="taijiq">太极拳-taijiq</option>
                                                                         <option value="balabala">巴拉巴拉-balabala</option>
                                                                         <option value="Uee">Uee-Uee</option>
                                                                         <option value="gansd">江南style-gansd</option>
                                                                         <option value="follow">追随-follow</option>
                                                                         <option value="TFBOYS">青春修炼手册-tfboys</option>
                                                                 	    </select>
                                                                </div>
																<div class="input-group dance-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;background: #fbeded;">音乐</span>
                                                                        <select name="music"  class="form-control cmd-attr"  style="width:350px; height: 40px;display: inline;">
                                                                         <option value="是">播放音乐</option>
                                                                         <option value="否">不播放音乐</option>
                                                                 	    </select>
                                                                </div>
                                                                <div class="input-group speak-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;font-size: 12px;background: #fbeded;padding: 6px 0px;">表情延迟</span>
                                                                        <input type="text" class="form-control cmd-attr" name="emotionDelayTime" placeholder="单位ms"
                                                                                        maxlength="100" style="display: inline; width: 350px; height: 40px;" value="0"/>
                                                                </div>

                                                                <div class="input-group emotion-attr speak-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;background: #fbeded;">表情</span>
                                                                        <select name="emotion"  class="form-control cmd-attr"  style="width:350px; height: 40px;display: inline;">
                                                                         <option value="none">常规</option>
                                                                         <option value="speaking">说话</option>
                                                                         <option value="blink">眨眼</option>
                                                                         <option value="smile">微笑</option>
                                                                         <option value="happy">开心</option>
                                                                         <option value="angry">愤怒</option>
                                                                         <option value="sad">悲伤</option>
                                                                         <option value="cry">哭泣</option>
                                                                         <option value="shy">害羞</option>
                                                                         <option value="heart">喜欢</option>
                                                                         <option value="heart_broken">心碎</option>
                                                                         <option value="dizzy">眩晕</option>
                                                                         <option value="music">听音乐</option>
                                                                 	    </select>
                                                                </div>

                                                                <div class="input-group speak-attr control-led-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;font-size: 12px;background: #fbeded;padding: 6px 0px;">灯光</span>
                                                                        <input type="text" class="form-control cmd-attr" name="ledController" placeholder="延迟时间1,编号1,时长1;延迟时间n,编号n,时长n;"
                                                                                        maxlength="100" style="display: inline; width: 350px; height: 40px;" value="0"/>
                                                                </div>

                                                                <div class="input-group speak-attr control-motor-attr" style="margin-top: 15px">
                                                                        <span class="input-group-addon" style="width: 68px;font-size: 12px;background: #fbeded;padding: 6px 0px;">电机</span>
                                                                        <input type="text" class="form-control cmd-attr" name="motorController" placeholder="延迟时间1,编号1,PWM1,时长1;延迟时间n,编号n,PWM2,时长n;"
                                                                                        maxlength="100" style="display: inline; width: 350px; height: 40px;" value="0"/>
                                                                </div>
                                                                
                                                                <div class="input-group  turn-attr emotion-attr around-attr move-attr head-attr" style="margin-top: 15px" >
                                                                  <span class="input-group-addon" style="width: 68px;background: #fbeded;">时长</span>
                                                                  <input type="number" class="form-control cmd-attr" name="duration" placeholder="持续时长(毫秒)"
                                                                                        maxlength="8" style="display: inline; width: 350px; height: 40px;"/>
                                                                </div>
                                                                <div class="input-group turn-attr around-attr head-attr" style="margin-top: 15px">
                                                                  <span class="input-group-addon" style="width: 68px;background: #fbeded;">角度</span>
                                                                  <input type="number" class="form-control cmd-attr" name="angle" placeholder="运动的角度"
                                                                                        maxlength="8" style="display: inline; width: 350px;height: 40px;" />
                                                                </div>


                                                                <div class="input-group move-attr" style="margin-top: 15px">
                                                                  <span  class="input-group-addon" style="width: 68px;background: #fbeded;padding: 6px 0px;">距离</span>
                                                                  <input type="number" class="form-control cmd-attr" name="distance" placeholder="移动的距离，单位厘米"
                                                                                 maxlength="8" style="display: inline; width: 350px;height: 40px;" />
                                                                </div>


                                                                <div class="input-group around-attr" style="margin-top: 15px">
                                                                  <span class="input-group-addon" style="width: 68px;background: #fbeded;">半径</span>
                                                                  <input type="number" class="form-control cmd-attr" name="radius" placeholder="半径(厘米)"
                                                                                        maxlength="8" style="display: inline; width: 350px; height: 40px;" />
                                                                </div>


                                                                <div class="input-group qa-attr" style="margin-top: 15px">
                                                                  <span class="input-group-addon" style="width: 68px;background: #fbeded;">问题</span>
                                                                  <input type="text" class="form-control cmd-attr" name="question" placeholder="问题描述"
                                                                                        maxlength="100" style="display: inline; width: 350px; height: 40px;" />
                                                                </div>

                                                                <div class="input-group qa-attr" style="margin-top: 15px">
                                                                  <span class="input-group-addon" style="width: 68px;background: #fbeded;">答案</span>
                                                                  <input type="text" class="form-control cmd-attr" name="criterion" placeholder="标准答案"
                                                                                        maxlength="100" style="display: inline; width: 350px; height: 40px;" />
                                                                </div>

                                                                <div class="input-group qa-attr" style="margin-top: 15px">
                                                                  <span class="input-group-addon" style="width: 68px;background: #fbeded;padding: 6px 0px;">回答次数</span>
                                                                  <input type="number" class="form-control cmd-attr" name="count" placeholder="最多回答的次数"
                                                                                        maxlength="8" style="display: inline; width: 350px; height: 40px;" />
                                                                </div>

                                                                <div class="input-group qa-attr" style="margin-top: 15px">
                                                                  <span class="input-group-addon" style="width: 68px;background: #fbeded;padding: 6px 0px;">错误提示</span>
                                                                  <input type="text" class="form-control cmd-attr" name="errorhint" placeholder="回答错误的提示"
                                                                                        maxlength="100" style="display: inline; width: 350px; height: 40px;" />
                                                                </div>
                                                                <div class="input-group qa-attr" style="margin-top: 15px" >
                                                                  <span class="input-group-addon" style="width: 68px;background: #fbeded;padding: 6px 0px;">失败提示</span>
                                                                  <input type="text" class="form-control cmd-attr" name="failtips" placeholder="最终没有回答正确的提示"
                                                                                        maxlength="100" style="display: inline; width: 350px; height: 40px;" />
                                                                </div>
                                                                <div class="input-group qa-attr" style="margin-top: 15px">
                                                                  <span class="input-group-addon" style="width: 68px;background: #fbeded;padding: 6px 0px;">正确提示</span>
                                                                  <input type="text" class="form-control cmd-attr" name="righttips" placeholder="回答正确的提示"
                                                                                       maxlength="100" style="display: inline; width: 350px; height: 40px;" />
                                                                </div>

                                                                <div class="input-group insert-script-attr" style="margin-top: 15px">
                                                                    <span class="input-group-addon" style="width: 68px;background: #fbeded;">机器人数</span>
                                                                    <select id="robotNumber" name="robotNumber"  class="form-control cmd-attr"  style="width:350px; height: 40px;display: inline;">
                                                                        <option value="1">1</option>
                                                                        <option value="2">2</option>
                                                                        <option value="3">3</option>
                                                                        <option value="4">4</option>
                                                                        <option value="5" selected = "selected">5</option>
                                                                    </select>
                                                                </div>
                                                                <div class="input-group insert-script-attr" style="margin-top: 15px">
                                                                    <span class="input-group-addon" style="width: 68px;background: #fbeded;">类别</span>
                                                                    <select id="type" name="type" class="form-control cmd-attr" style="display:inline; width:350px;height:40px;">
																	    <category:options/>
																	</select>
                                                                </div>
                                                                <div class="input-group insert-script-attr" style="margin-top: 15px">
                                                                    <span class="input-group-addon" style="width: 68px;background: #fbeded;">剧本</span>
                                                                    <select id="sid" name="sid" class="form-control cmd-attr" style="display:inline; width:350px;height:40px;">
																	</select>
                                                                </div>
                                                                <div class="input-group insert-script-attr" style="margin-top: 15px">
                                                                    <span class="input-group-addon" style="width: 68px;background: #fbeded;">所有角色名</span>
                                                                    <input id="roles" type="text" class="form-control cmd-attr" name="roles" readonly="true"
                                                                                        maxlength="100" style="display: inline; width: 330px; height: 40px;" />
                                                                </div>
                                                                <div id="input-group0" class="input-group insert-script-attr" style="margin-top: 15px">
                                                                    <span class="input-group-addon" style="width: 68px;background: #fbeded;">角色</span>
                                                                    <select id="deviceId1" name="deviceId1" class="form-control cmd-attr" style="display:inline; width:350px;height:40px;">
																	</select>
                                                                </div>
                                                                <div id="input-group1" class="input-group insert-script-attr" style="margin-top: 15px">
                                                                    <span class="input-group-addon" style="width: 68px;background: #fbeded;">角色</span>
                                                                    <select id="deviceId2" name="deviceId2"  class="form-control cmd-attr" style="display:inline; width:350px;height:40px;">
																	</select>
                                                                </div>
                                                                
                                                                <div id="input-group2" class="input-group insert-script-attr" style="margin-top: 15px">
                                                                    <span class="input-group-addon" style="width: 68px;background: #fbeded;">角色</span>
                                                                    <select id="deviceId3" name="deviceId3"  class="form-control cmd-attr" style="display:inline; width:350px;height:40px;">
																	</select>
                                                                </div>
                                                                
                                                                <div id="input-group3" class="input-group insert-script-attr" style="margin-top: 15px">
                                                                    <span class="input-group-addon" style="width: 68px;background: #fbeded;">角色</span>
                                                                    <select id="deviceId4" name="deviceId4"  class="form-control cmd-attr" style="display:inline; width:350px;height:40px;">
																	</select>
                                                                </div>
                                                                
                                                                <div id="input-group4" class="input-group insert-script-attr" style="margin-top: 15px">
                                                                    <span class="input-group-addon" style="width: 68px;background: #fbeded;">角色</span>
                                                                    <select id="deviceId5" name="deviceId5"  class="form-control cmd-attr" style="display:inline; width:350px;height:40px;">
																	</select>
                                                                </div>
                                                                
                                                                <div class="input-group base-attr" style="margin-top: 15px" >
                                                                  <span data-placement="top" data-content="开始这一个剧本之前的等待时间，单位毫秒(1000毫秒=1秒)" style="width: 68px;padding: 0px;font-size: 12px;" tabindex="0" class="input-group-addon" role="b
utton" data-toggle="popover" data-trigger="focus" title="说明" ><span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span>准备时间</span>
                                                                  <input type="number" class="form-control cmd-attr" id="prepareTime" name="prepareTime" placeholder="开始执行前准备时间(毫秒)，默认 0"
                                                                                        maxlength="8" style="display: inline; width: 350px; height: 40px;" value= "0" />
                                                                </div>


                                                                <div class="input-group base-attr" style="margin-top: 15px" >
                                                                  <span data-placement="top" data-content="开始这一个剧本之后通知开始下一个剧本的时间，(默认)0:执行完毕之后，-1 ：刚执行时，其他正数：(*)毫秒之后，单位毫秒(1000毫秒=1秒)" style="width: 68px;pad
ding: 0px;font-size: 12px;" tabindex="0" class="input-group-addon" role="button" data-toggle="popover" data-trigger="focus" title="说明" ><span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span>调度时间</span>
                                                                  <input type="number" class="form-control cmd-attr" id="delayTime"  name="delayTime" placeholder="开始执行后调度下一个剧本的时间(毫秒)，默认 0"
                                                                                        maxlength="8" style="display: inline; width: 350px; height: 40px;" value= "0" />
                                                                </div>

                                                </form>


                                                <div  style="text-align: center;width:598px;border-top: 1px solid #ddd;height: 50px;border-bottom-right-radius: 6px;background-color: #f4f5f9;bottom: 0px;left: 0px;position: absolute;">
                                        <button  id="addRootMenuButton" class="btn btn-success" style="margin-top: 7px; " onclick="doSaveCommand()">
                                                <span class="glyphicon glyphicon-ok-circle" style="top:2px;"></span>保存剧情
                                        </button>

                                        <div class="btn-group btn-group-xl" style="margin-top: 7px;right: 30px;top: 0px;position: absolute; ">
                                                <button   class="btn btn-danger ttsPlayButton" onclick="onTTSPlay()">
                                                        <span class="glyphicon glyphicon-headphones" style="top:2px;"></span>单句试听
                                                </button>
                                                <button   class="btn btn-danger ttsPlayButton" onclick="onAllTTSPlay(0)">
                                                        <span class="glyphicon glyphicon-headphones" style="top:2px;"></span>整体试听
                                                </button>
                                        </div>
                        </div>
                        </div>
                        </div>
                </div>

        </div>
        </div>
</div>
<object type="application/x-shockwave-flash" data="<%=cmdBasePath%>/plugin/XunfeiBridge.swf" id="XunfeiBridge"  width="0px" height="0px">
                                <param name="quality" value="high"/>
                                <param name="allowScriptAccess" value="always"/>
                                <param name="wmode" value="transparent"/>
                                <param name="movie" value="<%=cmdBasePath%>/plugin/XunfeiBridge.swf"/>
</object>

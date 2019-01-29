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
                <title>机器人管理</title>
                <link rel="shortcut icon" href="<%=basePath%>/resource/img/favicon.ico"type="image/x-icon" />
                <link rel="stylesheet" href="<%=basePath%>/resource/bootstrap-3.3.6-dist/css/bootstrap.min.css" />
                <link charset="utf-8" rel="stylesheet" href="<%=basePath%>/resource/css/base-ui.css" />
                <script type="text/javascript" src="<%=basePath%>/resource/js/jquery-2.2.3.min.js"></script>
                <script type="text/javascript" src="<%=basePath%>/resource/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
                <script type="text/javascript" src="<%=basePath%>/resource/js/framework.js"></script>
                <style>
                    * {
                                -webkit-touch-callout:none;
                                -webkit-user-select:none;
                                -khtml-user-select:none;
                                -moz-user-select:none;
                                -ms-user-select:none;
                                -user-select:none;
                        }
                        input {
              -webkit-user-select:auto; /*webkit浏览器*/
}
                </style>

                <script>
            var onMouseDown = false;

                function getCookie(name)
                {
                  var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
                  if(arr=document.cookie.match(reg))
                      return unescape(arr[2]);
                  else
                      return null;
            }
            var uid = getCookie("uid");
            function loadSentences()
            {
                var sentences = getCookie("sentences");
                if(sentences != null && sentences != '')
                {
                        var sentenceList = sentences.split('#');
                        for(i = 0; i < sentenceList.length; i++)
                        {
                            if(sentenceList[i] != "")
                                $('.list-group').prepend("<li onclick='onitemclick(this)' class='list-group-item'>" + sentenceList[i] + "</li>");
                        }
                }
          }
                function loadRobots()
                {
            $.post("<%=basePath%>/cgi/robot/list.api", {uid:uid,API_AUTH_KEY:'7215EE9C7D9DC229D2921A40E899EC5F'},
                    function(data){
                                $("#Tdevid").empty();
                                                var length = data.dataList.length;
                                                for(var i=0;i<length;i++){
                                                  $('#Tdevid').append("<option value='"+data.dataList[i].deviceId+"'>"+data.dataList[i].name+"</option>");
                                                }
              });
                }
                $(function () {
                        loadSentences();
                        loadRobots();
                        $('.opt-button').on('touchstart',function(e) {

                                onMouseDown = true;
                                var button = $(this);
                                 var content = {};
                                 content.action = button.attr("action");
                                 content.orientation = button.attr("orientation");
                                 content.duration = button.attr("duration");
                                 if(button.attr("distance")!=''){
                                         content.distance = parseInt(button.attr("distance"));
                                 }
                                 if(button.attr("angle")!=''){
                                         content.angle = parseInt(button.attr("angle"));
                                 }
                                 if(button.attr("radius")!=''){
                                         content.radius = parseInt(button.attr("radius"));
                                 }


                                 var action = button.attr("msgAction");
                                 $.post("http://rmp.cellbot.cn/cgi/message/send.api", {API_AUTH_KEY:'7215EE9C7D9DC229D2921A40E899EC5F',content:JSON.stringify(content),action:action,receiver:$('#Tdevid').val(),format:'0'},
                                         function(data){
                                     }
                                 );


                        });

                        $('.opt-button').on('touchend',function(e) {
                                onMouseDown = false;
                                showSTip("已经取消");

                                var button = $(this);
                                callstop(button);
                        });

                        $("#leftHand").change(function(){
 

                        var content = {};
                                content.action = '1016';
                                content.leftangle = parseInt($(this).val());
                                content.leftduration = 5000;
                                content.rightduration = 5000;
                                content.rightangle = -1000;
                                sendMessage(701,JSON.stringify(content),this);
                    });
                        $("#rightHand").change(function(){
                                var content = {};
                                content.action = '1016';
                                content.rightangle = parseInt($(this).val());
                                content.leftduration = 5000;
                                content.rightduration = 5000;
                                content.leftangle = -1000;
                                sendMessage(701,JSON.stringify(content),this);
                    });

                        $('#Tdevid').change(function(){
                                var date = new Date();
                date.setDate(date.getDate() + 10);
                document.cookie="robotDeviceId1=" + $('#Tdevid').val() + ";expires=" + date.toGMTString();
                        })
                        var robotDeviceId = getCookie("robotDeviceId1");
                        if(robotDeviceId != null)
                                $("#Tdevid option[value=" + robotDeviceId + "]").attr("selected",true);
         });






                function forward(button)
                {
                         var content = {};
                         content.action = '1001';
                         content.orientation = 'forward';
                         content.duration = 5000;
                         content.distance = 100;
                         sendMessage(700,JSON.stringify(content),button);
                }

                function callstop(button)
                {
                         var content = {};
                         content.action = '1001';
                         content.orientation = 'stop';
                         sendMessage(700, JSON.stringify(content),button);
                }

                function gotoback(button)
                {
                         var content = {};
 
                         content.action = '1001';
                         content.orientation = 'backward';
                         content.duration = 5000;
                         content.distance = 100;
                         sendMessage(700,JSON.stringify(content),button);
                }

                function gotoleft(button)
                {
                         var content = {};
                         content.action = '1002';
                         content.orientation = 'left';
                         content.duration = 1000;
                         content.angle = 100;
                         sendMessage(700,JSON.stringify(content),button);
                }
                function gotoright(button)
                {
                         var content = {};
                         content.action = '1002';
                         content.orientation = 'right';
                         content.duration = 1000;
                         content.angle = 100;
                         sendMessage(700,JSON.stringify(content),button);
                }


                function  speakText(){
                         var text = $('#text').val().trim();
                         var actionName = $('#action').val();
                         var extraContent = $('#extraContent').val();
                         if(text=='' && actionName == 'none'){
                                 return ;
                         }
						 if(text != '')
					     {
	                         if($("#ttscheckbox").prop('checked'))
	                         {
	                                 sendMessage(303,text);

	                         }else
	                         {
	                                 sendMessage(100,text);
	                         }
						 }
						 var delayTime = 0;
						 if(actionName != 'none')
					     {
							 var timeStr = $('#delayTime').val().trim();
							 if(timeStr != null && timeStr != '')
							 {
								 delayTime = parseInt(timeStr);
								 if(delayTime > 0)
									 setTimeout("exeAction(" + actionName + "," + extraContent + ")", delayTime);
								 else
									 exeAction(actionName, extraContent);
							 }
							 
						 }
						 var storedText = $('#text').val() + ";" + actionName + ";" + delayTime + ";" + extraContent;
                         $('.list-group').prepend("<li onclick='onitemclick(this)' class='list-group-item'>" + storedText + "</li>");
                         var storedSentence = getCookie("sentences");
                         document.cookie="sentences=" + escape((storedSentence != null ? storedSentence : "") + storedText + "#");
                         $('#text').val("");
                }
				function exeAction(actionName, extraContent){
					 var content = {};
					 content.combineActionsName = actionName;
					 content.extraContent = extraContent;
					 sendMessage(703, JSON.stringify(content));
				}
                function onitemclick(obj){
//                       $('#text').val($(obj).text());
//                       $(obj).remove();
                         if($('#text').val() == '删除')
                         {
                                 var sentences = getCookie("sentences");
                                 sentences = sentences.replace($(obj).text() + "#", "");
                                 document.cookie="sentences=" + escape(sentences);
                                 $(obj).remove();
                         }
                         var textInfo = $(obj).text().split(';');
                         
                         if($("#ttscheckbox").prop('checked'))
                     	 {
                                 sendMessage(303, textInfo[0]);

                         }
                         else
                         {
                                 sendMessage(100, textInfo[0]);
                         }
                         var actionName = textInfo[1];
                         var delayTime = parseInt(textInfo[2]);
                         var extraContent = textInfo[3];
                         if(actionName != 'none')
					     {
								 if(delayTime > 0)
									 setTimeout("exeAction('" + actionName + "','" + extraContent + "')", delayTime);
								 else
									 exeAction(actionName, extraContent);
						 }
                }



                function sendMessage(action,content,button)
                {
                    $(button).attr('disabled',"true");

                    $.post("http://rmp.cellbot.cn/cgi/message/send.api", 
                    	   {API_AUTH_KEY:'7215EE9C7D9DC229D2921A40E899EC5F',content:content,action:action,receiver:$('#Tdevid').val(),format:'0'},
                           function(data){
                          $(button).removeAttr("disabled");
                    });
                 }

                </script>
        </head>
        <body style="text-align: center;">



       <div class="input-group" style="margin: 20px;">
                  <span class="input-group-addon" >设备</span>

                  <select id="Tdevid"   class="form-control" style="display: inline; width: 100%;height:40px;">
                  </select>
       </div>


        <div style="padding-left: 30px;padding-right: 30px;">

                  <button action="1003" orientation="left"   msgAction = "700" radius= "25" angle = "720" type="button" duration="10000" class="btn btn-info btn-lg opt-button"  style="float:left;padding: 20px 3px;border-radius: 99px;"  >
                                  <span class="glyphicon glyphicon-arrow-up" style="top:2px;"></span> 左转
                  </button>

                  <button action="1001" orientation="forward" duration = "100000" distance = "2500" msgAction = "700" angle = "" type="button" class="btn btn-success btn-lg opt-button"  style="padding: 24px 8px;border-radius: 99px;"  >
                                  <span class="glyphicon glyphicon-arrow-up" style="top:2px;"></span> 前进
                  </button>

                  <button action="1003" orientation="right" msgAction = "700" radius= "25" angle = "720" duration="10000" type="button" class="btn btn-info btn-lg opt-button"  style="float:right;padding: 20px 3px;border-radius: 99px;"  >
                                  <span class="glyphicon glyphicon-arrow-up" style="top:2px;"></span> 右转
                  </button>
        </div>
        <div style="padding-left: 30px;padding-right: 30px;margin-top: 10px;">

                <button action="1002" orientation="left" duration = "100000" distance = "" msgAction = "700" angle = "1800" type="button" class="btn btn-success btn-lg opt-button"  style="float:left;padding: 24px 8px;border-radius: 99px;" >
                                        <span class="glyphicon glyphicon-arrow-left" style="top:2px;"></span> 向左
                </button>

                <button  type="button" class="btn btn-danger btn-lg"  style="padding: 20px 4px;border-radius: 99px;" onclick="callstop(this)">
                                        <span class="glyphicon glyphicon-remove-circle" style="top:2px;"></span> 停止
                </button>

                <button action="1002" orientation="right" duration = "100000" distance = "" msgAction = "700" angle = "1800" type="button" class="btn btn-success btn-lg opt-button"  style="float:right;padding: 24px 8px;border-radius: 99px;" >
                                        <span class="glyphicon glyphicon-arrow-right" style="top:2px;"></span> 向右
                </button>
        </div>
        <div style="margin-top: 10px;">

                <button action="1001" orientation="backward" duration = "100000" distance = "2500" msgAction = "700" angle = "" type="button" class="btn btn-success btn-lg opt-button"  style="padding: 24px 8px;border-radius: 99px;" onclick="gotoback(this)">
                                        <span class="glyphicon glyphicon-arrow-down" style="top:2px;"></span> 后退
                </button>
        </div>


        <div class="input-group" style="margin-top: 15px;width: 50%;float: left;padding-right: 5px;">
                <span class="input-group-addon" style="PADDING: 0 5px;">左手</span>
                <input type="range" id="leftHand" class="form-control"  min="-45" max="180" value="0"
                        style="display: inline;   height: 40px;" />
        </div>

    <div class="input-group" style="margin-top: 15px;width: 50%;padding-left: 5px;">
                <input type="range" id="rightHand" class="form-control"  min="-45" max="180" value="0"
                        style="display: inline;  height: 40px;" />
                <span class="input-group-addon" style="PADDING: 0 5px;">右手</span>

        </div>
                 <div class="btn-group" style="margin-top: 40px;">

                   <p/>
                   <a type="button" target="_blank" href="script.jsp" class="btn btn-info btn-lg" >
                                        单剧
                   </a>
                   <a type="button" target="_blank" href="twoRolesScript.jsp" class="btn btn-info btn-lg" >
                                        双剧
                   </a>
                   <a type="button" target="_blank" href="threeRolesScript.jsp" class="btn btn-info btn-lg" >
                                        三剧
                   </a>
                   <a type="button" target="_blank" href="fourRolesScript.jsp" class="btn btn-info btn-lg" >
                                        四剧
                   </a>
                   <a type="button" target="_blank" href="fiveRolesScript.jsp" class="btn btn-info btn-lg" >
                                        五剧
                   </a>
                   <p/>
                   <button type="button"  onclick="sendMessage(305,'',this)" class="btn btn-warning btn-lg" >
                                        闭嘴
                   </button>

                   <button type="button" onclick="sendMessage(307,'',this)" class="btn btn-danger btn-lg" >
                                        重启
                   </button>
                   <button type="button"   onclick="sendMessage(306,'',this)" class="btn btn-danger btn-lg" >
                                        关机
                   </button>
                   <a type="button"   target="_blank" href="wifi.jsp" class="btn btn-success btn-lg"  >
               Wifi
                   </a>
                   <a type="button"   target="_blank" href="proxy.jsp" class="btn btn-success btn-lg"  >
                                        代理
                   </a>
                   <a type="button"   target="_blank" href="motor.jsp" class="btn btn-success btn-lg"  >
                                        电机控制
                   </a>
                   <a type="button"   target="_blank" href="led.jsp" class="btn btn-success btn-lg"  >
                                        灯光控制
                   </a>
                </div>

      <div class="input-group" style="margin: 20px;">
      <span class="input-group-addon">
        <input type="checkbox" id="ttscheckbox" />
      </span>
      <input type="text" id="text" style="height: 50px;-webkit-user-select:auto;" class="form-control" placeholder="文字内容,选中则直接讲话"/>
      <span class="input-group-btn">
        <button class="btn btn-default" style="height: 50px;width: 80px;" type="button" onclick="speakText()">发送</button>
      </span>
    </div>
	<div class="input-group" style="margin: 20px;">
          <span class="input-group-addon" >配合动作</span>
          <select id="action"   class="form-control" style="display: inline; width: 100%;height:50px;">
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
                                  
                 <option value="motor">电机控制-motor</option>
                 <option value="led">灯光控制-led</option>
                 <option value="motor_led">电机灯光混合控制-motor_led</option>
          </select>
          <input type="text" id="delayTime" style="height: 50px;-webkit-user-select:auto;" class="form-control" placeholder="延迟时间(ms)"/>
          <input type="text" id="extraContent" style="height: 50px;-webkit-user-select:auto;" class="form-control" placeholder="附加内容(可选填)"/>
    </div>

     <ul class="list-group" style="margin-top: 20px;">
	 </ul>
        <div id="global_mask" style="display: none; position: absolute; top: 0px; left: 0px; z-index: 9999; background-color: rgb(20, 20, 20); opacity: 0.5; width: 100%; height: 100%; overflow: hidden; background-position: initial initial; background-repeat: initial initial;"></div>

        </body>
</html>
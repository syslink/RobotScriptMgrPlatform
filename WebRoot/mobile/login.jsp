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
<title>机器人管理系统</title>

<%@include file="../resources.jsp"%>
<style type="text/css">

.login-wrapper {
    background: #e1e4e7 url(<%=basePath%>/resource/img/pattern.png) repeat;
    background: url(<%=basePath%>/resource/img/pattern.png) repeat,linear-gradient(#e1e4e7,#f3f4f5);
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    z-index: 100;
}

</style>
<script>
$(function () {
  $('[data-toggle="popover"]').popover();
});
function doLogin()
{
            var account = $('#account').val();
            var password = $('#password').val();
            if($.trim(account)=='' || $.trim(password)=='')
            {
               return;
            }

            showProcess('正在登录请稍候......');
            $.post("<%=basePath%>/console/user/login.action", {account:account,password:password},
                   function(data){
                      hideProcess();
                      if(data.code == 404)
                      {
                         showETip("用户不存在");
                         return ;
                      }
                      if(data.code == 403)
                      {
                         showETip("账号或者密码错误");
                         return ;
                      }
                      var date = new Date();
                      date.setDate(date.getDate() + 10);
                      document.cookie="uid=" + data.data + ";expires=" + date.toGMTString();
                      doHideDialog('LoginDialog');
                      window.location.href="<%=basePath%>/mobile/controlRobot.jsp";
             });
}
</script>
</head>
<body class="login-wrapper">
        <div class="modal-dialog" style="width: 360px;">
                <div class="modal-content">
                        <div class="modal-header" style="text-align: center;color:#ffffff;border:0px;height: 150px;background: linear-gradient(to right,rgba(11,32,58,1),rgba(11,32,58,.85) 50%,rgba(11,32,58,.6));font-family: 楷体;">
                        <h2 style="margin: 40px;">管理员登录</h2>
                        </div>
                        <div class="modal-body" style="padding:25px;">



                        <div class="input-group" style="margin-top: 10px;">
                          <span class="input-group-addon"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></span>
                                  <input type="text" class="form-control" id="account" maxlength="32" placeholder="管理员帐号" value="system"
                                        style="display: inline; width: 100%; height: 50px;" />
                                </div>

                                <div class="input-group" style="margin-top: 30px;margin-bottom: 10px;">
                                  <span class="input-group-addon"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></span>
                                  <input type="password" class="form-control" id="password" placeholder="密码" value=""
                                        maxlength="32" style="display: inline; width: 100%; height: 50px;" />
                                </div>


                </div>
                <div class="modal-footer" style="text-align: center;">
                        <a type="button" class="btn btn-success btn-lg" onclick="doLogin()"
                                style="width: 320px;">登录</a>
                </div>
</div>
</div>
<script>
doShowDialog('LoginDialog');
</script>
</body>


</html>
                         
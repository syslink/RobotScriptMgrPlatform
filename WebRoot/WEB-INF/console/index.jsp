<%@ page language="java"   pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<title>内容管理系统</title>
        <%@include file="/resources.jsp"%>
	 	<script type="text/javascript" src="<%=resourceBasePath%>/resource/js/jquery-ui.min.js"></script>
		<script>		  
		  function getCookie(name)
		  {
			  var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
			  if(arr=document.cookie.match(reg))
			      return unescape(arr[2]);
			  else
			      return null;
		  }
		  var uid = getCookie("uid");
	      $(function(){
	    	  if(uid != '10000')
	 	   	     $("#userMenu").hide();
	      });
		</script>
	</head>
	<body class="web-app ui-selectable">


           <%@include file="header.jsp"%>

           <%@include file="nav.jsp"%>

			<div id="mainWrapper" style="padding-top: 25px;" >
				  
			</div>
			 <script>
		      $('#indexMenu').addClass('current');
		      </script>
	</body>
	
           
</html>

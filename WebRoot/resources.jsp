<%
	String resourceBasePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
%>
<link rel="shortcut icon" href="<%=resourceBasePath%>/resource/img/favicon.ico"type="image/x-icon" />
<link rel="stylesheet" href="<%=resourceBasePath%>/resource/bootstrap-3.3.6-dist/css/bootstrap.min.css" />
<link charset="utf-8" rel="stylesheet" href="<%=resourceBasePath%>/resource/css/base-ui.css" />
<script type="text/javascript" src="<%=resourceBasePath%>/resource/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="<%=resourceBasePath%>/resource/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=resourceBasePath%>/resource/js/framework.js"></script>
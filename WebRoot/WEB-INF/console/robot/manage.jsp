<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/table.tld" prefix="table"%>
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
		<title>所有机器人</title>
	    <%@include file="/resources.jsp"%>
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
	    	  {
	 	   	     $("#usrid").hide();
	    	     $("#usrid").val(uid);
	    	  }
	      });
		</script>
	</head>
	<body class="web-app ui-selectable">


		<%@include file="../header.jsp"%>

		<%@include file="../nav.jsp"%>

		<div id="mainWrapper">
		<div class="lay-main-toolbar"></div>

		 
				<div>
					<form action="<%=basePath%>/console/robot/list.action" method="post"
						id="searchForm" style="padding: 0px;">
						<input type="hidden" name="currentPage" id="currentPage" value="1" />
						<table width="100%" class="utable">

							<thead>
								<tr class="tableHeader">
								    <th width="10%">设备号</th>
									<th width="15%">SN</th>
									<th width="10%">名称</th>
									<th width="10%">所属用户</th>
									<th width="20%">操作</th>
								</tr>
							 	<tr>
									<td>
										<input name="deviceId" value="${robot.deviceId }" type="number"
											class="form-control"
											style="margin: 3px 0px;height: 32px;" />
									</td>
                                     <td>
									<input name="sn" value="${robot.sn }" type="text"
											class="form-control"
											style="margin: 3px 0px;height: 32px;" />
									</td>
									<td>
										<input name="name" type="text" value="${robot.name }"
											class="form-control"
											style="margin: 3px 0px;height: 32px;"/>
									</td>
								 
									 
									<td>
									<input id="usrid" name="uid" value="${robot.uid }" type="text"
											class="form-control"
											style="margin: 3px 0px;height: 32px;" />
									</td>
									 
									<td >
										<button type="submit" class="btn btn-primary btn-sm">
											<span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查询
										</button>
									</td>
								</tr>
							 
							</thead>
							<tbody id="checkPlanList">

								<c:forEach var="robot" items="${page.dataList}">
									<tr id="${robot.deviceId}"  style=" height: 50px;">
									 
									 <td>
											${robot.deviceId }
										</td>
										<td>
											${robot.sn }
										</td>
										<td>
											${robot.name }
										</td>
										 
									 <td>
											${robot.uid }
										</td>
										<td>
										
										  <div class="btn-group btn-group-xs">
											  <button type="button" class="btn btn-primary"  style="padding: 5px;" onclick="showEditDialog('${robot.deviceId}')">
											   <span class="glyphicon glyphicon-edit" aria-hidden="true"></span> 修改
											  </button>
									      </div>
										</td>
									</tr>
								</c:forEach>
								 
							</tbody>
							<tfoot>
							<tr>
									<td colspan="5">
										<table:page page="${page}"/> 
									</td>
								</tr>
							
							</tfoot>
						</table>
					</form>

				</div>
			</div>
<%@include file="editDialog.jsp"%>
		<script>
		       $('#robotMenu').addClass('current');
		</script>
	</body>
</html>

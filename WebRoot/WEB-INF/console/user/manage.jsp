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
		<title>用户管理</title>
	    <%@include file="/resources.jsp"%>
	</head>
	<body class="web-app ui-selectable">


		<%@include file="../header.jsp"%>

		<%@include file="../nav.jsp"%>

		<div id="mainWrapper">
		<div class="lay-main-toolbar"></div>

		 
				<div>
					<form action="<%=basePath%>/console/user/list.action" method="post"
						id="searchForm" style="padding: 0px;">
						<input type="hidden" name="currentPage" id="currentPage" value="1" />
						<table width="100%" class="utable">

							<thead>
								<tr class="tableHeader">
								    <th width="10%">UID</th>
									<th width="15%">账号</th>
									<th width="10%">名称</th>
									<th width="10%">性别</th>
									<th width="10%">电话号码</th>
									<th width="10%">邮箱</th>
									<th width="20%">操作</th>
								</tr>
							 	<tr>
									<td >
										<input name="uid" value="${user.uid }" type="number"
											class="form-control"
										    style="margin: 3px 0px;height: 32px;" />
									</td>
                                     <td >
									<input name="account" value="${user.account }" type="text"
											class="form-control"
											style="margin: 3px 0px;height: 32px;" />
									</td>
									<td>
										<input name="name" type="text" value="${user.name }"
											class="form-control"
											style="margin: 3px 0px;height: 32px;" />
									</td>
									<td>
									<select name="gender" class="form-control" id="mstatus" style="height: 32px;margin: 3px 0px;width: 100%; height: 30px;padding:2px;">
									 <option />
									 <option value="1" <c:if test="${user.gender  eq '1'}"> selected="selected"</c:if>>男</option>
									 <option value="0" <c:if test="${user.gender  eq '0'}"> selected="selected"</c:if>>女</option>
									</select>
									</td>
									<td>
									<input name="telephone" value="${user.telephone }" type="text"
											class="form-control"
											style="margin: 3px 0px;height: 32px;" />
									</td>
									<td>
									<input name="email" value="${user.email }" type="text"
											class="form-control"
											style="margin: 3px 0px;height: 32px;"/>
									</td>
									 
									<td >
										<button type="submit" class="btn btn-primary btn-sm">
											<span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查询
										</button>
									</td>
								</tr>
							 
							</thead>
							<tbody id="checkPlanList">

								<c:forEach var="user" items="${page.dataList}">
									<tr id="${user.uid}"  style=" height: 50px;">
									 
									 <td>
											${user.uid }
										</td>
										<td>
											${user.account }
										</td>
										<td>
											${user.name }
										</td>
										<td>
											<c:if test="${user.gender eq '0' }">女</c:if>
											<c:if test="${user.gender eq '1' }">男</c:if>
										</td>
									 <td>
											${user.telephone }
										</td>
										<td>
											${user.email }
										</td>
										 
										<td>
										
										  <div class="btn-group btn-group-xs">
											  <button type="button" class="btn btn-primary"  style="padding: 5px;" onclick="showEditDialog('${user}')">
											   <span class="glyphicon glyphicon-edit" aria-hidden="true"></span> 修改
											  </button>
									      </div>
										</td>
									</tr>
								</c:forEach>
								 
							</tbody>
							<tfoot>
							<tr>
									<td colspan="7">
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
		       $('#userMenu').addClass('current');
		</script>
	</body>
</html>

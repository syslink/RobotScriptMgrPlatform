<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/table.tld" prefix="table"%>
<%@ taglib uri="/WEB-INF/tld/category.tld" prefix="category"%>
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
		<title>内容管理系统</title>
	    <%@include file="/resources.jsp"%>
	    <script type="text/javascript" src="<%=resourceBasePath%>/resource/js/jquery-ui.min.js"></script>
		<script>
		  function doDelete(id)
		  {
		     var setting = {hint:"删除后无法恢复,确定删除这个剧本吗?",
		                    onConfirm:function(){
		                      showProcess('正在删除，请稍候......');
		                      $.post("<%=basePath%>/console/script/delete.action", {sid:id},
							  function(data){
							      hideProcess();
							      showSTip("删除成功");
					              $('#'+id).fadeOut().fadeIn().fadeOut();
					              doHideConfirm();
						      });
		                     
		                    }};
		     doShowConfirm(setting);
		  }
		  $(function () {
             $('[data-toggle="popover"]').popover();
          });

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
		<div class="lay-main-toolbar">
		       <div class="btn-group">
					   <button type="button" class="btn btn-success" style="margin-top: 5px;font-size: 12px;" onclick="doShowDialog('AddDialog','slide_in_left')">
						 <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
					   </button>
			   </div>
        </div>
		 
				<div>
					<form action="<%=basePath%>/console/script/list.action" method="post"
						id="searchForm" style="padding: 0px;">
						<input type="hidden" name="currentPage" id="currentPage" value="1"/>
						<table width="100%" class="utable">

							<thead>
								<tr class="tableHeader">
								    <th width="10%">ID</th>
									<th width="15%">名称</th>
									<th width="9%">作者ID</th>
									<th width="11%">类型</th>
									<th width="7%">状态</th>
									<th width="7%">公开</th>
									<th width="6%">角色数</th>
									<th width="25%">描述</th>
									<th width="12%">创建时间</th>
									<th width="14%">操作</th>
								</tr>
							 	<tr>
                                    <td>
                                      <input name="sid" type="text" value="${script.sid }"
											class="form-control"
											style="margin: 3px 0px;height: 32px;" /> 
									</td>

									<td>
										<input name="title" type="text" value="${script.title }"
											class="form-control"
											style="margin: 3px 0px;height: 32px;" />
									</td>
									<td>
										<input id="usrid" name="uid" type="text" value="${script.uid }"
											class="form-control"
											style="margin: 3px 0px;height: 32px;" />
									</td>
									<td>
									<select name="type"   class="form-control" style="width: 100%; margin: 3px 0px;height: 32px;padding:2px;">
									 <option />
									 <category:options key = "${script.type}"/>
									</select>
									</td>
									<td>
									<select name="state"   class="form-control" style="width: 100%; margin: 3px 0px;height: 32px;padding:2px;">
									 <option />
									 <option value="1" <c:if test="${script.state eq '1'}"> selected="selected"</c:if>>正常</option>
									 <option value="0" <c:if test="${script.state eq '0'}"> selected="selected"</c:if>>未审核</option>
									</select>
									</td>
									<td>
									<select name="open"   class="form-control" style="width: 100%; margin: 3px 0px;height: 32px;padding:2px;">
									 <option />
									 <option value="1" <c:if test="${script.open eq '1'}"> selected="selected"</c:if>>公开的</option>
									 <option value="0" <c:if test="${script.open eq '0'}"> selected="selected"</c:if>>私有的</option>
									</select>
									</td>
									<td>
										<input name="roleCount" type="number" value="${script.roleCount }"
											class="form-control"
											style="margin: 3px 0px;height: 32px;padding: 5px;" />
									</td>
									<td>
									</td>
									<td>
									</td>
									<td >
										<button type="submit" class="btn btn-primary btn-sm">
										 <span class="glyphicon glyphicon-search"></span> 查询
										</button>
									</td>
								</tr>
							 
							</thead>
							<tbody>

								<c:forEach var="script" items="${page.dataList}">
									<tr id="${script.sid}"  style=" height: 50px;">
										<td>
										<span  tabindex="0" role="button" data-toggle="popover" title="显示" data-placement="top" data-content="${script.sid }">${script.sid }</span>
										</td>
										<td>
											${script.title }
										</td>
										<td>
											${script.uid }
										</td>
										<td>
										   <category:name key = "${script.type}"/>
										</td>
										<td>
										 <c:if test="${script.state eq '0'}">
										         未审核
										 </c:if>
										 <c:if test="${script.state eq '1'}">
										         正常
										 </c:if>
										</td>
										<td>
										 <c:if test="${script.open eq '1'}">
										        公开的
										 </c:if>
										 <c:if test="${script.open eq '0'}">
										         私有的
										 </c:if>
										</td>
										<td>
										${script.roleCount }
										</td>
										<td>
										     ${script.description }
										</td>
											<td><table:datetime timestamp="${script.timestamp}"/></td>
										<td>
										
										    <div class="btn-group btn-group-xs">
											  <button type="button" class="btn btn-primary"  style="padding: 5px;" onclick="showEditDialog('${script.sid}')">
											   <span class="glyphicon glyphicon-edit"></span> 修改
											  </button>
											  <button type="button" class="btn btn-success"  style="padding: 5px;" onclick="showCommandDialog('${script.sid}')">
											  <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> 剧情
											  </button>
										 	  <button type="button" class="btn btn-danger"  style="padding: 5px;" onclick="doDelete('${script.sid}')">
										 	  <span class="glyphicon glyphicon-trash" aria-hidden="true"></span> 删除
										 	  </button>
											</div>
										</td>
									</tr>
								</c:forEach>
								 
							</tbody>
							<tfoot>
							<tr>
									<td colspan="10">
										<table:page page="${page}"></table:page>
									</td>
								</tr>
							
							</tfoot>
						</table>
					</form>

				</div>
			</div>
<%@include file="addDialog.jsp"%>
            <%@include file="editDialog.jsp"%>
            <%@include file="commandConfig.jsp"%>
            <%@include file="uploadVoiceFile.jsp"%>
		<script>
		       $('#scriptMenu').addClass('current');
		</script>
	</body>
</html>

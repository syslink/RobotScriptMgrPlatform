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
		  <script>
		  function doDelete(id)
		  {
		     var setting = {hint:"删除后无法恢复,确定删除这个剧本吗?",
		                    onConfirm:function(){
		                      showProcess('正在删除，请稍候......');
		                      $.post("<%=basePath%>/console/yunsou/delete.action", {id:id},
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
					<form action="<%=basePath%>/console/yunsou/list.action" method="post"
						id="searchForm" style="padding: 0px;">
						<input type="hidden" name="currentPage" id="currentPage" value="1" />
						<table width="100%" class="utable">

							<thead>
								<tr class="tableHeader">
								    <th width="10%">ID</th>
									<th width="15%">问题</th>
									<th width="20%">答案</th>
									<th width="40%">分词</th>
									<th width="10%">操作</th>
								</tr>
							 	<tr>
									<td >
									</td>
                                     <td >
									<input name="question" value="${model.question }" type="text"
											class="form-control"
											style="margin: 3px 0px;height: 32px;" />
									</td>
									<td>
										 
									</td>
									<td>
									 
									</td>
									 
									<td >
										<button type="submit" class="btn btn-primary btn-sm">
											<span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查询
										</button>
									</td>
								</tr>
							 
							</thead>
							<tbody>

								<c:forEach var="yunsou" items="${page.dataList}">
									<tr id="${yunsou.id}"  style=" height: 50px;">
									 
									 <td>
											${yunsou.id }
										</td>
										<td>
											${yunsou.question }
										</td>
										<td>
											${yunsou.answer }
										</td>
										<td>
											 ${yunsou.discreted }
										</td>
									 
										<td>
										
										  <div class="btn-group btn-group-xs">
											  <button type="button" class="btn btn-danger"  style="padding: 5px;" onclick="doDelete(${yunsou.id })">
											   <span class="glyphicon glyphicon-trash" aria-hidden="true"></span> 删除
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
<%@include file="addDialog.jsp"%>
		<script>
		       $('#yunsouMenu').addClass('current');
		</script>
	</body>
</html>

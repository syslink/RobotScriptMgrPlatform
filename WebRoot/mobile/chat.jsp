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
		<title>小派WEB客户端</title>
		<link rel="shortcut icon" href="<%=basePath%>/resource/img/favicon.ico"type="image/x-icon" />
		<link rel="stylesheet" href="<%=basePath%>/resource/bootstrap-3.3.6-dist/css/bootstrap.min.css" />
		<link charset="utf-8" rel="stylesheet" href="<%=basePath%>/resource/css/base-ui.css" />
		<script type="text/javascript" src="<%=basePath%>/resource/js/jquery-2.2.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/resource/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/resource/js/framework.js"></script>
		<script>
	 
		$(function () {
			$('#Ttype').change(function(){ 
				 $(".list-group").hide();
				 $("#list"+$("#Ttype").val()).show();
			}) 
        });
		
		function sendMessage(object)
		{
			showProcess("发送中......");
		    var content = $(object).text();
		    $.post("<%=basePath%>/cgi/message/send.api", {API_AUTH_KEY:'7215EE9C7D9DC229D2921A40E899EC5F',content:content,action:303,receiver:$('#deviceId').val(),format:'0'},
			   function(data){
		    	hideProcess();
		    });
		 }
 
		</script>
	</head>
	<body style="text-align: center;">

    <div class="input-group" style="margin-top: 20px;width: 100%;">
									<span class="input-group-addon" >设备</span>
										
				<select id="deviceId"   class="form-control" style="display: inline; width: 100%;height:40px;">
				  <option value="b8:27:eb:54:57:6c">小黄</option>
				  <option value="b8:27:eb:57:d0:00">小白</option>
				  <option value="b8:27:eb:9c:ab:ec">小金</option>
				 </select>
	  </div>
  <div class="input-group" style="margin-top: 30px;width: 100%;">
	   <span class="input-group-addon" >分类</span>
				  <select id="Ttype"   class="form-control" style="display: inline; width: 100%;height:40px;">
				  <option value="1">英语</option>
				   <option value="2">语文</option>
					<option value="3">数学</option>
					<option value="4">其他</option>
				 </select>
	  </div>
	  
	 
      <!-- 英語場景文字配置    在ul里面添加更多的配置-->
     <ul class="list-group" style="margin-top: 20px;" id="list1">
           <li onclick='sendMessage(this)' class='list-group-item'>Hello</li>
<li onclick='sendMessage(this)' class='list-group-item'>小朋友们，早上好！英语怎么说？</li>
<li onclick='sendMessage(this)' class='list-group-item'>Good morning!</li>
<li onclick='sendMessage(this)' class='list-group-item'>小朋友们，见面怎么用英语打招呼呢？</li>
<li onclick='sendMessage(this)' class='list-group-item'>How are you?</li>
<li onclick='sendMessage(this)' class='list-group-item'>两个英国人晚上见面的时候，他们会怎么说呢？</li>
<li onclick='sendMessage(this)' class='list-group-item'>Good evening!</li>
<li onclick='sendMessage(this)' class='list-group-item'>美国的小朋友晚上睡觉前都跟大人说啥呢？</li>
<li onclick='sendMessage(this)' class='list-group-item'>Good night!</li>
<li onclick='sendMessage(this)' class='list-group-item'>有没有小朋友能用英语说出2种夏天吃的水果呢？？</li>
<li onclick='sendMessage(this)' class='list-group-item'>water melon,西瓜</li>
<li onclick='sendMessage(this)' class='list-group-item'>grape, 葡萄</li>
<li onclick='sendMessage(this)' class='list-group-item'>lemon, 柠檬</li>
<li onclick='sendMessage(this)' class='list-group-item'>pineapple,菠萝</li>
<li onclick='sendMessage(this)' class='list-group-item'>peach,桃子</li>
<li onclick='sendMessage(this)' class='list-group-item'>很聪明</li>
<li onclick='sendMessage(this)' class='list-group-item'>很棒</li>
<li onclick='sendMessage(this)' class='list-group-item'>这位小朋友真聪明</li>
<li onclick='sendMessage(this)' class='list-group-item'>你好厉害啊</li>
<li onclick='sendMessage(this)' class='list-group-item'>你好聪明哦</li>
<li onclick='sendMessage(this)' class='list-group-item'>再想想看</li>
<li onclick='sendMessage(this)' class='list-group-item'>加油小朋友</li>
     </ul>
     
     <!-- 语文题場景文字配置    在ul里面添加更多的配置-->
     <ul class="list-group" style="margin-top: 20px;display:none;" id="list2">
           <li onclick='sendMessage(this)' class='list-group-item'>白日依山尽</li>
<li onclick='sendMessage(this)' class='list-group-item'>有没有小朋友会唐诗“咏鹅”呢？</li>
<li onclick='sendMessage(this)' class='list-group-item'>鹅，鹅，鹅，</li>
<li onclick='sendMessage(this)' class='list-group-item'>曲项向天歌。</li>
<li onclick='sendMessage(this)' class='list-group-item'>白毛浮绿水，</li>
<li onclick='sendMessage(this)' class='list-group-item'>红掌拨清波。</li>
<li onclick='sendMessage(this)' class='list-group-item'>唐朝大诗人李白写过一首著名的诗歌《静夜思》，你们听说过吗？</li>
<li onclick='sendMessage(this)' class='list-group-item'>床前明月光，</li>
<li onclick='sendMessage(this)' class='list-group-item'>疑是地上霜。</li>
<li onclick='sendMessage(this)' class='list-group-item'>举头望明月，</li>
<li onclick='sendMessage(this)' class='list-group-item'>低头思故乡。</li>
<li onclick='sendMessage(this)' class='list-group-item'>很聪明</li>
<li onclick='sendMessage(this)' class='list-group-item'>很棒</li>
<li onclick='sendMessage(this)' class='list-group-item'>这位小朋友真聪明</li>
<li onclick='sendMessage(this)' class='list-group-item'>你好厉害啊</li>
<li onclick='sendMessage(this)' class='list-group-item'>你好聪明哦</li>
<li onclick='sendMessage(this)' class='list-group-item'>再想想看</li>
<li onclick='sendMessage(this)' class='list-group-item'>加油小朋友</li>
     </ul>
     
     
     
     <!-- 数学場景文字配置   在ul里面添加更多的配置 -->
     <ul class="list-group" style="margin-top: 20px;display:none;" id="list3">
<li onclick='sendMessage(this)' class='list-group-item'>小朋友们，谁知道1加1等于几？</li>
 <li onclick='sendMessage(this)' class='list-group-item'>1加2等于几？ 请穿红衣服的小朋友回答。</li>
     <li onclick='sendMessage(this)' class='list-group-item'>2加3等于几？  请坐在最后的小朋友回答。</li>
 <li onclick='sendMessage(this)' class='list-group-item'>3加3等于几？谁愿意过来回答呀？</li>
<li onclick='sendMessage(this)' class='list-group-item'>小朋友们的算术都很棒！</li>
 <li onclick='sendMessage(this)' class='list-group-item'>小朋友们，老师有个很难得算术题，5加5等于几？</li>
  <li onclick='sendMessage(this)' class='list-group-item'>下一道题更难了。5加6等于几？</li>
<li onclick='sendMessage(this)' class='list-group-item'>再来一道怎么样？6加7等于几？</li>
<li onclick='sendMessage(this)' class='list-group-item'>最后一道题，做出来的小朋友就是算术老大！7加8等于几？谁愿意过来回答呀？</li>
     <li onclick='sendMessage(this)' class='list-group-item'>小朋友们安静一下，我们来做有个算术题怎么样？十加5等于几？</li>
 <li onclick='sendMessage(this)' class='list-group-item'>十一加6等于几？ 请刚才最活跃的小朋友来做。</li>
 <li onclick='sendMessage(this)' class='list-group-item'>前面这位小美女也来是一道哟！十三加7等于几？ </li>
<li onclick='sendMessage(this)' class='list-group-item'>小朋友的算术都很棒。谁能做出这道题就更棒啦！十五加十一等于几？</li>
   <li onclick='sendMessage(this)' class='list-group-item'>小朋友们有没有谁会做两位数的加法呀？十加十一等于几？</li>
 <li onclick='sendMessage(this)' class='list-group-item'>那么十二加十三等于几？ 请刚才最活跃的小朋友来做。</li>
<li onclick='sendMessage(this)' class='list-group-item'>再来一道难的！十五加十七等于几？ </li>
  <li onclick='sendMessage(this)' class='list-group-item'>小朋友的算术都很棒。下面这道题目可不是闹着玩的！请听题目，十八加十九等于几？</li>
<li onclick='sendMessage(this)' class='list-group-item'>小朋友们来比比谁的算术厉害吧？二十三加三十二等于几？</li>
<li onclick='sendMessage(this)' class='list-group-item'>那么三十三加四十七等于几？ 有没有算术厉害的来试试？</li>
 <li onclick='sendMessage(this)' class='list-group-item'>再来一道难的！三十八加五十四等于几？ </li>
 <li onclick='sendMessage(this)' class='list-group-item'>小朋友们的算术都太厉害了。下面这道题目可不是闹着玩的！请听题目，三十九加六十五等于几？</li>
     </ul>
     
     
    <!-- 其他場景文字配置  在ul里面添加更多的配置  -->
     <ul class="list-group" style="margin-top: 20px;display:none;" id="list4">
           <li onclick='sendMessage(this)' class='list-group-item'>呵呵</li>
     </ul>
	</body>
</html>

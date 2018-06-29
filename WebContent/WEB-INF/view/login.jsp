<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 
<link rel="shortcut icon" href="favicon.ico"/>
<link rel="bookmark" href="favicon.ico"/>
 -->
<link rel="stylesheet" type="text/css" href="h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="h-ui/css/H-ui.login.css" />
<link rel="stylesheet" type="text/css" href="h-ui/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="h-ui/lib/Hui-iconfont/1.0.1/iconfont.css" />
<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css" />

<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script type="text/javascript" src="h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="h-ui/lib/icheck/jquery.icheck.min.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
$(function() {
	//点击图片切换验证码
	$("#vcodeImg").click(function() {
		this.src="LoginServlet?method=GetVcode&t="+new Date().getTime();
	});
	
	//登录

	$("#submitBtn").click(function() {
		var data = $("#form").serialize();
		$.ajax({
			type: "post",
			url: "LoginServlet?method=Login",
			data: data, 
			dataType: "text", //返回数据类型
			success: function(msg){
				if("vcodeError" == msg) {
					$.messager.alert("消息提醒", "验证码错误!", "warning");
					$("#vcodeImg").click();//切换验证码
					$("input[name='vcode']").val("");//清空验证码输入框
				} else if("loginError" == msg) {
					$.messager.alert("消息提醒", "用户名或密码错误!", "warning");
					$("#vcodeImg").click();//切换验证码
					$("input[name='vcode']").val("");//清空验证码输入框
				} else if("admin" == msg) {
					window.location.href = "LoginServlet?method=toAdminView";
				} else if("student" == msg) {
					window.location.href = "LoginServlet?method=toStudentView";
				}
			}
		});
	});
	//设置复选框
	$(".skin-minimal input").iCheck({
		radioClass : 'iradio-blue',
		increaseArea : '25%'
	});
})
</script>

<title>登录 学生成绩管理系统</title>
<meta name="keywords" content="学生成绩管理系统">
</head>
<body>
	<div class="header" style="padding: 0;">
		<h2
			style="color: white; width: 400px; height: 60px; line-height: 60px; margin: 0 0 0 30px; padding: 0;">学生成绩管理系统</h2>
	</div>
	<div class="loginWraper">
		<div id="loginform" class="loginBox">
			<form id="form" class="form form-horizontal" action="<%=request.getContextPath() %>/servlet/LoginServlet" method="post">
				<div class="row cl">
					<label class="form-label col-3"><i class="Hui-iconfont">&#xe60d;</i></label>
					<div class="formControls col-8">
						<input id="" name="account" type="text" placeholder="账户"
							class="input-text size-L">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-3"><i class="Hui-iconfont">&#xe60e;</i></label>
					<div class="formControls col-8">
						<input id="" name="password" type="password" placeholder="密码"
							class="input-text size-L">
					</div>
				</div>
				<div class="row cl">
					<div class="formControls col-8 col-offset-3">
						<input class="input-text size-L" name="vcode" type="text"
							placeholder="请输入验证码" style="width: 200px;"> <img
							title="点击图片切换验证码" id="vcodeImg"
							src="LoginServlet?method=GetVCode">
					</div>
				</div>

				<div class="mt-20 skin-minimal" style="margin-left: 20%">
					<div class="radio-box">
						<input type="radio" id="radio-2" name="type" checked value="2" />
						<label for="radio-1">学生</label>
					</div>
					<div class="radio-box">
						<input type="radio" id="radio-1" name="type" value="1" /> <label
							for="radio-3">管理员</label>
					</div>
				</div>

				<div class="row">
					<div class="formControls col-8 col-offset-3">
						<input id="submitBtn" type="button"
							class="btn btn-success radius size-L"
							value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="footer">学生成绩管理系统</div>

</body>
</html>
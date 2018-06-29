<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>个人信息</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/validateextends.js"></script>
	<script type="text/javascript">
	</script>
</head>
<body>

	<form id="editForm">
	   	<table cellpadding="8" >
	   		<tr>
	   			<td>学号:</td>
	   			<td>
	   				<input id="sno" value="${student.sno }" data-options="readonly: true" class="easyui-textbox" style="width: 200px; height: 30px;" type="text" />
	   			</td>
	   		</tr>
	   		<tr>
	   			<td>姓名:</td>
	   			<td>
	   				<input id="sname" value="${student.sname }" data-options="readonly: true" class="easyui-textbox" style="width: 200px; height: 30px;" type="text" />
	   			</td>
	   		</tr>
	   		<tr>
	   			<td>性别:</td>
	   			<td>
	   				<input id="sex" value="${student.sex }" data-options="readonly: true" class="easyui-textbox" style="width: 200px; height: 30px;" type="text" />
	   			</td>
	   		</tr>
	   		<tr>
	   			<td>年龄:</td>
	   			<td>
	   				<input id="age" value="${student.age }" data-options="readonly: true" class="easyui-textbox" style="width: 200px; height: 30px;" type="text" />
	   			</td>
	   		</tr>
	   		<tr>
	   			<td>系别:</td>
	   			<td>
	   				<input id="sdept" value="${student.sdept }" data-options="readonly: true" class="easyui-textbox" style="width: 200px; height: 30px;" type="text" />
	   			</td>
	   		</tr>
	   	</table>
	</form>
	
</body>
</html>
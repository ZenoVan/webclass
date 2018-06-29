<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>课程列表</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/themes/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
	$(function() {	
		//datagrid初始化 
	    $('#dataList').datagrid({ 
	        title:'课程列表', 
	        border: true, 
	        fit: true,//自动大小 
	        method: "post",
	        url:"ExamServlet?method=StudentExamList",
	        columns: [[
 		        {field:'cname',title:'课程名称',width:200},    
 		        {field:'cno',title:'课程编号',width:200, sortable: true},
 		        {field:'teacher',title:'任教老师',width:200},
 		        {field:'grade',title:'课程成绩',width:200},
 		       	{field:'backnote',title:'备注',width:250}
	 		]]
	    }); 
	  	
	});
	</script>
</head>
<body>
	<!-- 数据列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	    
	</table> 
	
</body>
</html>
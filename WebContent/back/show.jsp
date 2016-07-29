<%@page import="com.yc.student.entity.Student"%>
<%@page import="java.util.List"%>
<%@page import="com.yc.stutent.dao.StudentDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>学生信息</title>
</head>
<body>
	<table border = "1px" cellspacing = "0px" cellpadding = "0px" width = "90%" align="center" style="border-collapse:collapse;">
	<thead>
		<tr>
			<th>图像</th>
			<th>学号 </th>
			<th>姓名</th>
			<th>年龄</th>
			<th>联系方式</th>
			<th>所在班级</th>
		</tr>
	</thead>
	<tbody id="show_student" align="center">
		<%
			StudentDao studentDao = new StudentDao();
			List<Student> list = studentDao.find();
			for(Student stu : list){
		%>
		
			<tr>
				<td><%=stu.getPhoto() %></td>
				<td><%=stu.getSid() %></td>
				<td><%=stu.getSname() %></td>
				<td><%=stu.getAge() %></td>
				<td><%=stu.getTel() %></td>
				<td><%=stu.getCname() %></td>
			</tr>
		<% 	
			}
		%>
	</tbody>
	</table>
	<center><p>
		<a href="">首页</a>
		<a href="">上一页</a>
		<a href="">下一页</a>
		<a href="">尾页</a>
		<span>当前第1页/总10页&nbsp;&nbsp;每页5页/共48条</span>	
	</p></center>
</body>
</html>
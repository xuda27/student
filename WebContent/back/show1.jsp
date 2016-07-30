<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>学生信息</title>
<style>
	#pageInfo a {
		float:left
	
		display: block;
		
		margin-top: 5px;
		margin-left: 5px;
		width : 30px;
		height : 30px;
		
		font-size: 30px;
		line-height: 30px;
		text-align :center;
	}
</style>
<script type="text/javascript" src="../js/jquery-1.11.3.js"></script>
<script type="text/javascript">
	$( function() {
		$.post("doshow.jsp",{op:1, pageNo:1, pageSize:5},function(data) {
			var str = "";
			$.each(data.studentInfo, function( index, item ) {
				str += "<tr><td>" + item.photo + "</td><td>" + item.sid + "</td><td>"+
				item.sname + "</td><td>" + item.age + "</td><td>" + item.tel + "</td><td>" + item.cname + 
				"</td><td>删除</td></tr>";
			});
			$("#show_student").html("").append( $(str) );
			var total = parseInt( data.total );
			
			var page = "";
			for(var i =0; i < total/5; i++ ){
				if(i == 0){
					page += "<a href = 'javascript:findStudentByPage("+ (i+1) + ",5)' class='over'>"+(i+1)+"</a>";
				}else{
					page += "<a href = 'javascript:findStudentByPage("+ (i+1) + ",5)' class='out'>"+(i+1)+"</a>";
					
				}
			}
			$("#pageInfo").html("").append( $(page) );
		},"json");
	} );
	
	function findStudentByPage(pageNo, pageSize){
		$.post("doshow.jsp", {op:2, pageNo:pageNo, pageSize:pageSize}, function(data) {
			var str = "";
			$.each(data.studentInfo, function( index, item ) {
				str += "<tr><td>" + item.photo + "</td><td>" + item.sid + "</td><td>"+
				item.sname + "</td><td>" + item.age + "</td><td>" + item.tel + "</td><td>" + item.cname + 
				"</td><td>删除</td></tr>";
			});
			$("#show_student").html("").append( $(str) );
		},"json");	
		
		$("#pageInfo a").attr("class", "out");
		$("#pageInfo a").eq(pageNo - 1).attr("class", "over");
	}
</script>
</head>
<body>
	<center><h1><a href="../add.jsp">返回添加</a></h1></center>
	<table border="1px" cellspacing="0px" cellpadding="0px" width="90%" align="center" style="border-collapse">
		<thead>
			<tr>
				<th>头像</th>
				<th>学号</th>
				<th>姓名</th>
				<th>年龄</th>
				<th>联系方式</th>
				<th>所在班级</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="show_student" align="center">
		
		</tbody>
	</table>
	<center id="pageInfo"></center>
</body>
</html>
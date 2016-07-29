<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>班级学生添加界面</title>
	<script type="text/javascript" src="js/jquery-1.11.3.js"></script>
	<script>
		function addClasses() {
			var cname = $.trim( $("#cname").val() );
			var $cname=$("#cname");
			if(cname === ""){
				$cname.next().text("请输入班级名称。。。").css("color", "red");
			}else{
				$.post("back/doaddclasses.jsp", {op:"addClasses",cname:cname}, function(data){
					data = parseInt( $.trim(data) );
					if( data>0 ){
						$cname.next().text("添加班级成功。。。").css("color", "green");
						$cname.val("");
					}else{
						$cname.next().text("添加班级失败。。。").css("color", "red");
					}
					
				});
				
			}
		}
		
		$( function() {
			$.post( "back/doaddclasses.jsp", {op:"findClasses"}, function(data) {
				if( data != "" ){
					$.each(data,function(index, item){
						$("#classesInfo").append( $("<option value='"+item.cid+"'>"+item.cname+"</option>") );
					});
					//json：{}
				}
			},"json" );
		});
		
		function addStudent() {
			var sname = $.trim( $("#sname").val() );
			var age = $.trim( $("#age").val() );
			var tel = $.trim( $("#tel").val() );
			var cid = $.trim( $("#classesInfo").val() );
			
			
			$.post("back/dostudent.jsp",{ op:"addStudent", sname:sname, age:age, tel:tel, cid:cid }, function(data) {
				if(data == null){
					alert("添加数据失败...");
				}else{
					$( "#sname" ).val("");
					$( "#age" ).val("");
					$( "#tel" ).val("");
					$( "#show_student" ).html("");
					$.each(data,function(index,item){
						$("#show_student").append( "<tr><td></td><td>" + item.sid + "</td><td>" + item.sname + "</td><td>"
						+ item.age + "</td><td>"+ item.tel + "</td><td>" + item.cname + "</td></tr>" );
						
					});
				}	
			}, "json" );
		}
	</script>
</head>
<body>
	<fieldset>
		<legend>添加班级</legend>
		<form method="post">
			班级名称：<input type="text" name="cname" id="cname"><span></span>
			<input type="button" value="添加班级" onclick="addClasses()">
		</form>
	</fieldset>
	
	<fieldset>
		<legend>添加学生</legend>
		<form method="post">
			班级：<select name="cid" id="classesInfo">
				
				</select>
			姓名：<input type="text" name="sname" id="sname">
			年龄：<input type="number" name="age" id="age">
			联系方式：<input type="text" name="tel" id="tel">
			图像：<input type="file" name="photo" id="photo">
			<input type="button" value="添加" onclick="addStudent()">
		</form>
	</fieldset>	
	
	<fieldset>
		<legend>学生信息浏览</legend>
		<table border="1px" cellspacing="0px" width="90%" align="center" style="border-collapse:collapse">
			<thead>
				<tr>
					<th>图像</th>
					<th>学号</th>
					<th>姓名</th>
					<th>年龄</th>
					<th>联系方式</th>
					<th>班级</th>
				</tr>
			</thead>
			<tbody id="show_student" align="center">
			
			</tbody>
		</table>
	</fieldset>
</body>
</html>
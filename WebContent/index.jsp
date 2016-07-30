<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>班级学生添加界面</title>
<style>
	table tr td img{
		width: 100px;
		height:100px;
	}
	
	#pageInfo {
		margin-top: 10px;
	}
	
	#pageInfo a{
		text-decoration: none;
		padding:2px 10px;
		color: #fff;
		font-weight: bold;
		margin-right: 10px;
	}
	
	#pageInfo a:hover{
		background: #999;
	}
	
	.over{
		background: navy;
	}
	
	.out{
		background: #ccc;
	}
</style>
	<script type="text/javascript" src="js/jquery-1.11.3.js"></script>
	<script type="text/javascript" src="js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="js/showPic.js"></script>
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
			firstPageOP();
		});
		//添加学生
		function addStudent() {
			var sname = $.trim( $("#sname").val() );
			var age = $.trim( $("#age").val() );
			var tel = $.trim( $("#tel").val() );
			var cid = $.trim( $("#classesInfo").val() );
			
			$.ajaxFileUpload( {
				url : "back/doadd.jsp",
				secureuri : false,
				fileElementId : "photo", //要上传的文件的id，如果哦呦多个file的表单元素，则用数组
				dataType : "json",
				data:{cid:cid, sname:sname, age:age, tel:tel},
				success: function(data, status) {
					data = $.trim(data);
					
					if(parseInt(data) == 1){
						$("#sname").val("");
						$("#age").val("");
						$("#tel").val("");
						$("#showPicDiv").css("display", "none");
						firstPageOP();
					}else{
						alert("添加学生信息失败..")
					}
					
				},
				error:function(data, status, e){
					alert("添加学生信息失败");
				}
			});
		
			
			
		}
		
		//更新页码更新界面
		 function firstPageOP() {
			$.post("back/doshow.jsp",{op:1, pageNo:1, pageSize:5},function(data) {
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
		} 
		
		//分页操作
		function findStudentByPage(pageNo, pageSize){
			$.post("back/doshow.jsp", {op:2, pageNo:pageNo, pageSize:pageSize}, function(data) {
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
			图像：<input type="file" name="photo" id="photo" multiple="multiple" onchange="setImagePreviews(this,'showPicDiv')" >
				<input type="button" value="添加" onclick="addStudent()" >
		</form>
		<div id="showPicDiv" style="width:840px; display:none;"></div>
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
					<th>操作</th>
				</tr>
			</thead>
			<!-- 学生信息 -->
			<tbody id="show_student" align="center">
			
			</tbody>
		</table>
		<!--   -->
		<center id="pageInfo"></center>
	</fieldset>
	
</body>
</html>
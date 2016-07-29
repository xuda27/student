<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
	<script type="text/javascript" src="js/jquery-1.11.3.js"></script>
	<script type="text/javascript" src="js/showPic.js"></script>
	<script type="text/javascript">
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
			if(sname != ""){
				$("#add_student").submit();
			}else{
				return;
			}
			
		}
	</script>
</head>
<body>
	<form action="back/doadd.jsp" method="post" id="add_student" enctype="multipart/form-data">
		班级：<select name="cid" id="classesInfo">
				
			</select>
		姓名：<input type="text" name="sname" id="sname">
		年龄：<input type="number" name="age" id="age">
		联系方式：<input type="text" name="tel" id="tel">
		图像：<input type="file" name="photo" id="photo" multiple="multiple" onchange="setImagePreviews(this,'showPicDiv')" >
		<input type="button" value="添加" onclick="addStudent()" >
	</form>
	<div id="showPicDiv" style="width:840px;"></div>
</body>
</html>
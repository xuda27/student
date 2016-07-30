<%@page import="java.util.Map"%>
<%@page import="com.yc.student.utils.UploadUtil"%>
<%@page import="com.yc.stutent.dao.StudentDao"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Random"%>
<%@page import="java.util.Date"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding( "utf-8" );
	/* 
	
	//创建一个磁盘文件项工厂
	DiskFileItemFactory factory = new DiskFileItemFactory();
	
	
	//通过工厂获取一个文件上传的对象
	ServletFileUpload upload = new ServletFileUpload( factory );
	*/
	//解析请求中的数据
	// List list = upload.parseRequest(request);
	
//	StudentDao studentDao = new StudentDao(); 
	
	/* FileItem item = null;
	String name =null ;
	String cid = null;
	String sname = null;
	String tel =null;
	String age =null;
	String fileName =null;
	String photo =null;  */
	
	/* for( int i=0,len=list.size();i<len;i++){
		item = (FileItem)list.get(i);
		//如果是文件 则.. 如果是普通的表单元素则...
		if( item.isFormField() ){
			//System.out.println( item.getFieldName()+" "+item.getString( "utf-8" ) );
			name = item.getFieldName();
			if("cid".equals(name)){
				cid  = item.getString("utf-8");
			}else if("sname".equals(name)){
				sname  = item.getString("utf-8");
			}else if("tel".equals(name)){
				tel  = item.getString("utf-8");
			}else if("age".equals(name)){
				age  = item.getString("utf-8");
			}
			
		}else{//说明可能是一个文件
			if( item.getName() != null && !"".equals( item.getName() ) ){
				System.out.println( item.getContentType() );//类型
				System.out.println( item.getSize() );//大小
				System.out.println( item.getName() );
				System.out.println( item.getFieldName() );//  html中的name属性的值 
				
				name = item.getName();//获取上传的文件的文件名
				//重命名
				fileName = new Date().getTime()+new Random().nextInt(10000)+name.substring(name.lastIndexOf("."));
				//获取Apache的图片路径
				File readFile = new File( pageContext.getServletContext().getRealPath("/")+"upload",fileName);
				
				//将上传的图片写入到文件readFile中
				item.write(readFile);
				
				//将图片路径在服务器中的路径存入到数据库中
				photo = "upload/"+fileName;
			}else{
				photo ="";
			}
		}
	} */
	
	//将数据添加到数据库
	StudentDao studentDao = new StudentDao();
	UploadUtil upload = new UploadUtil();
	Map<String,String> map = upload.upload(pageContext);
	if(studentDao.add( map.get("cid"), map.get("sname"), map.get("age"), map.get("tel"), map.get("photo") ) > 0){
		response.sendRedirect("show1.jsp");
	}else{
		response.sendRedirect("../add.jsp");
	}
%>
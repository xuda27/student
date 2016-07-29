<%@page import="com.yc.student.entity.Student"%>
<%@page import="java.util.List"%>
<%@page import="com.yc.stutent.dao.StudentDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding( "utf-8" );
	StudentDao studentDao = new StudentDao();
	String cid = request.getParameter("cid");
	String sname = request.getParameter( "sname" );
	String age = request.getParameter( "age");
	String tel = request.getParameter("tel");
	
	if( studentDao.add(cid, sname, age, tel, "") > 0 ){
		List<Student> list = studentDao.find();
		if(list != null && list.size() > 0){
			StringBuffer sbf = new StringBuffer("[");
			for(Student stu : list){
				sbf.append( stu +",");
			}
			String str = sbf.toString();
			str = str.substring(0, str.lastIndexOf(","));
			out.print(str+"]");
		}else{
			out.print("");
		}
	}
%>
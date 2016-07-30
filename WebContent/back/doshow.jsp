<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.yc.student.entity.Student"%>
<%@page import="java.util.List"%>
<%@page import="com.yc.stutent.dao.StudentDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");

	String op = request.getParameter("op");
	String pageNo = request.getParameter("pageNo");
	String pageSize = request.getParameter("pageSize");
	
	StudentDao studentDao = new StudentDao();
	List<Student> list = studentDao.find(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
	
	JSONArray json = JSONArray.fromObject(list); //把list集合中的数据转成一个json对象数组
	JSONObject jb = new JSONObject();
	if( "1".equals(op) ){
		jb.put( "total", studentDao.getTotal(null) );
	}
	jb.put("studentInfo", json);
	out.print( jb.toString() );
%>
<%@page import="com.yc.stutent.dao.StudentDao"%>
<%@page import="com.yc.stutent.dao.PageUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String flag=request.getParameter("op");
	StudentDao studentDao =new StudentDao();
	PageUtil pageUtil = (PageUtil)session.getAttribute("pageUtil");
	if( "1".equals(flag) ){
		pageUtil.setPageNo(1);
	}else if( "2".equals(flag) ){
		pageUtil.getPrePageNo();
	}else if( "3".equals(flag) ){
		pageUtil.getNextPageNo();
	}else if( "4".equals(flag) ){
		pageUtil.setPageNo( pageUtil.getTotalPage() );
		
	}
	session.setAttribute("pageUtil", pageUtil);
	response.sendRedirect("show.jsp");
%>
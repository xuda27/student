<%@page import="com.yc.student.entity.Classes"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.yc.stutent.dao.ClassesDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding( "utf-8" );
	String op = request.getParameter( "op" );
	ClassesDao classesDao =new ClassesDao();
	
	if( "addClasses".equals(op) ){//此请求是要进行添加班级信息的操作
		String cname = request.getParameter( "cname" );
		int result = classesDao.add( cname );		
		out.println( result );
	}else if( "findClasses".equals(op) ){//说明是要进行查询所有班级信息的操作
		/* List<Map<String,String>> list = classesDao.find();
		//此时说明服务器端已经准备好了所有的班级信息数据，接下来操作 将信息传到客户端中
		StringBuffer sbf = new StringBuffer("[");
		String str = null;
		
		Map<String,String>map;
		int i = 0,len = 0;
			if( list != null && list.size() > 0 ){
			
			for( i=0, len = list.size(); i<len-1; i++){
				map = list.get(i);
				sbf.append(" {\"cid\":\""+ map.get("CID")+"\","+"\"cname\":\""+map.get("CNAME")+"\"},");
			}
			map = list.get(i);
			sbf.append(" {\"cid\":\""+ map.get("CID")+"\","+"\"cname\":\""+map.get("CNAME")+"\"}");
			sbf.append("]");
			str = sbf.toString();
			
		} */
		StringBuffer sbf =new StringBuffer("[");
		List<Classes> list1 = classesDao.finds();
		for( Classes c : list1){
			sbf.append(c+",");
		}
		String str = sbf.toString();
		str = str.substring( 0, str.lastIndexOf(",") );
		out.println( str +"]" );
		
	}
	
	

	
	
%>
package com.yc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;


import com.yc.student.utils.UploadUtil;
import com.yc.stutent.dao.StudentDao;


public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrintWriter out;
	private StudentDao studentDao = new StudentDao();
	private UploadUtil upload = new UploadUtil();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		out = response.getWriter();
		
		String op = request.getParameter("op");
		
		if( "addStudent".equals(op) ){
			addStudent(request, response);
		}
	}
	
	private void addStudent(HttpServletRequest request,
			HttpServletResponse response) {
		PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(this, request, response, null, true, 1024, true);
		Map<String, String> map = upload.upload(pageContext);
		
		String cid = map.get("cid");
		String sname = map.get("sname");
		String age = map.get("age");
		String tel = map.get("tel");
		String photo = map.get("photo");
		
		if( studentDao.add(cid, sname, age, tel, photo) > 0 ){
			out.print(1);
		}else{
			out.print(0);
		}
	}
   
	
    

}

package com.yc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.yc.student.entity.Classes;
import com.yc.stutent.dao.ClassesDao;


public class ClassesServlet extends BasicServlet {
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doPost(request, response);
		
		String op = request.getParameter("op");
		
		if( "addClasses".equals(op) ){
			addClass(request, response);
		}else if( "findClasses".equals(op) ){
			findClass(request, response);
		}
	}

	/**
	 * 查询所有班级信息
	 * @param request
	 * @param response
	 */
	private void findClass(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ClassesDao classesDao = new ClassesDao();
		List<Classes> list = classesDao.finds();
		
		JSONArray json = JSONArray.fromObject(list);
		
		out.print(json);
		out.flush();
		out.close();
	}

	/**
	 * 添加班级信息
	 * @param request
	 * @param response
	 */
	private void addClass(HttpServletRequest request,
			HttpServletResponse response) {
		String cname = request.getParameter("cname");
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ClassesDao classesDao = new ClassesDao();
		int flag = classesDao.add(cname);
		
		out.print(flag);
		out.flush();
		out.close();
	}
   
	
    

}

package com.yc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import com.yc.student.entity.Student;
import com.yc.student.utils.PageUtil;
import com.yc.student.utils.UploadUtil;
import com.yc.stutent.dao.StudentDao;

/**
 * 在高并发时会产生资源抢占的问题，因为Servlet是单例模式，服务器启动时才将会调用init方法 初始化servlet，所以只要服务器不关，servlet就会一直存在。
 * 用户可以直接使用servlet，服务器可以有多个用户，但是全局变量就一个的话 那肯定会抢占资源。
 * 
 * @author xxx
 *
 */
public class StudentServlet extends BasicServlet {
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
		
		if( "addStudent".equals(op) ){
			addStudent(request, response);
		}else if( "findStudent".equals(op) ){
			findStudent(request, response);
		}else if( "studentLogin".equals(op) ){
			studentLogin(request, response);
		}
	}
	
	private void studentLogin(HttpServletRequest request,
			HttpServletResponse response) {
		//获取
		String uname = request.getParameter("uname");
		String pwd = request.getParameter("pwd");
		
		
		//运算
		StudentDao studentDao = new StudentDao();
		int result = 0;
		if( uname == null || "".equals(uname) ){
			result = 1;
		}else if( pwd == null || "".equals(pwd) ){
			result = 2;
		}else{
			Student stu = studentDao.login(uname, pwd);
			if(stu != null){
				HttpSession session = request.getSession();
				session.setAttribute("currentLogin", stu);
				result = 3;
			}else{
				result = 0;
			}
		}
		//转
		this.out(response, result);
	}

	/**
	 * 查找学生
	 * @param request
	 * @param response
	 */
	private void findStudent(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		StudentDao studentDao = new StudentDao();
		
		Object obj = session.getAttribute("pageUtil");
		PageUtil pageUtil = null;
		if(obj == null ){
			pageUtil = new PageUtil(5,studentDao.getTotal(null));
			session.setAttribute("pageUtil", pageUtil);
		}else{
			pageUtil = (PageUtil)obj;
		}
		session.setAttribute( "studentInfo", studentDao.find(pageUtil.getPageNo(), pageUtil.getPageSize()) );
		try {
			response.sendRedirect("back/show.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		   
	}

	private void addStudent(HttpServletRequest request,
			HttpServletResponse response) {
		UploadUtil upload = new UploadUtil();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StudentDao studentDao = new StudentDao();
		
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

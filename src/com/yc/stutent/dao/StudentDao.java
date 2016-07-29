package com.yc.stutent.dao;

import java.util.ArrayList;
import java.util.List;

import com.yc.student.entity.Student;

public class StudentDao {
	private DBHelper db = new DBHelper();
	/**
	 * 添加学生信息
	 * @param cid
	 * @param sname
	 * @param age
	 * @param tel
	 * @param photo
	 * @return 0 or 1 
	 */
	public int add(String cid, String sname, String age, String tel, String photo){
		String sql ="insert into student values(0,?,?,?,?,?)";
		List<String> param = new ArrayList<String>();
		param.add(cid);
		param.add(sname);
		param.add(age);
		param.add(tel);
		param.add(photo);
		return db.doUpdate(sql, param);
	}
	
	public List<Student> find(){
		String sql = "select s.*,cname from student s,classes c where s.cid=c.cid";
		return db.find(sql, null, Student.class);
	}
}

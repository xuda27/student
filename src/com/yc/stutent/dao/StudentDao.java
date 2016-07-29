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
	
	/**
	 * 查询全部学生
	 * @return list<Student>学生的集合
	 */
	public List<Student> find(){
		String sql = "select s.*,cname from student s,classes c where s.cid=c.cid";
		return db.find(sql, null, Student.class);
	}
	
	/**
	 * 分页查询学生信息
	 * @param pageNo：要查询的页数
	 * @param pageSize：每页显示的记录数
	 * @return
	 */
	public  List<Student> find(Integer pageNo, Integer pageSize){
		String sql = "select * from (select a.*, rownum rn from(select s.*, cname from student s, classes c "
				+"where s.cid = c.cid order by sid ) a where rownum<=?) where rn >?";
		List<String> param = new ArrayList<String>();
		param.add( String.valueOf(pageNo*pageSize) );
		param.add( String.valueOf( (pageNo-1)*pageSize) );
		return db.find(sql, param, Student.class);
	}
	
	/**
	 * 获取总记录
	 * @param cid
	 * @return
	 */
	public int getTotal(Integer cid){
		String sql = null;
		List<String> param = new ArrayList<String>();
		if( cid != null){
			sql = "select count(sid) as count from student s, class c where s.cid = c.cid and cid =?";
			param.add( String.valueOf(cid) );
		}else{
			sql = "select count(sid) as count from student s, class c where s.cid = c.cid";
		}
		
		double count = db.doSelectFunction(sql, param);
		return (int) count;
	}
	
}

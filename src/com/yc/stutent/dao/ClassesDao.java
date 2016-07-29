package com.yc.stutent.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.student.entity.Classes;

public class ClassesDao {
	private DBHelper db = new DBHelper();
	
	/**
	 * 添加班级的方法
	 * @param cname: 班级名称
	 * @return
	 */
	public int add(String cname){
		String sql = "insert into classes values(0, ?)";
		List<String> params = new ArrayList<String>();
		params.add( cname );
		return db.doUpdate(sql, params);
	}
	
	/**
	 * 查询所有班级
	 * @return list
	 */
	public List<Map<String,String>> find(){
		String sql = "select cid, cname from classes";
		return db.find(sql, null);
	}
	
	public List<Classes> finds(){
		return db.find("select cid,cname from classes",null, Classes.class);
	}
}

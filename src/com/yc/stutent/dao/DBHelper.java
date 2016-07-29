package com.yc.stutent.dao;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 
 * 关于数据库的所有的功能
 * 
 * @author a
 *
 */
public class DBHelper {

	// 获取连接的方法
	public Connection getCon() {
		Connection con = null;
		try {
			Context ct = new InitialContext();
			DataSource ds = (DataSource) ct.lookup("java:comp/env/jdbc");
			con = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 更新的方法（DML中insert,update,delete）
	 * 
	 * @param: sql要执行的语句，里面可能有？的占位符
	 * @param： ？所代表的值
	 */
	public int doUpdate(String sql, List<String> params) // throws Exception
	{
		Connection con = getCon();
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			setParams(params, pstmt);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e); // 实现了早抛出(在Dao层代码中不处理异常，将异常抛出，由界面以友好的方式处理)
			// throw e ; // 2.异常类型的选择 非受检 如果是受捡的，改动一个地方，
		} finally {
			closeAll(pstmt, con);
		}
		return result;
	}

	private void setParams(List<String> params, PreparedStatement pstmt)
			throws SQLException { // 设参
		if (params != null && params.size() > 0) {
			for (int i = 1; i <= params.size(); i++) {
				pstmt.setString(i, params.get(i - 1));
			}
		}
	}

	/**
	 * 
	 * 查询一：聚合函数的查询
	 * 
	 * @param sql
	 * @param params
	 */
	public double doSelectFunction(String sql, List<String> params) {
		Connection con = getCon();
		PreparedStatement pstmt = null;
		double result = 0;
		ResultSet rs = null;

		try {
			pstmt = con.prepareStatement(sql);
			setParams(params, pstmt);
			rs = pstmt.executeQuery(); // 聚合函数也是一种查询
			if (rs.next()) {
				result = rs.getDouble(1);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			closeAll(rs, pstmt, con);
		}
	}

	/**
	 * 执行创建 DDL操作 创建表,删除，修改，约束，序列。。。
	 * @param sql
	 * @param params
	 */
	public void doDDL(String sql, List<String> params) {
		Connection con = getCon();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			setParams(params, pstmt);
			pstmt.execute();
		} catch (SQLException e) {

			e.printStackTrace();

			throw new RuntimeException(e);
		} finally {
			closeAll(pstmt, con);
		}
	}

	/**
	 * 关闭
	 * @param pstmt
	 * @param con
	 */
	public void closeAll(PreparedStatement pstmt, Connection con) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 重载关闭方法
	 * @param rs
	 * @param pstmt
	 * @param con
	 */
	public void closeAll(ResultSet rs, PreparedStatement pstmt, Connection con) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {

				e.printStackTrace();

				throw new RuntimeException(e);
			}
		}
		closeAll(pstmt, con);
	}

	/**
	 * 查询一：查询出来的结果是一个 ListMap<String,String> > map中键就是 记录的列名，map 的值就是记录的值
	 * Map对应数据表 中的一条记录 List对应数据表中的全部记录
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, String>> find(String sql, List<String> params) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		try {
			// 查询开始
			con = getCon();
			pstmt = con.prepareStatement(sql);
			setParams(params, pstmt);
			// 得到结果集
			rs = pstmt.executeQuery();
			// 得到所有的列名
			List<String> columnNameList = getColumnName(rs);
			// 循环结果集，取出结果
			while (rs.next()) {
				// 每next()一次，就是一条记录 =》 一条记录就是一个Map<String,String>
				Map<String, String> map = new HashMap<String, String>();
				// 将值存到map中
				// rs.get类型（列的序号/列名)；
				// TODO：循环columnNameList,从中取出每个列的名字。再根据列名，以rs.get类型（列名）
				// 取出这一列的值
				for (String cn : columnNameList) {
					String value = rs.getString(cn);
					map.put(cn, value);

				}
				list.add(map); // 将这个map存到list中，一个list就对应了查询的结果

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			closeAll(rs, pstmt, con);

		}
		return list;

	}

	/**
	 * 从ResultSet中取出列名。包装成一个方法
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<String> getColumnName(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}
		List<String> columnList = new ArrayList<String>();
		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 0; i < rsmd.getColumnCount(); i++) {
			columnList.add(rsmd.getColumnLabel(i + 1));
		}
		return columnList;
	}
	
	/**
	 * 基于对象的查询
	 * @param sql：查询语句
	 * @param params：参数列表
	 * @param c：要注入的对象
	 * @return ：返回查询结果 存入list
	 */
	public <T> List<T> find(String sql, List<String> params, Class<T> c) {
		List<T> list = new ArrayList<T>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = this.getCon();
			pstmt = con.prepareStatement(sql);
			this.setParams(params, pstmt);
			rs = pstmt.executeQuery();

			// 将结果集中的每一条记录注入到一个对象中
			// 1.获取结果集中的每个列的名称，并将其存放在一个数组中
			List<String> cols = this.getColumnName(rs);
			
			//2.获取给定类中的所有setter方法
			Method[] methods = c.getMethods();

			T t = null;
			String cname = null;
			String mname = null;
			String ctypeName = null;
			while ( rs.next() ) {
				t = c.newInstance(); // 创建反射类的实例化对象 //Classes t = new Classes();

				for (int i = 0, len = cols.size(); i < len; i++) {
					cname = cols.get(i);// CID 
												
					// 从类的方法中找对应的setter方法
					if (methods != null && methods.length > 0) {
						for (Method method : methods) {
							mname = method.getName();// 获取当前循环的这个方法的方法名

							// 比较当前的方法名是否跟我处理后的列名相同
							// 如果找到了对应的setter方法，则激活这个方法，将这个列的值注入进去
							if ( ("set"+cname).equalsIgnoreCase(mname)
									&& rs.getObject(cname) != null ) {
								//3.获取即将要注入的这个列的列类型
								
								ctypeName = rs.getObject( cols.get(i) )
										.getClass().getSimpleName();
								//4.实例化一个这样的对象，并通过反射将这个列的值 注入到对象对应的属性中
								if( "String".equals(ctypeName) ){
									method.invoke(t, rs.getString(cname) );
								}else if( "BigDecimal".equals(ctypeName) ){
									try {
										method.invoke(t, rs.getInt(cname) );
									} catch (Exception e) {
										method.invoke(t, rs.getDouble(cname));
									}
								}else{
									method.invoke(t, rs.getString(cname));
								}
								break;
							}
						}
					}
				}
				//每循环一次就是一条记录，即对应一个对象
				list.add(t);	
			}
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeAll(pstmt, con);
		}
		
		
		
		return list;
	}

}

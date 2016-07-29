package com.yc.student.utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

public class UploadUtil {
	private static final String PATH = "upload";
	private static final String ALLOWED = "gif,jpg,png,txt,doc,xls"; //文件上传的文件类型
	private static final String DENIDE = "exe,bat,jsp,html"; //不允许上传的文件类型
	private static final int SINGLEFILESIZE = 2*1024*1024; //单个文件最大大小
	private static final int TOTALMAXSIZE = 20*1024*1024; //每次上传的总大小
	
	public Map<String,String> upload(PageContext pageContext){
		Map<String,String> map = new HashMap<String, String>();
		SmartUpload upload = new SmartUpload();
		
		try {
			upload.initialize(pageContext);
			upload.setDeniedFilesList(DENIDE);
			upload.setAllowedFilesList(ALLOWED);
			upload.setMaxFileSize(SINGLEFILESIZE);
			upload.setTotalMaxFileSize(TOTALMAXSIZE);
			upload.setCharset("utf-8");
			
			//开始上传
			upload.upload();
			
			Request request = upload.getRequest();
			
			//取出所有的普通表单元素，并将其存入到map中
			Enumeration<String> names = request.getParameterNames();
			
			String str = null;
			while( names.hasMoreElements()){
				str=names.nextElement();
				map.put(str, request.getParameter(str));
			}
			
			//获取所有要上传的文件
			Files files = upload.getFiles();
			
			//说明确实有文件要上传
			if(files != null && files.getCount() > 0){
				Collection<File> cols = files.getCollection();
				String fname = null;
				for( File file : cols){
					if( !file.isMissing() ){//如果上传的时候没有丢失数据
						fname = new Date().getTime()+""+new Random().nextInt(1000)+"."+file.getFileExt();
						file.saveAs(PATH+"/"+fname);
						map.put(file.getFieldName(), PATH+"/"+fname);
					}
				}
			}
		} catch (ServletException | IOException | SQLException
				| SmartUploadException e) {
			e.printStackTrace();
		}
		
		
		return map;
	}
}
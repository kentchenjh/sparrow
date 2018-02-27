package com.sparrow.mvc.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sparrow.Const;
import com.sparrow.kit.FileKit;
import com.sparrow.mvc.http.MimeType;

public class DefaultStaticsHandler implements StaticsHandler{

	@Override
	public void hanlde(String path, HttpServletRequest request, HttpServletResponse response) {
		
		File file = new File(Const.CLASS_PATH, path);
		
		if(file.isDirectory() || !file.exists()) return;
		
		response.setContentType(MimeType.get(FileKit.getSuffix(file.getName())));
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(file);
			os = response.getOutputStream();
			
			byte[] bytes = new byte[2048];
			int pos;
			while((pos = is.read(bytes)) != -1) {
				os.write(bytes);
			}
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}

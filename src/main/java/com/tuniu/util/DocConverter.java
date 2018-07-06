package com.tuniu.util;

import java.io.File;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class DocConverter {
	
	private String fileName;
	private File pdfFile;
	private File docFile;
	
	public DocConverter(String filePath){
		wait(filePath);
	}

	private void wait(String filePath) {
		fileName = filePath.substring(0, filePath.lastIndexOf("."));
		docFile = new File(filePath);
		pdfFile = new File(fileName+".pdf");
	}
	
	/**
	 * soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard 
	 * 启动文档转换服务 openoffice
	 * @throws Exception
	 */
	private void doc2pdf() throws Exception{
		if(docFile.exists()){
			if(!pdfFile.exists()){
				OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
				connection.connect();
				DocumentConverter converter=new OpenOfficeDocumentConverter(connection);
				converter.convert(docFile, pdfFile);
				connection.disconnect();
				System.out.println("pdf转换成功,pdf输出:"+ pdfFile.getPath());
			}else{
				System.out.println("文档已经存在,不能进行转换");
			}
		}else{
			System.out.println("文档不存在,无法转换");
		}
	}
	
	public boolean convert(){
		try {
			doc2pdf();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}

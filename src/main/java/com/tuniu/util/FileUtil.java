package com.tuniu.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.tuniu.common.util.Constant;

public class FileUtil {
	
	//判断文件扩展名,可否转换为pdf
	public static boolean checkExtension(String fileString){
		String fileExtension = fileString.substring(fileString.lastIndexOf(".")+1).toLowerCase();
		if(Constant.FILE_EXTENSION.indexOf(fileExtension) != -1)
			return true;
		return false;
	}
	
	//获取文件名
	public static String getFileName(String fileString){
		return fileString.substring(0, fileString.lastIndexOf(".")).toLowerCase();
	}
	
	//获取文件名扩展名
	public static String getFileExt(String fileString){
		return fileString.substring(fileString.lastIndexOf(".")+1).toLowerCase();
	}
	
	/**
	 * 下载文件
	 * @throws IOException 
	 */
	public static ResponseEntity<byte[]> download(String filename,File file) throws IOException{
		String newname=new String(filename.getBytes("gbk"),"iso8859-1");//中文乱码
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", newname);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers,HttpStatus.CREATED);
	}
	
	/**
	 * 删除文件或文件夹
	 */
	public static boolean delete(String fileName){
		File file = new File(fileName);
		if(!file.exists()){
			System.out.println("删除文件失败:"+ fileName + "不存在!");
			return false;
		}else{
			if(file.isFile())
				return deleteFile(fileName);
			else
				return deleteDir(fileName);
		}	
	}
	
	public static boolean isExist(String fileName){
		File file = new File(fileName);
		return file.exists();
	}
	
	public static boolean deleteDir(String fileName) {
		return false;
	}

	public static boolean deleteFile(String fileName){
		File file=new File(fileName);
		if(file.exists()&&file.isFile()){
			if(file.delete()){
				System.out.println("删除单个文件:"+ fileName + "成功!");
				return true;
			}else{
				System.out.println("删除单个文件:"+ fileName + "失败!");
				return false;
			}
		}else{
			System.out.println("删除单个文件失败:"+ fileName + "不存在!");
			return false;
		}
	}

}

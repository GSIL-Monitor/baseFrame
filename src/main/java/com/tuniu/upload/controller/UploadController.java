package com.tuniu.upload.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.tuniu.common.dto.BaseDataDto;
import com.tuniu.common.model.BaseData;
import com.tuniu.common.service.BaseDataService;
import com.tuniu.common.service.ResourceService;
import com.tuniu.common.util.Constant;
import com.tuniu.upload.dto.AccordionDto;
import com.tuniu.upload.dto.UploadStatus;
import com.tuniu.upload.model.Upload;
import com.tuniu.upload.service.UploadService;
import com.tuniu.util.FileUtil;

@Controller
@RequestMapping("/upload")
public class UploadController {
	
	@Value("${file.upload.directory}")
	private String directory;
	
	@Autowired
	private UploadService service;
	
	@Autowired
	private ResourceService resService;
	
	@Autowired
	private BaseDataService bdService;
	
	//@Value("#{configProperties['file.upload.directory']}")
	//private String directory;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		return "upload/index";
	}
	
	@RequestMapping("/fileManagement")
	public String fileManagement(Model model){
		List<AccordionDto> categories = resService.getAllCategoryList();
		BaseDataDto dto = new BaseDataDto();
		dto.setKey(Constant.FILE_TYPE);
		dto.setDelFlag(Constant.NO);
		List<BaseData> baseDataList = bdService.list(dto);
		model.addAttribute("categories", categories);
		model.addAttribute("bdList", resService.parse(baseDataList));
		return "upload/fileManagement";
	}

	@RequestMapping("/fileUpload")	
	public String fileUpload(@RequestParam("file")CommonsMultipartFile file)throws IOException{
		System.out.println(file.getName()+","+file.getOriginalFilename()+","+
				file.getContentType()+","+file.getStorageDescription()+","+
				file.getSize());
		long startTime=System.currentTimeMillis();
		System.out.println("fileName:"+file.getOriginalFilename()+"上传目录:"+directory);
		String path = directory+new Date().getTime()+file.getOriginalFilename();
		OutputStream os=new FileOutputStream(path);
		InputStream is=file.getInputStream();
		byte[] buff=new byte[1024];
		while(is.read(buff)!=-1){
			os.write(buff);
		}
		os.flush();
		os.close();
		is.close();
		long endTime=System.currentTimeMillis();
		System.out.println("运行时间:"+String.valueOf(endTime-startTime)+"ms");
		//保存
		Upload upload=new Upload();
		upload.setFileName(file.getOriginalFilename());
		upload.setFileType(file.getContentType());
		upload.setFileSize(file.getSize());
		upload.setUrl(path);
		service.add(upload);
		return "upload/success";
	}
	
	@RequestMapping("fileUpload2")
	public String fileUpload2(@RequestParam("file")CommonsMultipartFile file)throws IOException{
		long startTime=System.currentTimeMillis();
		System.out.println("fileName:"+file.getOriginalFilename());
		String path="d:/upload/"+new Date().getTime()+file.getOriginalFilename();
		File newFile=new File(path);
		file.transferTo(newFile);
		long endTime=System.currentTimeMillis();
		System.out.println("运行时间:"+String.valueOf(endTime-startTime)+"ms");
		return "upload/success";
	}
	
	@RequestMapping("/springUpload")
	public String springUpload(HttpServletRequest request) throws IllegalStateException,IOException{
		long startTime=System.currentTimeMillis();
		CommonsMultipartResolver resolver=new CommonsMultipartResolver(request.getSession().getServletContext());
		if(resolver.isMultipart(request)){
			MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
			Iterator<String> iter=multipartRequest.getFileNames();
			while(iter.hasNext()){
				MultipartFile file=multipartRequest.getFile(iter.next().toString());
				
				if(file!=null){
					String path=directory+new Date().getTime()+file.getOriginalFilename();
					file.transferTo(new File(path));
					System.out.println(file.getName()+","+file.getOriginalFilename()+","+
							file.getContentType()+","+
							file.getSize());
				}
			}
		}
		long endTime=System.currentTimeMillis();
		System.out.println("运行时间:"+String.valueOf(endTime-startTime)+"ms");
		return "upload/success";
	}
	
	@RequestMapping("/indexMulti")
	public String indexMulti(){
		return "upload/indexMulti";
	}
	
	@RequestMapping("/indexAjax")
	public String indexAjax(){
		return "upload/ajax";
	}
	
	@RequestMapping("/multi")
	@ResponseBody
	public String multi(@RequestParam(value="file",required=false) MultipartFile[] files,HttpServletRequest request) throws IOException{
		long startTime=System.currentTimeMillis();
		String fileSavePath = request.getParameter("filelSavePath");
		File pathFile = new File(directory+ fileSavePath);
		if(!pathFile.exists() && !pathFile.isDirectory()){
			pathFile.mkdirs();
		}
		if(files!=null && files.length>0){
			for(int i=0;i<files.length;i++){
				MultipartFile file=files[i];
				//效率慢
				//FileUtils.writeByteArrayToFile(new File(""), file.getBytes());
				file.transferTo(new File(pathFile + "\\" + new Date().getTime()+file.getOriginalFilename()));
			}
		}
		long endTime=System.currentTimeMillis();
		System.out.println("进度条上传运行时间:"+String.valueOf(endTime-startTime)+"ms");
		return "success";		
	}
	
	@RequestMapping("/getStatus")
	@ResponseBody
	public UploadStatus getStatus(HttpSession session){
		UploadStatus status = (UploadStatus)session.getAttribute("upload_status");
		System.out.println(status);
		return status;
	}
	
	@RequestMapping("/download")
	public ResponseEntity<byte[]> download() throws IOException{
		String filename="基础框架_file_示例.war";
		File file = new File("D:\\apache-tomcat-8.5.8\\webapps\\baseFrame.war");
		return FileUtil.download(filename, file);
	}
	
	@RequestMapping(value="download2",method=RequestMethod.GET)
	public void download2(HttpServletResponse response,@RequestHeader String referer) throws IOException{
		System.out.println(referer);
		String filename="基础框架_file_示例.war";
		String newname=new String(filename.getBytes("gbk"),"iso8859-1");//中文乱码
		File file = new File("D:\\apache-tomcat-8.5.8\\webapps\\baseFrame.war");
		if(file.exists()){
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "form-data; name=\"attachment\"; filename=" + newname);
			FileInputStream fis=new FileInputStream(file);
			byte[] buff=new byte[fis.available()];
			fis.read(buff);
			OutputStream os=response.getOutputStream();
			os.write(buff);
			fis.close();
			os.close();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

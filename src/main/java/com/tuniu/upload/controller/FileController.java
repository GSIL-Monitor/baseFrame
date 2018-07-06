package com.tuniu.upload.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tuniu.common.model.Category;
import com.tuniu.common.service.ResourceService;
import com.tuniu.upload.dto.UploadDto;
import com.tuniu.upload.dto.UploadStatus;
import com.tuniu.upload.model.Upload;
import com.tuniu.upload.service.UploadService;
import com.tuniu.util.DocConverter;
import com.tuniu.util.FileUtil;

@RequestMapping("/file")
@Controller
public class FileController {
	
	@Autowired
	private UploadService service;
	
	@Autowired
	private ResourceService resService;
	
	@Value("${file.upload.directory}")
	private String directory;
	
	@RequestMapping("index/{typeId}/{cid}")
	public String index(@PathVariable("typeId")Integer typeId,@PathVariable("cid")Integer cid,UploadDto dto,Model model){
		dto.setTypeId(typeId);
		dto.setCategoryId(cid);
		service.loadPage(dto);
		model.addAttribute("dto",dto);
		//获取全路径
		Category category = resService.getCategory(typeId, cid);
		System.out.println(resService.getFullPath(category));
		model.addAttribute("title", resService.getFullPath(category));
		model.addAttribute("url", typeId + "/" + cid);
		model.addAttribute("typeId", typeId);
		model.addAttribute("cid", cid);
		return "upload/file/index";
	}
	
	@RequestMapping("addUI/{typeId}/{cid}")
	public String addUI(@PathVariable("typeId")Integer typeId,@PathVariable("cid")Integer cid,Model model){
		Upload upload = getUplaod(typeId,cid);
		model.addAttribute("upload", upload);
		return "upload/file/addUI";
	}
	
	@RequestMapping("/upload")
	@ResponseBody
	public String upload(@RequestParam(value="file",required=true)MultipartFile[] files,HttpServletRequest request) throws IllegalStateException, IOException{
		int typeId = Integer.parseInt(request.getParameter("typeId"));
		int cid =  Integer.parseInt(request.getParameter("cid"));
		//获取全路径
		Category category = resService.getCategory(typeId, cid);
		String[] arr = resService.getFullPath(category).split(" - ");
		String dirPath = "";
		for(String dir : arr){
			dirPath += dir + "/";
		}
		File pathFile = new File(directory + dirPath);
		if(!pathFile.exists() && !pathFile.isDirectory()){
			pathFile.mkdirs();
		}
		String fileName = "";
		if(files!=null && files.length>0){
			for(int i=0;i<files.length;i++){
				MultipartFile file = files[i];
				fileName = pathFile +"\\"+new Date().getTime() + "-" + file.getOriginalFilename();
				file.transferTo(new File(fileName));
				
				//判断文件扩展名,可否转换为pdf
				if(FileUtil.checkExtension(file.getOriginalFilename())){
					DocConverter converter = new DocConverter(fileName);
					converter.convert();
				}
				
				//保存
				Upload upload=new Upload();
				upload.setCategoryId(cid);
				upload.setTypeId(typeId);
				upload.setFileName(file.getOriginalFilename());
				upload.setFileType(file.getContentType());
				upload.setFileSize(file.getSize());
				upload.setUrl(fileName);
				service.add(upload);
			}
		}
		
		return "success";
	}
	
	@RequestMapping("/byte/{id}")
	@ResponseBody
	public ResponseEntity<byte[]> load(@PathVariable Integer id) throws IOException{
		Upload upload = service.get(id);	
		String fileName = upload.getUrl();
		String pdfName = fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf";
		System.out.println(pdfName);
		File file = new File(pdfName);
		FileInputStream fis=new FileInputStream(file);
		byte[] buff=new byte[fis.available()];
		fis.read(buff);
		fis.close();
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/pdf;charset=UTF-8"));
		headers.setContentLength(buff.length);
		//headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");
		ResponseEntity<byte[]> resp = new ResponseEntity<byte[]>(buff,headers,HttpStatus.OK);
		return resp;
		
	}
	
	@RequestMapping("/stream/{id}")
	public void loadPdf(@PathVariable Integer id,HttpServletResponse response) throws IOException{
		Upload upload = service.get(id);	
		String fileName = upload.getUrl();
		String pdfName = fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf";
		File file = new File(pdfName);
		FileInputStream fis = new FileInputStream(file);
		response.setHeader("Content-Disposition", "attachment;fileName=online.pdf");
		response.setContentType("multipart/form-data");
		OutputStream os = response.getOutputStream();
		IOUtils.write(IOUtils.toByteArray(fis), os);
		fis.close();
		os.close();
	}
	
	@RequestMapping("/getStatus")
	@ResponseBody
	public UploadStatus getStatus(HttpSession session){
		UploadStatus status = (UploadStatus)session.getAttribute("upload_status");
		System.out.println(status);
		return status;
	}

	private Upload getUplaod(Integer typeId, Integer cid) {
		Upload upload = new Upload();
		upload.setCategoryId(cid);
		upload.setTypeId(typeId);
		return upload;
	}
	
	@RequestMapping("/download/{id}")
	public ResponseEntity<byte[]> download(@PathVariable Integer id) throws IOException{
		Upload upload = service.get(id);
		File file = new File(upload.getUrl());
		return FileUtil.download(upload.getFileName(), file);
	}
	
	@RequestMapping(value="download2/{id}",method=RequestMethod.GET)
	public void download2(@PathVariable Integer id,HttpServletResponse response,@RequestHeader String referer) throws IOException{
		System.out.println(referer);
		Upload upload = service.get(id);
		String filename=upload.getFileName();
		String newname=new String(filename.getBytes("utf-8"),"iso8859-1");//中文乱码
		File file = new File(upload.getUrl());
		if(file.exists()){
//			response.setContentType("application/octet-stream");
			response.setContentType(upload.getFileType()+";charset=utf-8");
//			response.addHeader("Content-Disposition", "form-data; name=\"attachment\"; filename=" + newname);
			response.addHeader("Content-Disposition", "attachment ; filename=\"" + newname+"\"");
			FileInputStream fis=new FileInputStream(file);
			byte[] buff=new byte[fis.available()];
			fis.read(buff);
			OutputStream os=response.getOutputStream();
			os.write(buff);
			fis.close();
			os.close();
		}
	}
	
	@RequestMapping("delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id){
		Upload upload = service.get(id);
		String fileName = upload.getUrl();
		//删除数据库记录
		service.delete(id);
		//删除磁盘物理原文件
		String pdfName = FileUtil.getFileName(fileName)+".pdf";
		if(FileUtil.isExist(fileName)&& FileUtil.delete(fileName) ){
			if(FileUtil.isExist(pdfName))
				FileUtil.delete(pdfName);
			return "success";
		}
		else
			return "failure";
	}
}

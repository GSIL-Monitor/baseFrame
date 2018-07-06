package com.tuniu.upload.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.upload.dao.UploadDao;
import com.tuniu.upload.dto.UploadDto;
import com.tuniu.upload.model.Upload;
import com.tuniu.upload.service.UploadService;
import com.tuniu.util.FileUtil;
@Service
public class UploadServiceImpl implements UploadService {
	
	@Autowired
	private UploadDao mapper;

	@Override
	public void add(Upload obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(Upload obj) {

	}

	@Override
	public Upload get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<Upload> list(UploadDto dto) {
		List<Upload> list = mapper.list(dto);
		for(Upload upload : list){
			//设置在线阅读标识
			String fileName = upload.getUrl();
			if(FileUtil.checkExtension(fileName) || "pdf".equals(FileUtil.getFileExt(fileName))){
				String pdfName = fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf";	
				File file = new File(pdfName);
				if(file.exists())
					upload.setOnlineRead(true);
			}
			
			long size = upload.getFileSize();
			upload.setFileSizeWithUnit(convertFileSize(size));
		}
		return list;
	}
	
	
	private String convertFileSize(long size) {  
	       long kb = 1024;  
	       long mb = kb * 1024;  
	       long gb = mb * 1024;  
	       //%.2f 保留两位小数的浮点数
	       if (size >= gb) {  
	           return String.format("%.2f GB", (float) size / gb);  
	       } else if (size >= mb) {  
	           float f = (float) size / mb;  
	           //如果大于100MB就不用保留小数位
	           return String.format(f > 100 ? "%.0f MB" : "%.2f MB", f);  
	       } else if (size >= kb) {  
	           float f = (float) size / kb;  
	           //如果大于100kB就不用保留小数位 
	           return String.format(f > 100 ? "%.0f KB" : "%.2f KB", f);  
	       } else  
	           return String.format("%d B", size);  
	   }  

	@Override
	public void loadPage(UploadDto dto) {
		int totalRecords = mapper.count(dto);
		dto.setDataList(list(dto));
		dto.setTotalRecords(totalRecords);
	}

}

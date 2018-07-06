package com.tuniu.upload.listener;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class UploadCommonsMultipartResolver extends CommonsMultipartResolver {

	@SuppressWarnings("unchecked")
	@Override
	protected MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
		HttpSession session = request.getSession();
		String encoding="utf-8";
		FileUpload upload=prepareFileUpload(encoding);
		UploadListener listener=new UploadListener(session);
		upload.setProgressListener(listener);
		
		try {
			List<FileItem> fileItem = ((ServletFileUpload)upload).parseRequest(request);
			return parseFileItems(fileItem, encoding);
		}catch (SizeLimitExceededException e) {
			throw new MaxUploadSizeExceededException(upload.getSizeMax(),e);
		}catch (FileUploadException e) {
			throw new MultipartException("Could not parse multipart servlet request",e);
		}
		
	}
}

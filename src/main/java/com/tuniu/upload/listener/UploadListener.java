package com.tuniu.upload.listener;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

import com.tuniu.upload.dto.UploadStatus;

public class UploadListener implements ProgressListener {

	private HttpSession session;
	
	
	public UploadListener(HttpSession session) {
		super();
		this.session = session;
		UploadStatus status=new UploadStatus();
		session.setAttribute("upload_status", status);
	}


	@Override
	public void update(long pBytesRead, long pContentLength, int pItems) {
		UploadStatus status=(UploadStatus) session.getAttribute("upload_status");
		status.setBytesRead(pBytesRead);
		status.setContentLength(pContentLength);
		status.setItems(pItems);
		status.setUseTime(System.currentTimeMillis()-status.getStartTime());
		status.setPercent((int)(100*pBytesRead/pContentLength));
		session.setAttribute("upload_status", status);
	}

}

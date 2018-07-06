package com.tuniu.upload.model;

import java.util.Date;

public class Upload {
	
	private int id;
	private Integer typeId;
	private Integer categoryId;
	private String fileName;
	private String fileType;
	private long fileSize;
	private String fileSizeWithUnit;
	private String url;
	private Date uploadTime;
	private boolean onlineRead;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileSizeWithUnit() {
		return fileSizeWithUnit;
	}
	public void setFileSizeWithUnit(String fileSizeWithUnit) {
		this.fileSizeWithUnit = fileSizeWithUnit;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	
	public boolean isOnlineRead() {
		return onlineRead;
	}
	public void setOnlineRead(boolean onlineRead) {
		this.onlineRead = onlineRead;
	}
	
	@Override
	public String toString() {
		return "Upload [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", fileSize=" + fileSize
				+ ", url=" + url + ", uploadTime=" + uploadTime + "]";
	}
	
	

}

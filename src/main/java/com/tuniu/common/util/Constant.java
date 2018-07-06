package com.tuniu.common.util;


public class Constant {
	
	public static final String ENCODING = "utf-8";
	
	/** 否 */
	public static final int NO = 0;
	
	/** 是 */
	public static final int YES = 1;
	
	/** 基础员工 */
	public static final int ROLE_EMPLOYEE = 1;
	
	/** 经理 */
	public static final int ROLE_MANAGER = 2;
	
	/** 管理员 */
	public static final int ROLE_ADMINISTRATOR = 3;
	
	/** 查询操作 */
	public static final int R_OPERATE = 0;
	
	/** 增删改操作 */
	public static final int CUD_OPERATE = 1;
	
	public static final String SYS_SUCCESS = "0"; // 成功
	
	public static final String SYS_FAILED = "1"; // 失败
	
	public static final String SYS_WARNING = "2"; // 警告
	
	public static final String DEFAULT_PASSWORD = "123456";//默认密码
	
	public static final String DEFAULT_SALT = "admin";//初始密钥盐
	
	public static final Integer HASH_TIMES = 1 ;//密码散列次数
	
//	public static final String UPLOAD_IMAGES_PATH = "D:\\apache-tomcat8080-8.0.29\\webapps\\upload\\images\\";//上传图片位置
//	public static final String UPLOAD_IMAGES_PATH = "D:\\upload\\images\\";//上传图片位置
	
	public static final String LOG_FILE= "D:\\logs\\baseFrame-log.log";
	
	public static final int DELAY = 0;
	public static final int INTERVAL = 2;
	
	public static final String LEAVE_TYPE="leave_type";
	public static final String FILE_TYPE="file_type";
	public static final String CMS_TYPE="cms_type";
	//public static final Integer RELMENUID = 64;//联动tabs的ID,作用：点击申请请假后，联动打开'我的业务'页签，
	public static final String RESOURCE_MENU_TYPE="menu";
	public static final String FILE_EXTENSION="doc,docx,xls,xlsx,ppt,pptx,txt,rtf,htm,html";
}
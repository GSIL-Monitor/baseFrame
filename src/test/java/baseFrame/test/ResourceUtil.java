package baseFrame.test;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class ResourceUtil {
	public static final String LANGUAGE = "zh";// 系统默认语言'中文'
	public static final String COUNTRY = "CN";// 系统国家环境'中国'

	private static Locale getLocale() {
		Locale locale = new Locale(LANGUAGE, COUNTRY);
		return locale;
	}
	
	/**
	 * 根据语言、国家、资源文件名和key名字获取对应的值
	 * @param language语言
	 * @param country国家
	 * @param fileName classpath:资源文件名
	 * @param key名字
	 * @return 值
	 */
	public static String getValue(String fileName,String key){
		Locale locale = getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle(fileName, locale);
		return bundle.getString(key);
	}
	
	//获取fileName资源文件中所有的key
	public static List<String> getKeyList(String fileName){
		Locale locale = getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle(fileName, locale);
		List<String> resList = new ArrayList<>();
		Set<String> keyList = bundle.keySet();
		for(String key : keyList){
			resList.add(key);
		}
		return resList;
	}
	
	/**
	 * 通过key从资源文件读取内容，并格式化
	 * @param fileName 资源文件名
	 * @param key 索引
	 * @param args 格式化参数
	 * @return 格式化后的内容
	 */
	public static String getValue(String fileName,String key,Object[] args){
		String pattern = getValue(fileName, key);
		return MessageFormat.format(pattern, args);
	}
	
	public static void main(String[] args){
		System.out.println(getValue("resources.messages", "101",new Object[]{100,200}));
		//根据操作系统环境获取语言环境
		Locale locale = Locale.getDefault();
		System.out.println(locale.getCountry());//输出国家代码
		System.out.println(locale.getLanguage());//输出语言代码s
		
		//加载国际化资源（classpath下resources目录下的messages.properties，如果是中文环境会优先找messages_zh_CN.properties）
		//ResourceBundle rb = ResourceBundle.getBundle("resources.messages", locale);
		//String retValue = rb.getString("101");//101是messages.properties文件中的key
		String retValue = ResourceUtil.getValue("resources.messages", "101");
		System.out.println(retValue);
		
		//信息格式化，如果资源中有{}的参数则需要使用MessageFormat格式化，Object[]为传递的参数，数量根据资源文件中的{}个数决定
		//String value = MessageFormat.format(retValue, new Object[]{100,200});
		String value = ResourceUtil.getValue("resources.messages", "101", new Object[]{100,200});
		System.out.println(value);
		
	}
}

package string;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class StringCode {
	
	@Test
	public void testCode(){
		String filename = "安全开发培训.pptx";
		try {
			String newname=new String(filename.getBytes("gbk"),"iso8859-1");
			System.out.println(newname);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//中文乱码
	}

}

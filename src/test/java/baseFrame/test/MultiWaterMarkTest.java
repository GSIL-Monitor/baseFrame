package baseFrame.test;

import java.io.File;

import org.junit.Test;

public class MultiWaterMarkTest {
	
	@Test
	public void test() throws Exception{
		MultiWaterMark mark = new MultiWaterMark();
		
		File image = new File("D:\\indigo\\baseFrame\\src\\test\\java\\baseFrame\\test\\img\\123.jpg");
		String path =mark.watermark(image, "123.jpg", "img", "D:\\indigo\\baseFrame\\src\\test\\java\\baseFrame\\test\\img");
		System.out.println(path);
	}

}

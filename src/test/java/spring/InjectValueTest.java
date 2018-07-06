package spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class InjectValueTest {
	
	@Value("#{configProperties['test.value']}")
	private String value;
	
	@Value("#{configProperties['constant']}")
	private String constant;
	
	@Value("${test.value}")
	private String value1;
	
	@Value("${file.upload.directory}")
	private String constant1;

	@Test
	public void test() {
		System.out.println(value);
		System.out.println(constant);
		System.out.println("${test.value}:"+value1);
		System.out.println("${file.upload.directory}:"+constant1);
	}

}

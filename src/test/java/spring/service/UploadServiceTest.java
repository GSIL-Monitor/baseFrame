package spring.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tuniu.upload.dto.UploadDto;
import com.tuniu.upload.model.Upload;
import com.tuniu.upload.service.UploadService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml","classpath:spring-mybatis.xml" })
public class UploadServiceTest {
	
	@Autowired
	private UploadService service;
	
	@Test
	public void testList(){
		UploadDto dto=new UploadDto();
		List<Upload> list = service.list(dto);
		for(Upload upload : list){
			System.out.println(upload);
		}
	}

}

package activiti;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-activiti.xml","classpath:spring-mybatis.xml"})
@TransactionConfiguration(transactionManager="txManager",defaultRollback=false)
@Transactional
public class BaseTestCase extends AbstractTransactionalJUnit4SpringContextTests {

	@Test
	public void test() {
		System.out.println("test");
	}

}

package baseFrame.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ListTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testList() {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("4");
		list.add("2");
		list.add("8");
		System.out.println(list.toString());
	}

}

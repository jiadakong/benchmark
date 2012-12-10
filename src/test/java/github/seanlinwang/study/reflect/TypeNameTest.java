package github.seanlinwang.study.reflect;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TypeNameTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		System.out.println(new String[2].getClass().getCanonicalName());
		System.out.println(new String[2].getClass().getName());
	}

}

package github.seanlinwang.study.primary;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PrimaryTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws ClassNotFoundException {
		System.out.println(Integer.TYPE == int.class);
		System.out.println(Integer.TYPE);
		System.out.println(Long.TYPE);
		System.out.println(Short.TYPE);
		System.out.println(Double.TYPE);
		System.out.println(Boolean.TYPE);
		System.out.println(Float.TYPE);
		System.out.println(Byte.TYPE);
		System.out.println(Character.TYPE);
		System.out.println(Void.TYPE);
	}
	
	@Test
	public void testClassForName() throws ClassNotFoundException {
		Class.forName((int[].class.getName()));
		System.out.println(int[].class.getName());
		System.out.println(int[].class.getCanonicalName());
		System.out.println(String.class.getName());
	}

}

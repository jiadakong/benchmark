package github.seanlinwang.study.primary;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OverflowTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws InterruptedException {
		for(int i = 0; i < 10000; i++) {
		System.out.println(1000L * 60 * 60 * 24);
		Thread.sleep(100);
		}
	}

}

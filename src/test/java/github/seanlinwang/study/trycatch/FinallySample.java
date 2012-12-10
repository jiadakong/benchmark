package github.seanlinwang.study.trycatch;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FinallySample {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		label: for (int i = 0; i < 10; i++) {
			try {
				if (i % 2 == 0) {
					continue label;
				}
			} finally {
				System.out.println(i);
			}
		}
	}

}

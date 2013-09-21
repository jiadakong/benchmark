package github.seanlinwang.study.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RegexDateTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String result = "1953年江苏寄杭州普4型（另收成本100元）邮资片一件、销11月18日江苏戳、杭州落戳12:09:09";
		String regex = "[0-9]{2}:[0-9]{2}:[0-9]{2}"; // 提取 xx:xx:xx 也就是时间
		Pattern pattern = Pattern.compile(regex);
		String input = result.trim();
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			System.out.println(matcher.group(0));
		}
	}

}

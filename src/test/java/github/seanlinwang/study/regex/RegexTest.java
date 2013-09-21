package github.seanlinwang.study.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Test;

public class RegexTest {

	@Test
	public void testMatchSingleWord() {
		Pattern p = Pattern.compile("Kevin");
		Matcher m = p.matcher("Kevin");
		Assert.assertTrue(m.matches());
	}

	@Test
	public void testMatchSomeWord() {
		Pattern p = Pattern.compile(".*代.*戳.*");
		Matcher m = p.matcher("50年代上海印刷品寄捷克斯洛伐克封一件、贴纪8（3-1）原版一枚、改票二枚、销3月24日上海戳");
		Assert.assertTrue(m.matches());
	}

	@Test
	public void testOr() {
		Pattern p = Pattern.compile(".*air[ ]+.*(cover|mail).*");
		Matcher m = p.matcher("China air mail cover to Sweden 1966");
		Assert.assertTrue(m.matches());
	}

	@Test
	public void testMatchSomeWordBlank() {
		Pattern p = Pattern.compile(".*代[ ]+戳.*");
		Matcher m = p.matcher("50年代    戳77");
		Assert.assertTrue(m.matches());

		m = p.matcher("50年代戳77");
		Assert.assertFalse(m.matches());
	}

	@Test
	public void testMatchSomeWordNoSequence() {
		Pattern p = Pattern.compile(".*(?=.*?代)(?=.*?(戳|封|片|简)).*");
		Matcher m = p.matcher("50年代戳77");
		Assert.assertTrue(m.matches());

		m = p.matcher("50年戳77代");
		Assert.assertTrue(m.matches());

		m = p.matcher("戳代");
		Assert.assertTrue(m.matches());

		m = p.matcher("代戳");
		Assert.assertTrue(m.matches());

		m = p.matcher("代片");
		Assert.assertTrue(m.matches());

		m = p.matcher("50年77代");
		Assert.assertFalse(m.matches());
	}

	@Test
	public void testMatchSomeWordNoSequence2() {
		Pattern p = Pattern.compile(".*(?=.*代).*(?=.*戳).*");
		Matcher m = p.matcher("50年代戳77");
		Assert.assertTrue(m.matches());

		m = p.matcher("50年戳代");
		Assert.assertTrue(m.matches());

		m = p.matcher("50年戳77");
		Assert.assertFalse(m.matches());

	}

	@Test
	public void testMatchNotIncluded() {
		Pattern p = Pattern.compile(".*普(9|九)型((?!美术).)*封.*");
		String title = "北京火车戳普9型x美术封1958年双戳清";
		Matcher m = p.matcher(title);
		Assert.assertFalse(m.matches());

		title = "北京火车戳普9型2美术2封1958年双戳清";
		m = p.matcher(title);
		Assert.assertFalse(m.matches());

		title = "北京火车戳普9型封1958年双戳清";
		m = p.matcher(title);
		Assert.assertTrue(m.matches());

		title = "北京火车戳普九型封1958年双戳清";
		m = p.matcher(title);
		Assert.assertTrue(m.matches());

	}

	@Test
	public void testMatchNotIncluded2() {
		Pattern p = Pattern.compile(".*纪东(1|一).*东贴((?!再版).)*");
		String title = "纪东1北京东贴寄出再版";
		Matcher m = p.matcher(title);
		Assert.assertFalse(m.matches());

		title = "纪东1北京东贴寄出";
		m = p.matcher(title);
		Assert.assertTrue(m.matches());
	}
	
	
	@Test
	public void testMatchNotIncluded3() {
		Pattern p = Pattern.compile(".*纪东(1|一)((?!再版).)*");
		String title = "纪东1北京东贴寄出再版";
		Matcher m = p.matcher(title);
		Assert.assertFalse(m.matches());

		title = "纪东1北京东贴寄出";
		m = p.matcher(title);
		Assert.assertTrue(m.matches());
	}
	

	@Test
	public void testMatch() {
		Pattern p = Pattern.compile(".*(?=.*?(第(1|一)(版|套))).*");
		String title = "第一版人民币排云殿200元菱花水印一枚";
		Matcher m = p.matcher(title);
		Assert.assertTrue(m.matches());

		title = "第一个人民币排云殿200元菱花水印一枚";
		m = p.matcher(title);
		Assert.assertFalse(m.matches());
	}

}

package github.seanlinwang.sample.httpclient;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpPost {

	/**
	 * @param args
	 * @throws IOException
	 * @throws HttpException
	 */
	public static void main(String[] args) throws HttpException, IOException {
		String url = "http://www.xiuhao.com/register";
		PostMethod postMethod = new PostMethod(url);
		// 填入各个表单域的值
		NameValuePair[] data = { new NameValuePair("username", "xalinx2222"), //
				new NameValuePair("email", "xalinx@gmail.com"), //
				new NameValuePair("password", "722222"), //
				new NameValuePair("password_again", "722222"), //
				new NameValuePair("captchaKey", "6543702d-801c-43de-9a51-016a88aa5a80"), //
				new NameValuePair("captchaAnswer", "wdyal") };
		// 将表单的值放入postMethod中
		postMethod.setRequestBody(data);
		postMethod.setRequestHeader(new Header("Accept", "application/json"));
		// 执行postMethod
		HttpClient httpClient = new HttpClient();
		int statusCode = httpClient.executeMethod(postMethod);
		System.out.println(statusCode);
		System.out.println(postMethod.getResponseBodyAsString());
	}

}

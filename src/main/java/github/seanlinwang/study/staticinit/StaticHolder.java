package github.seanlinwang.study.staticinit;

public class StaticHolder {
	private static int i = 0;
	static{
		i = 1;
		System.out.println("hold:" + i);
	}
	
	public static void test() {
		System.out.println("test");
	}
}
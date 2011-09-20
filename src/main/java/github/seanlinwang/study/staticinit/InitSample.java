/**
 * 
 */
package github.seanlinwang.study.staticinit;


class Holder {
	private int i = 0;
	{
		i = 1;
		System.out.println("hold:" + i);
	}
	
	public void test() {
		System.out.println("test");
	}
}
/**
 * @author alin
 *
 */
public class InitSample {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Holder holder = new Holder();
		System.out.println("before hold");
		Thread.sleep(5000);
		holder.test();
	}

}

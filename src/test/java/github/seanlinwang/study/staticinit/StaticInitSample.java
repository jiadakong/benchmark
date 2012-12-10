package github.seanlinwang.study.staticinit;



public class StaticInitSample {

	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("before hold");
		Thread.sleep(5000);
		StaticHolder.test();
	}

}

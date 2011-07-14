/**
 * 
 */
package github.seanlinwang.benchmark.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author alin mailto:xalinx@gmail.com
 * @date Jul 14, 2011
 */
public class HashMapMapResize {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Map<String, Integer> map = new HashMap<String, Integer>(1);
		Thread.sleep(20000);
		for (int i = 0; i < 1000000; i++) {
			map.put("" + i, i);
		}
	}

}

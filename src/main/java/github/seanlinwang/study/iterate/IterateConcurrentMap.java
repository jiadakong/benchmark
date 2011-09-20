/**
 * 
 */
package github.seanlinwang.study.iterate;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sean.wang
 * @since Aug 8, 2011
 */
public class IterateConcurrentMap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> statMsgs = new ConcurrentHashMap<String, String>();
		statMsgs.put("1", "2");
		statMsgs.put("2", "3");
		for (Entry<String, String> msgEntry : statMsgs.entrySet()) {
			String msg = statMsgs.get(msgEntry.getKey());
			statMsgs.put(msgEntry.getKey(), "");
		}

	}

}

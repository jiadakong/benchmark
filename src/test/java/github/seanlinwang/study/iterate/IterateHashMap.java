/**
 * 
 */
package github.seanlinwang.study.iterate;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author sean.wang
 * @since Aug 8, 2011
 */
public class IterateHashMap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> statMsgs = new HashMap<String, String>();
		statMsgs.put("1", "2");
		statMsgs.put("2", "3");
		for (Entry<String, String> msgEntry : statMsgs.entrySet()) {
			String msg = statMsgs.get(msgEntry.getKey());
			statMsgs.put(msgEntry.getKey(), "");
		}

	}

}

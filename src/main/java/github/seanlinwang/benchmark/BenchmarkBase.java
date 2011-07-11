/**
 * 
 */
package github.seanlinwang.benchmark;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author seanlinwang at gmail dot com
 * @date Jul 11, 2011
 * 
 */
public abstract class BenchmarkBase implements Runnable {

	public static PrintWriter createOutput(String filename) throws IOException {
		File file = new File("/Users/alin/Documents/workspace/benchmark/" + filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter outFile = new FileWriter(file);
		PrintWriter out = new PrintWriter(outFile);
		return out;
	}

	/**
	 * 
	 */
	protected abstract void deleteTestData();

}

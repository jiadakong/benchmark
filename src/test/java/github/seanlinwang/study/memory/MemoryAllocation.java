package github.seanlinwang.study.memory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

import org.junit.Test;

public class MemoryAllocation {
	

	@Test
	public void testIntMemory() {
		int[] test = new int[1000];
		for (int i = 0; i < test.length; i++) {
			test[i] = i;
		}

		Integer[] test2 = new Integer[1000];
		for (int i = 0; i < test2.length; i++) {
			test2[i] = i;
		}
		
		MemoryMXBean bean = ManagementFactory.getMemoryMXBean();
		runGC();

		long start = bean.getHeapMemoryUsage().getUsed();

		int[] boos = new int[count];
		for (int i = 0; i < boos.length; i++) {
			boos[i] = i;
		}

		long usage = bean.getHeapMemoryUsage().getUsed() - start;
		System.out.println("int:\t" + usage);
	}
	
	private static final int count = 100000;
	
	
	@Test
	public void testIntegerMemory() {

		MemoryMXBean bean = ManagementFactory.getMemoryMXBean();
		runGC();

		long start = bean.getHeapMemoryUsage().getUsed();

		Integer[] boos = new Integer[count];
		for (int i = 0; i < boos.length; i++) {
			boos[i] = i;
		}

		long usage = bean.getHeapMemoryUsage().getUsed() - start;

		System.out.println("integer:\t" + usage);
	}

	public void runGC() {
		// It helps to call Runtime.gc()
		// using several method calls
		for (int r = 0; r < 10; r++) {
			runGC0();
		}
	}

	private void runGC0() {
		long usedMem1 = usedMemory();
		long usedMem2 = Long.MAX_VALUE;

		// run finalization until no more garbage memory could be collected
		for (int i = 0; (usedMem1 < usedMem2) && (i < 500); i++) {
			Runtime.getRuntime().runFinalization();
			Runtime.getRuntime().gc();

			// allow other threads to execute
			Thread.yield();

			usedMem2 = usedMem1;
			usedMem1 = usedMemory();
		}
	}

	public long usedMemory() {
		return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}

}

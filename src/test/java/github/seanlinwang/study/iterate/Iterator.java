package github.seanlinwang.study.iterate;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class Iterator {

	/**
	 * 
	 * @param args
	 */
	@Test
	public void testListRemove1() {
		List<Integer> l = new ArrayList<Integer>();
		l.add(1);
		l.add(2);

		for (Integer i : l) {
			l.remove(i);
		}

	}

	/**
	 * 
	 * @param args
	 */
	@Test
	public void testListRemove2() {
		List<Integer> l = new ArrayList<Integer>();
		l.add(1);
		l.add(2);

		java.util.Iterator<Integer> itt = l.iterator();
		while (itt.hasNext()) {
			Integer item = itt.next();
			l.remove(item);
		}

	}
	
	public void testListAdd() {
		List<Integer> l = new ArrayList<Integer>();
		l.add(1);
		l.add(2);

		java.util.Iterator<Integer> itt = l.iterator();
		while (itt.hasNext()) {
			itt.next();
			l.add(1);
		}

	}

}

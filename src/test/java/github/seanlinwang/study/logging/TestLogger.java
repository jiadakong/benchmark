package github.seanlinwang.study.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestLogger {
	public static void main(String[] args) {
		Logger log = Logger.getLogger("lavasoft");
		log.setLevel(Level.INFO);

		Logger log2 = Logger.getLogger("lavasoft.blog");
		log2.setLevel(Level.WARNING);

		log.info("aaa");

		log2.info("bbb");// ignore
		log2.fine("fine");// ignore
		
		Logger.getLogger("lavasoft").setLevel(Level.SEVERE);
		
		log.info("aaa"); //ignore
	}
}

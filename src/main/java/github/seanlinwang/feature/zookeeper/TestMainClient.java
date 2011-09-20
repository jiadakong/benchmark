package github.seanlinwang.feature.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * TestMainClient
 * <p/>
 * Author By: junshan Created Date: 2010-9-7 14:11:44
 */
public class TestMainClient implements Watcher {
	protected  ZooKeeper zk = null;
	protected Integer mutex;
	private int sessionTimeout = 10000;
	protected String root;

	public TestMainClient(String connectString) {
		if (zk == null) {
			try {
				zk = new ZooKeeper(connectString, sessionTimeout, this);
				mutex = new Integer(-1);
			} catch (IOException e) {
				zk = null;
			}
		}
	}

	public void process(WatchedEvent event) {
		synchronized (mutex) {
			mutex.notify();
		}
	}

	public void close() {
		if (zk != null) {
			try {
				zk.close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

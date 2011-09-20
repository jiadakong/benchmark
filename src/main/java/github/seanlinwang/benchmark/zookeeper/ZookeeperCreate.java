package github.seanlinwang.benchmark.zookeeper;

import github.seanlinwang.benchmark.BenchmarkBase;
import github.seanlinwang.benchmark.util.Format;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperCreate extends BenchmarkBase {

	private static int CLIENT_PORT = 2181;
	private static int CONNECTION_TIMEOUT = 10;

	private String[] resultSet;
	private DateFormat dateFormat = Format.getDateFormat();
	private Calendar cal = Format.getCalendar();
	private PrintWriter out;

	private ZooKeeper zk;
	private String name;

	public ZookeeperCreate(PrintWriter out, ZooKeeper zk, String name, int times) {
		this.zk = zk;
		this.resultSet = new String[times];
		this.out = out;
		this.name = name;
	}

	@Override
	public void run() {
		for (int i = 0; i < resultSet.length; i++) {
			final long now = System.currentTimeMillis();
			try {
				zk.create("/" + name + "-" + i, "nodeDate".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long end = System.currentTimeMillis();
			resultSet[i] = ((end - now) + "," + end);
		}
	}

	@Override
	protected void deleteTestData() {
		for (int i = 0; i < resultSet.length; i++) {
			try {
				zk.delete("/" + name + "-" + i, -1);
			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void printResult() {
		for (String line : resultSet) {
			String[] segs = StringUtils.split(line, ',');
			out.write(segs[0]);
			out.write(",");
			this.cal.setTimeInMillis(Long.valueOf(segs[1]));
			out.write(this.dateFormat.format(cal.getTime()) + "\n");
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		PrintWriter out = BenchmarkBase.createOutput("zookeeper-create.csv");
		ZooKeeper zk = new ZooKeeper("localhost:" + CLIENT_PORT, CONNECTION_TIMEOUT, new Watcher() {
			@Override
			public void process(WatchedEvent event) {

			}
		});

		ZookeeperCreate[] senders = new ZookeeperCreate[10];
		for (int i = 0; i < senders.length; i++) {
			senders[i] = new ZookeeperCreate(out, zk, "node" + i, 10000);
		}
		Thread[] ts = new Thread[senders.length];
		for (int i = 0; i < senders.length; i++) {
			Thread t = new Thread(senders[i]);
			ts[i] = t;
			t.start();
			Thread.sleep(1);
		}

		for (int i = 0; i < ts.length; i++) {
			ts[i].join();
		}

		Thread.sleep(5000);// wait nodes created

		for (int i = 0; i < senders.length; i++) {
			ZookeeperCreate sender = senders[i];
			sender.printResult();
			sender.deleteTestData();
		}

		// 关闭连接
		zk.close();
		out.close();
	}

}

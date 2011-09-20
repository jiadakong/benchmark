package github.seanlinwang.feature.zookeeper.leaderelection;

import github.seanlinwang.feature.zookeeper.TestMainClient;

import java.net.InetAddress;
import java.net.UnknownHostException;


import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

/**
 * LeaderElection
 * <p/>
 * Author By: junshan Created Date: 2010-9-8 10:05:41
 */
public class LeaderElection extends TestMainClient {
	public static final Logger logger = Logger.getLogger(LeaderElection.class);
	private String name;

	public LeaderElection(String name, String connectString, String root) {
		super(connectString);
		this.root = root;
		this.name = name;
		if (zk != null) {
			try {
				Stat s = zk.exists(root, false);
				if (s == null) {
					zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				}
			} catch (KeeperException e) {
				logger.error(e);
			} catch (InterruptedException e) {
				logger.error(e);
			}
		}
	}

	void findLeader() throws InterruptedException, UnknownHostException, KeeperException {
		byte[] leader = null;
		org.apache.zookeeper.data.Stat stat = new Stat();
		try {
			leader = zk.getData(root + "/leader", true, stat);
		} catch (KeeperException e) {
			if (e instanceof KeeperException.NoNodeException) {
				System.out.println(this.name + ":no node");
			} else {
				throw e;
			}
		}
		if (leader != null) {
			following(stat.getCzxid() + "");
		} else {
			String newLeader = null;
			byte[] localhost = InetAddress.getLocalHost().getAddress();
			try {
				newLeader = zk.create(root + "/leader", localhost, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			} catch (KeeperException e) {
				if (e instanceof KeeperException.NodeExistsException) {
					logger.error(e);
				} else {
					throw e;
				}
			}
			if (newLeader != null) {
				leading(newLeader);
			} else {
				synchronized (mutex) {
					mutex.wait();
				}
			}
		}
	}

	@Override
	public void process(WatchedEvent event) {
		logger.error(this.name + event);
		if (event.getType() == Event.EventType.NodeCreated && event.getPath().equals(root + "/leader")) {
			System.out.println(this.name + "得到通知");
			super.process(event);
			following(event.getPath());
		} else if (event.getType() == Event.EventType.NodeDeleted && event.getPath().equals(root + "/leader")) {
			try {
				this.findLeader();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void leading(String path) {
		System.out.println(this.name + ":成为领导者:" + path);
	}

	void following(String path) {
		System.out.println(this.name + ":成为组成员:" + path);
	}

	public static void main(String[] args) throws InterruptedException {
		String connectString = "localhost:2181";

		LeaderElection le1 = new LeaderElection("member1", connectString, "/GroupMembers");
		LeaderElection le2 = new LeaderElection("member2", connectString, "/GroupMembers");
		LeaderElection le3 = new LeaderElection("member3", connectString, "/GroupMembers");
		try {
			le1.findLeader();
		} catch (Exception e) {
			logger.error(e);
		}
		try {
			le2.findLeader();
		} catch (Exception e) {
			logger.error(e);
		}
		try {
			le3.findLeader();
		} catch (Exception e) {
			logger.error(e);
		}
		le2.close();
		le3.close();
		Thread.sleep(2000);
	}
}

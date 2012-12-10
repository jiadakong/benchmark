package github.seanlinwang.study.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UdpTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private String getAddress() {
		Enumeration<?> en = null;
		try {
			en = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		for (; en.hasMoreElements();) {
			NetworkInterface intf = (NetworkInterface) en.nextElement();
			for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
				InetAddress inetAddress = enumIpAddr.nextElement();
				if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
					return inetAddress.getHostAddress();
				}
			}
		}
		return null;
	}

	@Test
	public void testBroadcast() throws IOException {

		final int port = 8899;

		Thread sender = new Thread(new Runnable() {

			@Override
			public void run() {
				DatagramSocket socket = null;
				try {
					socket = new DatagramSocket();
				} catch (SocketException e) {
					e.printStackTrace();
				}

				String ip = getAddress();
				String senderName = "sean";
				while (true) {
					int lastSpot = ip.lastIndexOf(".");
					String mask = ip.substring(0, lastSpot + 1);
					InetAddress address = null;
					try {
						address = InetAddress.getByName(mask + 255);
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					DatagramPacket packet = new DatagramPacket(senderName.getBytes(), senderName.getBytes().length, address, port);
					try {
						socket.send(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		sender.start();

		DatagramSocket socket = new DatagramSocket(port);
		while (true) {
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);
			String rawip = packet.getAddress().toString().trim();
			String ip = rawip.substring(1, rawip.length());
			String message = new String(packet.getData()).trim();
			System.out.println(String.format("%s:%s", ip, message));
		}
	}
}

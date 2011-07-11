package github.seanlinwang.benchmark.activemq;

import github.seanlinwang.benchmark.BenchmarkBase;
import github.seanlinwang.benchmark.util.Format;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Calendar;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * mq sender
 */
public class ActiveMqSend extends BenchmarkBase {

	private JmsTemplate template;
	private Destination destination;
	private String[] resultSet;
	private DateFormat dateFormat = Format.getDateFormat();
	private Calendar cal = Format.getCalendar();
	private PrintWriter out;

	public ActiveMqSend(PrintWriter out, int times) {
		this.out = out;
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
		template = (JmsTemplate) ctx.getBean("jmsTemplate");
		template.setDeliveryMode(DeliveryMode.PERSISTENT);
		template.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		template.setDeliveryPersistent(true);
		destination = (Destination) ctx.getBean("destination");

		this.resultSet = new String[times];
	}

	@Override
	public void run() {
		for (int i = 0; i < resultSet.length; i++) {
			final long now = System.currentTimeMillis();
			template.send(destination, new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					TextMessage message = session.createTextMessage("" + now);
					message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
					return message;
				}
			});
			long end = System.currentTimeMillis();
			resultSet[i] = ((end - now) + "," + end);
		}
	}

	public static void main(String[] args) throws JMSException, IOException, InterruptedException {
		PrintWriter out = BenchmarkBase.createOutput("activemq-send.csv");
		
		ActiveMqSend[] senders = new ActiveMqSend[5];
		for (int i = 0; i < senders.length; i++) {
			senders[i] = new ActiveMqSend(out, 10000);
		}
		Thread[] ts = new Thread[senders.length];
		for (int i = 0; i < senders.length; i++) {
			Thread t = new Thread(senders[i]);
			ts[i] = t;
			t.start();
		}

		for (int i = 0; i < ts.length; i++) {
			ts[i].join();
		}


		for (int i = 0; i < senders.length; i++) {
			ActiveMqSend sender = senders[i];
			sender.print();
		}

		out.close();

	}

	private void print() {
		for (String line : resultSet) {
			String[] segs = StringUtils.split(line, ',');
			out.write(segs[0]);
			out.write(",");
			this.cal.setTimeInMillis(Long.valueOf(segs[1]));
			out.write(this.dateFormat.format(cal.getTime()) + "\n");
		}
	}

	/* (non-Javadoc)
	 * @see github.seanlinwang.benchmark.BenchmarkBase#deleteTestData()
	 */
	@Override
	protected void deleteTestData() {
		// TODO Auto-generated method stub
		
	}
}

package github.seanlinwang.bentchmark.activemq;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

/**
 * mq receiver
 */
public class MyReceiver {
	public static void main(String[] args) throws JMSException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
		JmsTemplate template = (JmsTemplate) ctx.getBean("jmsTemplate");
		template.setDeliveryMode(DeliveryMode.PERSISTENT);
		Destination destination = (Destination) ctx.getBean("destination");
		
		while (true) {
			TextMessage txtmsg = (TextMessage) template.receive(destination);
			if (null != txtmsg)
				System.out.println(txtmsg.getText());
			else
				break;
		}
		
	}
}

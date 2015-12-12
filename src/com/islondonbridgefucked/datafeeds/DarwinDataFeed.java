package com.islondonbridgefucked.datafeeds;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

public class DarwinDataFeed {
	private final ActiveMQConnectionFactory amqFactory;
	private final Connection conn;
	private final MessageConsumer receiver;
	private DarwinOnMessage listener;
	
	@FunctionalInterface
	public interface DarwinOnMessage extends MessageListener {
		public void onMessage(Message msg);
	}
	
	public DarwinDataFeed(String brokerUrl, String queue) throws JMSException {
		// Generic values. The real password is the queue name.
		amqFactory = new ActiveMQConnectionFactory("d3user", "d3password", brokerUrl);
		conn = amqFactory.createConnection();
		
		Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue topic = new ActiveMQQueue(queue);
		receiver = sess.createConsumer(topic);
	}
	
	public void setListener(DarwinOnMessage l) throws JMSException {
		listener = l;
		receiver.setMessageListener(listener);
	}
	
	public void start() throws JMSException {
		conn.start();
	}
}

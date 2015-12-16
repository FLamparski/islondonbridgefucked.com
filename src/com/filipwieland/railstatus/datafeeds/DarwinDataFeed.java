package com.filipwieland.railstatus.datafeeds;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.zip.GZIPInputStream;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.util.ByteArrayInputStream;

import com.thalesgroup.pushport.Pport;

public class DarwinDataFeed implements MessageListener {
	private final ActiveMQConnectionFactory amqFactory;
	private final Connection conn;
	private final MessageConsumer receiver;
	private DarwinOnMessage listener;
	
	// private BufferedOutputStream debugStream;
	
	@FunctionalInterface
	public interface DarwinOnMessage {
		public void onMessage(Pport msg);
	}
	
	public DarwinDataFeed(String brokerUrl, String queue) throws JMSException, FileNotFoundException {
		// Generic values. The real password is the queue name.
		amqFactory = new ActiveMQConnectionFactory("d3user", "d3password", brokerUrl);
		conn = amqFactory.createConnection();
		
		Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue topic = new ActiveMQQueue(queue);
		receiver = sess.createConsumer(topic);
		receiver.setMessageListener(this);
		
		// debugStream = new BufferedOutputStream(new FileOutputStream("/tmp/data-capture"));
	}
	
	public void setListener(DarwinOnMessage l) {
		listener = l;
	}
	
	public void start() throws JMSException {
		conn.start();
	}
	
	private InputStream decodeMessage(BytesMessage bmsg, int len) throws JMSException, IOException {
		byte[] bytes = new byte[len];
		bmsg.readBytes(bytes);
		
		GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(bytes));
		return gis;
	}
	
	private Pport unfuckXML(InputStream is) throws JAXBException {
		String packageName = Pport.class.getPackage().getName();
		JAXBContext jcx = JAXBContext.newInstance(packageName);
		Unmarshaller u = jcx.createUnmarshaller();
		return (Pport) u.unmarshal(is);
	}

	@Override
	public void onMessage(Message msg) {
		try {
			BytesMessage bmsg = (BytesMessage) msg;
			int len = (int) bmsg.getBodyLength();
			Pport parsed = unfuckXML(decodeMessage(bmsg, len));
			listener.onMessage(parsed);
		} catch (JMSException | IOException | JAXBException e) {
			System.err.println("O SHI-");
			e.printStackTrace();
		}
	}
}

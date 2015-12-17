package com.filipwieland.railstatus.datafeeds;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.util.ByteArrayInputStream;

import com.filipwieland.railstatus.events.EventEmitter;
import com.filipwieland.railstatus.events.EventListener;
import com.thalesgroup.pushport.Pport;
import com.thalesgroup.pushport.Schedule;
import com.thalesgroup.pushport.StationMessage;
import com.thalesgroup.pushport.TS;
import com.thalesgroup.pushport.TSLocation;
import com.thalesgroup.pushport.TrainAlert;

public class DarwinDataFeed extends EventEmitter implements MessageListener {
	public static final String EVT_TRAIN_STATUS = "train-status";
	private final String brokerUrl;
	private final String queueName;
	private ActiveMQConnectionFactory amqFactory;
	private Connection conn;
	private MessageConsumer receiver;
	//private final Map<String, String> stationNames;
	
	public DarwinDataFeed(String brokerUrl, String queue) {
		//this.stationNames = stationNames;
		this.queueName = queue;
		this.brokerUrl = brokerUrl;
	}
	
	public void start() throws JMSException {
		// Generic values. The real password is the queue name.
		amqFactory = new ActiveMQConnectionFactory("d3user", "d3password", brokerUrl);
		conn = amqFactory.createConnection();
		
		Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue topic = new ActiveMQQueue(queueName);
		receiver = sess.createConsumer(topic);
		receiver.setMessageListener(this);	
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
			processPushPortMessage(parsed);
		} catch (JMSException | IOException | JAXBException e) {
			Map<String, Object> attrs = new HashMap<>();
			attrs.put("exception", e);
			this.emit(EVT_ERROR, attrs);
		}
	}
	
	public void onCookedMessage(InputStream is) throws JAXBException {
		Pport parsed = unfuckXML(is);
		processPushPortMessage(parsed);
	}
	
	protected void processTrainStatus(TS ts) {
		System.out.printf("    TS: %s %s %s\n", ts.getRid(), ts.getUid(), ts.getSsd().toString());
		for (TSLocation lcn : ts.getLocation()) {
			if (lcn.getPass() != null) {
				try {
					ServiceCall cPass = ServiceCall.getInstance(new Date(), ts.getUid(), lcn.getWtp(), lcn.getPass().getEt(), lcn.getTpl(), ServiceCall.CallType.PASS);
					Map<String, Object> attrs = new HashMap<>();
					attrs.put("descriptor", cPass);
					this.emit(EVT_TRAIN_STATUS, attrs);
				} catch (ParseException e) {
					Map<String, Object> attrs = new HashMap<>();
					attrs.put("exception", e);
					this.emit(EVT_ERROR, attrs);
				}
			}
			if (lcn.getArr() != null) {
				try {
					ServiceCall cPass = ServiceCall.getInstance(new Date(), ts.getUid(), lcn.getWta(), lcn.getArr().getEt(), lcn.getTpl(), ServiceCall.CallType.ARRIVAL);
					Map<String, Object> attrs = new HashMap<>();
					attrs.put("descriptor", cPass);
					this.emit(EVT_TRAIN_STATUS, attrs);
				} catch (ParseException e) {
					Map<String, Object> attrs = new HashMap<>();
					attrs.put("exception", e);
					this.emit(EVT_ERROR, attrs);
				}
			}
			if (lcn.getDep() != null) {
				try {
					ServiceCall cPass = ServiceCall.getInstance(new Date(), ts.getUid(), lcn.getWtd(), lcn.getDep().getEt(), lcn.getTpl(), ServiceCall.CallType.PASS);
					Map<String, Object> attrs = new HashMap<>();
					attrs.put("descriptor", cPass);
					this.emit(EVT_TRAIN_STATUS, attrs);
				} catch (ParseException e) {
					Map<String, Object> attrs = new HashMap<>();
					attrs.put("exception", e);
					this.emit(EVT_ERROR, attrs);
				}
			}
		}
	}

	protected void processPushPortMessage(Pport port) {
		System.out.println(port.getTs().toString() + ": " + port.getUR().getUpdateOrigin() + ":");
		if (port.getUR().getTS().size() > 0) {
			List<TS> tss = port.getUR().getTS();
			System.out.println("  TrainStatus x " + tss.size());
			tss.stream().forEach(this::processTrainStatus);
		}
		if (port.getUR().getOW().size() > 0) {
			List<StationMessage> sms = port.getUR().getOW();
			System.out.println("  StationMessage x " + sms.size());
		}
		if (port.getUR().getSchedule().size() > 0) {
			List<Schedule> ss = port.getUR().getSchedule();
			System.out.println("  Schedule x " + ss.size());
		}
		if (port.getUR().getTrainAlert().size() > 0) {
			List<TrainAlert> ats = port.getUR().getTrainAlert();
			System.out.println("  TrainAlert x " + ats.size());
		}
		if (port.getUR().getSchedule().size() > 0) {
			List<Schedule> ss = port.getUR().getSchedule();
			for (Schedule schedule : ss) {
				System.out.println(schedule.getUid());
			}
		}
	}
}

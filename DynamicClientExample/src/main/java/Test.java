

import java.util.ArrayList;
import javax.jms.JMSException;

public class Test {
	public static void main(String[] args) throws InterruptedException, JMSException{
		
		String acceptorAddress = "tcp://localhost:61616";
		String queueName = "testQueue";
		int NODES = 1, CONSUMERS = 5;
		
		long max = 3000;
	    long min = 500;
	    long range = max - min + 1;
		
		//set up node driver
		System.out.println("SENDERS:" + NODES);
		ArrayList<NodeDriver> nodes = new ArrayList<NodeDriver>();
		for(int i=1; i<=NODES; i++) {
			NodeDriver node = new NodeDriver(queueName, acceptorAddress, i);
			System.out.println("Node n." + i + " - sending period: "+ node.SENDER_PERIOD + " s");
			node.startSending();
			nodes.add(node);
		}
				
		//set up consumer
		System.out.println("CONSUMERS:" + CONSUMERS);
		ArrayList<Consumer> consumers = new ArrayList<Consumer>();
		for(int i=1; i<=CONSUMERS; i++) {
			Consumer consumer = new Consumer(queueName, acceptorAddress, i);
			System.out.println("Consumer n." + i + " - consuming period: "+ consumer.CONSUMER_PERIOD + " s");
			consumer.startConsuming();
			consumers.add(consumer);
		}
		
		System.out.println("\n----SIMULATION STARTED ----\n");

		//Let simulation run
		Thread.sleep(60*1000);
				
		//stop and clean all
		for(NodeDriver node: nodes) node.stopSending();
		for(Consumer consumer: consumers) consumer.stopConsuming();
		System.out.println("----SIMULATION ENDEND----\n");		
	}
}

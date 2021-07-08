import adaptiveProducerFramework.producers.JMSProducerCreator;
import adaptiveProducerFramework.producers.Producer;
import adaptiveProducerFramework.producers.Sample;
import adaptiveProducerFramework.producers.exceptions.InvalidSampleTTLException;

public class SimpleTest {

	public static void main(String[] args) throws InvalidSampleTTLException {
		String acceptorAddress = "tcp://localhost:61616";
		String queueName = "testQueue";
		//Initialize client
		Producer producer = new JMSProducerCreator().createProducer(queueName, acceptorAddress);
		
		//start connection and queue monitoring
		producer.startProducer();
		
		//Send a sample
		Sample sample = new Sample(20.0, 500);
		producer.trySending(sample);
		
		//Close connection and queue monitoring
		producer.stopProducer();
	}
}



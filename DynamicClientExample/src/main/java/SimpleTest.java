import dynamiClientFramework.clients.Client;
import dynamiClientFramework.clients.DJMSClientCreator;
import dynamiClientFramework.clients.Sample;
import dynamiClientFramework.clients.exceptions.InvalidSampleTTLException;

public class SimpleTest {

	public static void main(String[] args) throws InvalidSampleTTLException {
		String acceptorAddress = "tcp://localhost:61616";
		String queueName = "testQueue";
		//Initialize client
		Client dclient = new DJMSClientCreator().createDynamicClient(queueName, acceptorAddress);
		
		//start connection and queue monitoring
		dclient.startClient();
		
		//Send a sample
		Sample sample = new Sample(20.0, 500);
		dclient.trySending(sample);
		
		//Close connection and queue monitoring
		dclient.stopClient();
	}

}



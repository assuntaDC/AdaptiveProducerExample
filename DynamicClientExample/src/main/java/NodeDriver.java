
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import dynamiClientFramework.clients.Client;
import dynamiClientFramework.clients.DJMSClientCreator;
import dynamiClientFramework.clients.Sample;
import dynamiClientFramework.clients.exceptions.InvalidSampleTTLException;

public class NodeDriver{
	
	private Client dclient;
	public long SENDER_PERIOD = 500;
	double max = 30;
    double min = 20;
    double range = max - min + 1;
	private ScheduledExecutorService executor;
	private ScheduledFuture<?> future;
	
	public NodeDriver(String queueName, String acceptorAddress, int id){
		dclient = new DJMSClientCreator().createDynamicClient(queueName, acceptorAddress);
	}
	
	public void startSending() {
		dclient.startClient();
		executor = Executors.newSingleThreadScheduledExecutor();
		future = executor.scheduleWithFixedDelay(new SendThread(), 0, SENDER_PERIOD, TimeUnit.MILLISECONDS);			
	}
	
	public void stopSending() {
		dclient.stopClient();
		future.cancel(false);
		executor.shutdown();
	}
	
	private class SendThread implements Runnable{
		public void run() {
			int temperature = (int) ((int)(Math.random() * range) + min);
			try {
				Sample sample = new Sample(temperature, 500);
				dclient.trySending(sample);
			}catch(InvalidSampleTTLException e) {
				e.printStackTrace();
			}
		}
	}
}



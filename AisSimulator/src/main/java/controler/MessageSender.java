package controler;

import java.io.IOException;

import model.TCPClient;
import model.aismessages.AisStream;

/**
 * Contrôleur Thread gérant l'envoie temporisé du flux AIS généré
 */
public class MessageSender extends Thread {
	
	private AisStream stream;
	private boolean running;
	
	public MessageSender(AisStream stream) {
		this.stream = stream;
	}
	
	public void run() {
		running = true;
		int i = 0;
		while(running && i < stream.size()) {
			try {
				TCPClient.sendMessage(stream.getMessage(i).substring(stream.getMessage(i).indexOf("!")));
				System.out.println(stream.getMessage(i));
				long delay = stream.getTimeStamp(stream.getMessage(i+1)) - stream.getTimeStamp(stream.getMessage(i));
				sleep(delay);
			} catch (IOException | InterruptedException e2) { System.out.println(e2.getMessage());}
			i++;
		}
	}
	
	public void cancel() {
	      running = false;
	}
	
}

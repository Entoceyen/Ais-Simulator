package model.aismessages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Modèle correspondant à un flux de messages AIS
 */
public class AisStream {

	private ArrayList<String> aisMessages;
	
	public AisStream() {
		aisMessages = new ArrayList<String>();
	}
	
	public void addMessage(String line) {
		aisMessages.add(line);
	}

	public String getMessage(int i) {
		return aisMessages.get(i);
	}
	
	public int size() {
		return aisMessages.size();
	}
	
	/**
	 * Retourne le timeStamp d'un message passé en paramètre
	 * @param msg le message
	 * @return long en millisecond
	 */
	public long getTimeStamp(String msg) {
		return Long.parseLong(msg.substring(4, msg.indexOf("*")));
	}
	
	/**
	 * Créé un object File à partir du flux AIS
	 * @return File
	 * @throws IOException
	 */
	public File export() throws IOException {
		File f = new File("aisMessage.log");
		FileOutputStream fos = new FileOutputStream(f);
		BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fos));
		
		for(String line : aisMessages) {
			br.write(line);
			br.newLine();
		}
		br.close();
		
		return f;
	}
	
	@Override
	public String toString() {
		String s = "AisStream [\nnbMessage=" +aisMessages.size()+ "\naisMessages=";
		for(String msg : aisMessages) s += msg+"\n";
		s += "\n]";
		return s;
	}
}

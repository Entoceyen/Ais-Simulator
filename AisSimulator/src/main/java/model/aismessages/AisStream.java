package model.aismessages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

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
	
	public int getTimeStamp(String msg) {
		return Integer.parseInt(msg.substring(4, msg.indexOf("*")));
	}
	
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

package model;

import java.io.*;
import java.net.*;

/**
 * Client TCP permettant d'envoyer des messages sur un serveur tcp
 */
public class TCPClient {
	
	private static String address;
	private static int port;
	
	public static void sendMessage(String line) throws UnknownHostException, IOException {
		Socket clientSocket = new Socket(address, port);
		// connexion transpondeur
		PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
		pw.print(line);
		// connexion hercules
		OutputStream os = clientSocket.getOutputStream();
		os.write(line.getBytes());
		clientSocket.close();
		pw.close();
	}
	
	/**
	 * Test la connexion au serveur
	 * @return true si la connexion est établie, false sinon
	 */
	public static boolean ping() {
		try {
			Socket s = new Socket(address, port);
			s.close();
			return true;
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	public static String getAddress() {
		return address;
	}

	public static void setAddress(String address) {
		TCPClient.address = address;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		TCPClient.port = port;
	}
}
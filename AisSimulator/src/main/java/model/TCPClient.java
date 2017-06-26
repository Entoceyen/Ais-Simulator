package model;

import java.io.*;
import java.net.*;

/**
 * Client TCP permettant d'envoyer des messages sur un serveur tcp
 */
public class TCPClient {
	
	private static String address;
	private static int port;
	
	public static void sendMessage(String[] lines) throws UnknownHostException, IOException {
		Socket clientSocket = new Socket(address, port);
		PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
		for(String line : lines) pw.print(line);
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
package model;

import java.io.*;
import java.net.*;

/**
 * Modèle client TCP permettant d'envoyer des messages sur un serveur tcp
 */
public class TCPClient {
	
	private static String address;
	private static int port;
	
	/**
	 * Se connecte et envoi la chaine de caractère sur un serveur TCP
	 * @param line
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void sendMessage(String line) throws UnknownHostException, IOException {
		Socket clientSocket = new Socket(address, port);
		// connexion transpondeur
//		PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
//		pw.println(line);
//		pw.close();
		// connexion hercules
		DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
		os.write((line+"\n").getBytes());
		os.flush();
		clientSocket.close();
		os.close();
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
package view;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controler.TcpConfigListener;

import javax.swing.JButton;

/**
 * Vue de configuration du client TCP
 */
public class TCPConfigPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextField textFieldIP;
	private JTextField textFieldPort;
	private JButton btnOk;
	private JButton btnPing;
	private JLabel lblState;
	
	public TCPConfigPanel(TcpConfigListener listener) {
		setLayout(new GridLayout(4, 0, 0, 0));
		
		JLabel lblIpServeur = new JLabel("IP serveur :");
		add(lblIpServeur);
		
		textFieldIP = new JTextField();
		add(textFieldIP);
		textFieldIP.setColumns(11);
		
		JLabel lblPort = new JLabel("Port :");
		add(lblPort);
		
		textFieldPort = new JTextField();
		add(textFieldPort);
		textFieldPort.setColumns(5);
		
		lblState = new JLabel();
		add(lblState);
		JLabel label = new JLabel();
		add(label);
		
		btnPing = new JButton("Test");
		btnPing.addActionListener(listener);
		add(btnPing);
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(listener);
		add(btnOk);
	}
	
	/**
	 * Retourne l'adresse saisie dans le formulaire
	 * @return String
	 */
	public String getAddress() {
		return textFieldIP.getText();
	}
	
	/**
	 * Retourne le port saisi dans le formulaire
	 * @return int
	 */
	public int getPort() {
		try {
			return Integer.parseInt(textFieldPort.getText());
		} catch(NumberFormatException e) {
			return 0;
		}
	}
	
	/**
	 * Actualise l'affichage de l'état de la connexion au serveur
	 * @param s String
	 */
	public void setState(String s) {
		lblState.setText(s);
	}
}

package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.TCPClient;
import view.OptionsFrame;
import view.TCPConfigPanel;

/**
 * Controleur listener à l'écoute des actions utilisateur sur le panneau de configuration tcp
 */
public class TcpConfigListener implements ActionListener {

	private TCPConfigPanel panel;

	public void setPanel(TCPConfigPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		TCPClient.setAddress(panel.getAddress());
		TCPClient.setPort(panel.getPort());
		if(e.getActionCommand().equals("Ok")) 
			((OptionsFrame) panel.getParent()).setVisible(false);
		else if(e.getActionCommand().equals("Test")) 
			panel.setState(TCPClient.ping() ? "Connecté" : "Echec de connexion");
	}

}

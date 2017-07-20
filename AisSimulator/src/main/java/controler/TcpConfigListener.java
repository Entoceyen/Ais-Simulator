package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.TCPClient;
import view.OptionsFrame;
import view.TCPConfigPanel;

/**
 * Contrôleur listener à l'écoute des actions utilisateur sur le panneau de configuration tcp
 */
public class TcpConfigListener implements ActionListener {

	private TCPConfigPanel panel;
	private OptionsFrame frame;

	public void setPanel(TCPConfigPanel panel) {
		this.panel = panel;
	}
	
	public void setFrame(OptionsFrame frame) {
		this.frame = frame;
	} 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		TCPClient.setAddress(panel.getAddress());
		TCPClient.setPort(panel.getPort());
		if(e.getActionCommand().equals("Ok")) 
			frame.setVisible(false);
		else if(e.getActionCommand().equals("Test")) 
			panel.setState(TCPClient.ping() ? "Connecté" : "Echec de connexion");
	}

}

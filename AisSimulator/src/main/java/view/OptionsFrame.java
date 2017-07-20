package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import controler.Application;
import java.awt.CardLayout;

/**
 * Vue et fenetre affichant les différents panneaux d'options
 */
public class OptionsFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private OptionsPanel optionsPanel;
	private TCPConfigPanel tcpPanel;
	private Application application;
	
	public OptionsFrame(OptionsPanel optionsPanel, TCPConfigPanel tcpPanel, Application application) {
		this.optionsPanel = optionsPanel;
		this.tcpPanel = tcpPanel;
		this.application = application;
		
		getContentPane().setLayout(new CardLayout(0, 0));
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width / 3;
		int height = screenSize.height / 4;
        int xpos = (screenSize.width - width) / 2;
        int ypos = (screenSize.height - height) / 2;
        setBounds(xpos, ypos, width, height);
		setTitle("Options");
		
		getContentPane().add(tcpPanel, "TCP");
		getContentPane().add(optionsPanel, "OPTIONS");
	    
	    setVisible(false);
	    
	    addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	setVisible(false);
            }
        });
	}
	
	/**
	 * Affiche le panneau d'options
	 */
	public void displayOptionsPanel() {
		setVisible(true);
		CardLayout cl = (CardLayout)(getContentPane().getLayout());
	    cl.show(getContentPane(), "OPTIONS");
	}
	
	/**
	 * Affiche le panneau de configuration de la connexion TCP
	 */
	public void displayTcpPanel() {
		setVisible(true);
		CardLayout cl = (CardLayout)(getContentPane().getLayout());
	    cl.show(getContentPane(), "TCP");
	}
	
	public OptionsPanel getOptionsPanel() {
		return optionsPanel;
	}
	
	public TCPConfigPanel getTCPConfigPanel() {
		return tcpPanel;
	}
	
	public Application getApplication() {
		return application;
	}
	
}

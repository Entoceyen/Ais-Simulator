package controler;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.border.TitledBorder;
import javax.swing.JPanel;

import model.Simulation;
import view.*;

/**
 * Controleur principal de l'application
 */
public class Application {
	
	private Font font;
	private ApplicationFrame applicationFrame;
	private OptionsFrame optionsFrame;
	
	/**
	 * Constructeur initialisant et démarrant l'application
	 * Créé l'ensemble des vues et des controleurs et les associe
	 */
	public Application() {
		font = new Font("Tahoma",Font.PLAIN,15);;
		Simulation simulation = new Simulation();
		
		PathListener pathListener = new PathListener(simulation);
		PathPanel pathPanel = new PathPanel(pathListener);
		pathListener.setPanel(pathPanel);
		
		DataImportListener importListener = new DataImportListener(simulation);
		DataExportListener exportListener = new DataExportListener(simulation);
		DataLoadListener loadListener = new DataLoadListener(simulation);
		DataPanel dataPanel = new DataPanel(importListener, exportListener, loadListener);
		importListener.setPanel(dataPanel);
		exportListener.setPanel(dataPanel);
		loadListener.setPanel(dataPanel);
		
		FormPanel formPanel = new FormPanel(dataPanel, pathPanel);
		
		OptionsListener optionsListener = new OptionsListener();
		OptionsPanel optionsPanel = new OptionsPanel(optionsListener);
		optionsListener.setPanel(optionsPanel);
		
		TcpConfigListener tcpListener = new TcpConfigListener();
		TCPConfigPanel tcpPanel = new TCPConfigPanel(tcpListener);
		tcpListener.setPanel(tcpPanel);
		
		optionsFrame = new OptionsFrame(optionsPanel, tcpPanel, this);
		optionsListener.setFrame(optionsFrame);
		applicationFrame = new ApplicationFrame(formPanel, this);
		setFont(font.getSize());
		optionsPanel.setFontSize();
	}
	
	/**
	 * Met à jour la taille de la police
	 * @param size - taille de la police
	 */
	public void setFont(int size) {
		font = new Font("Tahoma", Font.PLAIN, size);
		changeFont(applicationFrame, font);
		changeFont(optionsFrame, font);
		applicationFrame.setFontMenu(font);
	}
	
	/**
	 * Met à jour la taille de la police à un composant et tout ses enfants
	 * @param component
	 * @param font
	 */
	public static void changeFont(Component component, Font font) {
	    component.setFont(font);
	    if(component instanceof JPanel) {
	    	TitledBorder b = ((TitledBorder) ((JPanel)component).getBorder());
	    	if(b != null) b.setTitleFont(new Font("Tahoma",Font.BOLD,font.getSize()));
	    }
		component.revalidate();
	    if(component instanceof Container)
	        for (Component child : ((Container) component).getComponents())
	            changeFont(child, font);
	}
	
	public ApplicationFrame getApplicationFrame() {
		return applicationFrame;
	}
	
	public OptionsFrame getOptionsFrame() {
		return optionsFrame;
	}
	
	/**
	 * Démarre l'application
	 * @param args
	 */
	public static void main(String args[]) {
		new Application();
	}
}

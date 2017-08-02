package controler;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.Simulation;
import view.*;

/**
 * Contrôleur principal de l'application
 */
public class Application {
	
	private Font font;
	private ApplicationFrame applicationFrame;
	private OptionsFrame optionsFrame;
	private AboutFrame aboutFrame;
	private InitFrame initFrame;
	
	/**
	 * Constructeur initialisant et démarrant l'application
	 * Créé l'ensemble des vues et des controleurs et les associe
	 */
	public Application() {
		ImageIcon img;
		try { img = new ImageIcon(getClass().getResource("/resources/deais_icone.png")); }
		catch(NullPointerException e) { img = new ImageIcon(ClassLoader.getSystemResource("deais_icone.png")); }
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
		
		TimedSimulationListener timedSimuListener = new TimedSimulationListener(simulation);
		TimedSimulationPanel timedSimuPanel = new TimedSimulationPanel(timedSimuListener);
		timedSimuListener.setPanel(timedSimuPanel);
		loadListener.setTimedSimulationListener(timedSimuListener);
		
		ScenariosRemoveListener scenarioRemoveListener = new ScenariosRemoveListener(simulation);
		scenarioRemoveListener.setTimedSimuListener(timedSimuListener);
		
		DataPreviewPanel previewPanel = new DataPreviewPanel(scenarioRemoveListener);
		timedSimuListener.setPreviewPanel(previewPanel);
		
		ScenariosListener scenariosListener = new ScenariosListener();
		loadListener.setScenariosListener(scenariosListener);
		ScenariosFormListener scenariosFormListener = new ScenariosFormListener(simulation);
		ScenariosPanel scenariosPanel = new ScenariosPanel(scenariosListener);
		ScenarioFormFrame scenarioForm = new ScenarioFormFrame(scenariosFormListener);
		scenariosListener.setPanel(scenariosPanel);
		scenariosListener.setScenarioFrame(scenarioForm);
		scenariosFormListener.setFrame(scenarioForm);
		scenariosFormListener.setTimedSimuListener(timedSimuListener);
		
		ManualDelayMessageListener delayMessageListener = new ManualDelayMessageListener(simulation);
		ManualDelayMessageFrame delayMessageFrame = new ManualDelayMessageFrame(delayMessageListener);
		delayMessageListener.setFrame(delayMessageFrame);
		
		AisStreamListener aisListener = new AisStreamListener(simulation, delayMessageFrame);
		AisPanel aisPanel = new AisPanel(aisListener);
		aisListener.setPanel(aisPanel);
		loadListener.setStreamListener(aisListener);
				
		OptionsListener optionsListener = new OptionsListener();
		OptionsPanel optionsPanel = new OptionsPanel(optionsListener);
		optionsListener.setPanel(optionsPanel);
		
		TcpConfigListener tcpListener = new TcpConfigListener();
		TCPConfigPanel tcpPanel = new TCPConfigPanel(tcpListener);
		tcpListener.setPanel(tcpPanel);
		
		initFrame = new InitFrame(img);
		aboutFrame = new AboutFrame(img);
		optionsFrame = new OptionsFrame(optionsPanel, tcpPanel, this);
		optionsListener.setFrame(optionsFrame);
		tcpListener.setFrame(optionsFrame);
		applicationFrame = new ApplicationFrame(formPanel, timedSimuPanel, previewPanel, scenariosPanel, aisPanel, this);
		applicationFrame.setIconImage(img.getImage());
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
		changeFont(aboutFrame, font);
		applicationFrame.setFontMenu(font);
	}
	
	/**
	 * Met à jour la taille de la police d'un composant et de tout ses enfants
	 * @param component composent
	 * @param font police
	 */
	public static void changeFont(Component component, Font font) {
	    component.setFont(font);
	    if(component instanceof JPanel) {
	    	Border b = ((Border) ((JPanel)component).getBorder());
	    	if(b != null && b instanceof TitledBorder) ((TitledBorder)b).setTitleFont(new Font("Tahoma",Font.BOLD,font.getSize()));
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
	
	public AboutFrame getAboutFrame() {
		return aboutFrame;
	}
	
	public InitFrame getInitFrame() {
		return initFrame;
	}
	
	/**
	 * Démarre l'application
	 * @param args
	 */
	public static void main(String args[]) {
//		try {
//			System.setOut(new PrintStream(new File("outputLog.log")));
//		} catch (FileNotFoundException e) {}
		new Application();
	}
}

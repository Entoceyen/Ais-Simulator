package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import controler.Application;

import java.awt.BorderLayout;
import java.awt.Desktop;

/**
 * Vue correspondant à la fenêtre principale de l'application
 */
public class ApplicationFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Application application;
	private JMenuBar menuBar;
	
	public ApplicationFrame(InitFrame initFrame, AboutFrame aboutFrame, FormPanel formPanel, TimedSimulationPanel timedSimuPanel, DataPreviewPanel previewPanel, ScenariosPanel scenarPanel, AisPanel aisPanel, Application appli) {
		this.application = appli;
		setTitle("Simulateur DéAIS");
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPaneLeft = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPaneLeft.setLeftComponent(formPanel);
		splitPaneLeft.setRightComponent(previewPanel);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(scenarPanel, BorderLayout.CENTER);
		panel.add(aisPanel, BorderLayout.SOUTH);
		JSplitPane splitPaneRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPaneRight.setLeftComponent(splitPaneLeft);
		splitPaneRight.setRightComponent(panel);
		getContentPane().add(splitPaneRight, BorderLayout.CENTER);
		getContentPane().add(timedSimuPanel, BorderLayout.SOUTH);
		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setMinimumSize(new Dimension(width/4, height/4));
		
		addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	int choise = PopupManager.optionsMessage("Quitter ?", "Voulez-vous quittez l'application ?", "Non","Oui");
            	if(choise == 1) setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	if(choise == 0) setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });
		
		// TODO menu à propos & crédit
	    JMenu m = new JMenu("Menu");
	    JMenuItem itemNew = new JMenuItem("Nouveau");
	    itemNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				Application.main(null);
			}
		});
	    JMenuItem itemOption = new JMenuItem("Options");
	    itemOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				application.getOptionsFrame().displayOptionsPanel();
			}
		});
	    JMenuItem itemTcp = new JMenuItem("Configurer connexion TCP");
	    itemTcp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				application.getOptionsFrame().displayTcpPanel();
			}
		});
	    m.add(itemNew);
	    m.add(itemOption);
	    m.add(itemTcp);
	    
	    JMenu m1 = new JMenu("Aide");
	    JMenuItem itemAbout = new JMenuItem("A propos");
	    itemAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				application.getAboutFrame().setVisible(true);
			}
		});
	    JMenuItem itemDoc = new JMenuItem("Documentation");
	    itemDoc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Desktop.isDesktopSupported()){
					if(Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)){
						URI uri;
						try {
							uri = new URI("file:///C:/Users/Nicolas%20BERNARD/Documents/AIS%20Simulateur/javadoc/index.html");
							Desktop.getDesktop().browse(uri);
						} catch (Exception e1) {}
					}
				}
			}
		});
	    m1.add(itemDoc);
	    m1.add(itemAbout);
	    
	    menuBar = new JMenuBar();
	    menuBar.add(m);
	    menuBar.add(m1);
	    setJMenuBar(menuBar);
	    
	    try {
			Thread.sleep(1500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	    initFrame.setVisible(false);
		setVisible(true);
	}
	
	/**
	 * Met à jour la police dans le menu
	 * @param font Font
	 */
	public void setFontMenu(Font font) {
		for(int i=0 ; i<menuBar.getMenuCount() ; i++)
			for(int j=0 ; j<menuBar.getMenu(i).getItemCount() ; j++)
				menuBar.getMenu(i).getItem(j).setFont(font);
	}

}

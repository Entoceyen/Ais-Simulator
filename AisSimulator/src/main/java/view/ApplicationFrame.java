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
	private FormPanel formPanel;
	private TimedSimulationPanel timedSimuPanel;
	private DataPreviewPanel previewPanel;
	private ScenariosPanel scenarPanel;
	private AisPanel aisPanel;
	
	public ApplicationFrame(FormPanel formPanel, TimedSimulationPanel timedSimuPanel, DataPreviewPanel previewPanel, ScenariosPanel scenarPanel, AisPanel aisPanel, Application appli) {
		this.application = appli;
		this.formPanel = formPanel;
		this.timedSimuPanel = timedSimuPanel;
		this.previewPanel = previewPanel;
		this.scenarPanel = scenarPanel;
		this.aisPanel = aisPanel;
		
		setTitle("Simulateur DéAIS");
		getContentPane().setLayout(new BorderLayout(0, 0));
		
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
		
		build();
		buildMenu();
	    
	    try {
			Thread.sleep(1500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	    application.getInitFrame().setVisible(false);
		setVisible(true);
	}
	
	private void build() {
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
	}
	
	private void buildMenu() {
		JMenu m = new JMenu("Menu");
	    JMenuItem itemNew = new JMenuItem("Nouveau");
	    itemNew.addActionListener(actionNew());
	    JMenuItem itemOption = new JMenuItem("Options");
	    itemOption.addActionListener(actionOption());
	    JMenuItem itemTcp = new JMenuItem("Configurer connexion TCP");
	    itemTcp.addActionListener(actionTcp());
	    m.add(itemNew);
	    m.add(itemOption);
	    m.add(itemTcp);
	    
	    JMenu m1 = new JMenu("Aide");
	    JMenuItem itemAbout = new JMenuItem("A propos");
	    itemAbout.addActionListener(actionAbout());
	    JMenuItem itemDoc = new JMenuItem("Documentation");
	    itemDoc.addActionListener(actionDoc());
	    m1.add(itemDoc);
	    m1.add(itemAbout);
	    
	    menuBar = new JMenuBar();
	    menuBar.add(m);
	    menuBar.add(m1);
	    setJMenuBar(menuBar);
	}
	
	private ActionListener actionNew() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				Application.main(null);
			}
		};
	}
	
	private ActionListener actionOption() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				application.getOptionsFrame().displayOptionsPanel();
			}
		};
	}
	
	private ActionListener actionTcp() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				application.getOptionsFrame().displayTcpPanel();
			}
		};
	}
	
	private ActionListener actionAbout() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				application.getAboutFrame().setVisible(true);
			}
		};
	}
	
	private ActionListener actionDoc() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Desktop.isDesktopSupported()){
					if(Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)){
						URI uri;
						try {
							uri = new URI("https://github.com/Entoceyen/Ais-Simulator/blob/master/Documentation%20utilisateur.pdf");
							Desktop.getDesktop().browse(uri);
						} catch (Exception e1) {}
					}
				}
			}
		};
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

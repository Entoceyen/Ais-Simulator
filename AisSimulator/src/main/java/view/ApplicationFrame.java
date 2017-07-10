package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controler.Application;
import java.awt.BorderLayout;

/**
 * Vue correspondant à la fenêtre principale de l'application
 */
public class ApplicationFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Application application;
	private JMenuBar menuBar;
	
	public ApplicationFrame(FormPanel formPanel, TimedSimulationPanel timedSimuPanel, ScenariosPanel scenarPanel, AisPanel aisPanel, Application appli) {
		this.application = appli;
		setTitle("Simulateur AIS");
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(formPanel, BorderLayout.WEST);
		getContentPane().add(timedSimuPanel, BorderLayout.SOUTH);
		getContentPane().add(scenarPanel, BorderLayout.CENTER);
		getContentPane().add(aisPanel, BorderLayout.EAST);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setMinimumSize(new Dimension(width/4, height/4));
		
		addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	System.exit(0);
            }
        });
		
	    JMenu m = new JMenu("Menu");
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
	    m.add(itemOption);
	    m.add(itemTcp);
	    
	    menuBar = new JMenuBar();
	    menuBar.add(m);
	    setJMenuBar(menuBar);
		
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

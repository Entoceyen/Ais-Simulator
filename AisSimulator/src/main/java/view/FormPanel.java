/**
 * 
 */
package view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Vue organisant l'affichage du formulaire complet
 */
public class FormPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private DataPanel dataPanel;
	private PathPanel pathPanel;
	
	public FormPanel(DataPanel dataPanel, PathPanel pathPanel) {
		this.dataPanel = dataPanel;
		this.pathPanel = pathPanel;
		setLayout(new BorderLayout(0, 0));
		add(pathPanel, BorderLayout.NORTH);
		add(dataPanel, BorderLayout.CENTER);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setMinimumSize(new Dimension((int)(screenSize.getWidth()/10),(int)screenSize.getHeight()/4));
	}
	
	public DataPanel getDataPanel() {
		return dataPanel;
	}
	
	public PathPanel getPathPanel() {
		return pathPanel;
	}
	
	/**
	 * Désactive la vue 
	 */
	public void disable() {
		dataPanel.disable();
		pathPanel.disable();
	}
	
	/**
	 * Active la vue 
	 */
	public void enable() {
		dataPanel.enable();
		pathPanel.enable();
	}
}

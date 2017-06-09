package view;

import java.awt.Component;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import controler.PathListener;

/**
 * Vue permettant de selectionner un fichier trajet
 */
public class PathPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JButton selectBtn;
	private JLabel selectLbl;
	
	public PathPanel(PathListener listener) {
		TitledBorder border = BorderFactory.createTitledBorder("Trajet");
		setBorder(border);
		
		selectLbl = new JLabel("Choisir un fichier trajet");
		add(selectLbl);
		
		selectBtn = new JButton("Parcourir");
		add(selectBtn);
		selectBtn.addActionListener(listener);
	}

	/**
	 * Permet de selectionner un fichier trajet
	 * @return File
	 */
	public File getPathFile() {
		File f = PopupManager.fileChooser("Selectionner un fichier trajet", "Fichiers csv.", "csv");
		if(f != null) selectLbl.setText("Selectionné : "+f.getName());
		return f;
	}
	
	/**
	 * Désactive la modification de la vue
	 */
	public void disable() {
		for(Component c : getComponents()) c.setEnabled(false);
	}
	
	/**
	 * Active la modification de la vue
	 */
	public void enable() {
		for(Component c : getComponents()) c.setEnabled(true);
	}
	
}

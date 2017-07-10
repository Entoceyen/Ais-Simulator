package view;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

import controler.AisStreamListener;

public class AisPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JButton computeBtn;
	private JButton exportBtn;
	private JButton runBtn;
	
	public AisPanel(AisStreamListener listener) {
		computeBtn = new JButton("Calculer le flux AIS");
		exportBtn = new JButton("Exporter fichier");
		runBtn = new JButton("Jouer simulation temps réel");
		computeBtn.addActionListener(listener);
		computeBtn.setName("Compute");
		exportBtn.addActionListener(listener);
		exportBtn.setName("Export");
		runBtn.addActionListener(listener);
		runBtn.setName("Run");
		add(computeBtn);
		add(exportBtn);
		add(runBtn);
	}
	
	/**
	 * Permet de sauvegarder un fichier
	 * @param f File
	 * @return true si le fichier a été sauvegardé, false sinon
	 */
	public boolean saveDataFile(File f) {
		return PopupManager.fileSaver("Enregistrer sous", f);
	}

}

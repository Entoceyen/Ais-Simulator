package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import controler.AisStreamListener;

/**
 * Vue affichant les boutons de gestion du flux de message AIS
 */
public class AisPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JButton computeBtn;
	private JButton exportBtn;
	private JButton runBtn;
	private JButton stopBtn;
	
	public AisPanel(AisStreamListener listener) {
		TitledBorder border = BorderFactory.createTitledBorder("Flux AIS");
		setBorder(border);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setMinimumSize(new Dimension((int)(screenSize.getWidth()/10),(int)screenSize.getHeight()/4));
		computeBtn = new JButton("Modifier fréquence d'émission");
		exportBtn = new JButton("Exporter fichier");
		runBtn = new JButton("Jouer simulation temps r\u00E9el");
		stopBtn = new JButton("Arrêter simulation");
		computeBtn.addActionListener(listener);
		computeBtn.setName("ManualDelayMessage");
		exportBtn.addActionListener(listener);
		exportBtn.setName("Export");
		runBtn.addActionListener(listener);
		runBtn.setName("Run");
		stopBtn.addActionListener(listener);
		stopBtn.setName("Stop");
		stopBtn.setVisible(false);
		add(computeBtn);
		add(exportBtn);
		add(runBtn);
		add(stopBtn);
		setActive(false);
	}
	
	/**
	 * Permet de sauvegarder un fichier
	 * @param f File
	 * @return true si le fichier a \u00E9t\u00E9 sauvegard\u00E9, false sinon
	 */
	public boolean saveDataFile(File f) {
		return PopupManager.fileSaver("Enregistrer sous", f);
	}
	
	public void setActive(boolean b) {
		for(Component c : getComponents()) c.setEnabled(b);
	}
	
	public void setVisibleStop(boolean b) {
		stopBtn.setVisible(b);
	}
	
	public void setVisibleRun(boolean b) {
		runBtn.setVisible(b);
	}

}

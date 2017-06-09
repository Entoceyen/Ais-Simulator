package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.OptionsFrame;
import view.OptionsPanel;

/**
 * Contrôleur listener à l'écoute des actions utilisateur sur la vue options
 */
public class OptionsListener implements ActionListener, ChangeListener {
	
	private OptionsPanel panel;
	private OptionsFrame frame;
	
	public OptionsListener() {
	}
	
	public void setPanel(OptionsPanel panel) {
		this.panel = panel;
	}
	
	public void setFrame(OptionsFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Ok")) {
			frame.setVisible(false);
		}
	}

	/**
	 * Lorsque le slider est déplacé, la taille de police est changée dynamiquement
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		int size = (int)source.getValue();
		frame.getApplication().setFont(size);
		panel.setFontSize();
	}

}

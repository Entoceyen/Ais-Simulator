package view;

import javax.swing.JPanel;
import javax.swing.JSlider;

import controler.OptionsListener;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JButton;

/**
 * Vue concernant les options de modification visuelles (actuellement taille de la police)
 */
public class OptionsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel lblFontSize;
	private JSlider slider;
	private JButton btnOk;
	
	public OptionsPanel(OptionsListener listener) {
		setLayout(new GridLayout(0, 1, 0, 0));
		
		lblFontSize = new JLabel("Taille de la police : "+getFont().getSize());
		add(lblFontSize);
		
		slider = new JSlider();
		slider.setMaximum(40);
		slider.setMinimum(5);
		slider.addChangeListener(listener);
		add(slider);
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(listener);
		add(btnOk);
	}
	
	/**
	 * Actualise les éléments d'affichage par rapport à la taille de la police
	 */
	public void setFontSize() {
		lblFontSize.setText("Taille de la police : "+getFont().getSize());
		slider.setValue(getFont().getSize());
	}

}

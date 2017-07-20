package view;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import controler.ManualDelayMessageListener;

/**
 * Vue et fenêtre correspond au formulaire du scénario de changement de la fréquence d'émission des messages
 */
public class ManualDelayMessageFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JSpinner spinner1, spinner5;
	private JButton btnOk, btnCancel;
		
	public ManualDelayMessageFrame(ManualDelayMessageListener listener) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width / 3;
		int height = screenSize.height / 4;
        int xpos = (screenSize.width - width) / 2;
        int ypos = (screenSize.height - height) / 2;
        setBounds(xpos, ypos, width, height);
        
		getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblFrquencePourLe = new JLabel("Fr\u00E9quence pour le message 1");
		getContentPane().add(lblFrquencePourLe);
		
		spinner1 = new JSpinner();
		spinner1.setModel(new SpinnerNumberModel(0, 0, 1200, 1));
		getContentPane().add(spinner1);
		
		JLabel lblFrqueb = new JLabel("Fr\u00E9quence pour le message 5");
		getContentPane().add(lblFrqueb);
		
		spinner5 = new JSpinner();
		spinner5.setModel(new SpinnerNumberModel(0, 0, 1200, 1));
		getContentPane().add(spinner5);
		
		btnCancel = new JButton("Retour");
		btnCancel.setName("Cancel");
		btnCancel.addActionListener(listener);
		getContentPane().add(btnCancel);
		
		btnOk = new JButton("Ok");
		btnOk.setName("Ok");
		btnOk.addActionListener(listener);
		getContentPane().add(btnOk);
		
		setVisible(false);
	}

	public int getDelayMsg1() {
		return (int)spinner1.getValue();
	}
	
	public int getDelayMsg5() {
		return (int)spinner5.getValue();
	}
}

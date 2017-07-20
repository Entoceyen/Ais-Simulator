package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.Simulation;
import model.scenario.ChangeDelayMessageScenario;
import view.ManualDelayMessageFrame;

/**
 * Contrôleur listener permettant de créer le scénario ChangeDelayMessageScenario
 */
public class ManualDelayMessageListener implements ActionListener {

	private Simulation simulation;
	private ManualDelayMessageFrame frame;
	
	public ManualDelayMessageListener(Simulation simulation) {
		this.simulation = simulation;
	}
	
	public void setFrame(ManualDelayMessageFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(((JButton)e.getSource()).getName().equals("Cancel")) {
			frame.setVisible(false);
		}
		if(((JButton)e.getSource()).getName().equals("Ok")) {
			ChangeDelayMessageScenario delayMessageScenario = new ChangeDelayMessageScenario(frame.getDelayMsg1(), frame.getDelayMsg5());
			simulation.addScenario(delayMessageScenario);
			frame.setVisible(false);
		}
	}

}

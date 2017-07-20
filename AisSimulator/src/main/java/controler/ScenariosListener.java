package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import model.scenario.Scenario;
import model.scenario.Scenario.Scenarios;
import view.ScenarioFormFrame;
import view.ScenariosPanel;

/**
 * Contrôleur listener permettant de contruire le formulaire pour un scénario choisi
 */
public class ScenariosListener implements ActionListener {

	private ScenariosPanel panel;
	private ScenarioFormFrame scenarioFrame;
	
	public void setPanel(JPanel panel) {
		this.panel = (ScenariosPanel) panel;
	}
	
	public void setScenarioFrame(ScenarioFormFrame frame) {
		this.scenarioFrame = frame;
	}
	
	public void setEnablePanel(boolean b) {
		panel.setActive(b);
	}
	
	public Scenarios[] getScenarios() {
		return Scenario.getScenarios();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String scenarioLbl = e.getActionCommand();
		Scenarios s = Scenarios.getScenariosByLabel(scenarioLbl);
		scenarioFrame.buildForm(s);
	}

}

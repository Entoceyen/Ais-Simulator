package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import model.Simulation;
import model.scenario.BadMIDScenario;
import model.scenario.BadPositionScenario;
import model.scenario.ChangeDataScenario;
import model.scenario.ChangeSpeedScenario;
import model.scenario.DeAisScenario;
import model.scenario.GhostScenario;
import model.scenario.PathOnGroundScenario;
import model.scenario.Scenario;
import model.scenario.Scenario.Scenarios;
import model.scenario.TeleportScenario;
import model.scenario.VesselSameIDScenario;
import view.ScenarioFormFrame;
import view.ScenariosPanel;

public class ScenariosListener implements ActionListener {

	private ScenariosPanel panel;
	private Simulation simulation;
	private ScenarioFormFrame scenarioFrame;
	
	public ScenariosListener(Simulation simulation) {
		this.simulation = simulation;
	}
	
	public void setPanel(JPanel panel) {
		this.panel = (ScenariosPanel) panel;
	}
	
	public void setScenarioFrame(ScenarioFormFrame frame) {
		this.scenarioFrame = frame;
	}
	
	public String[] getScenarios() {
		return Scenario.getScenarios();
	}
	
	public Scenarios[] getScenarios2() {
		return Scenario.getScenarios2();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String scenarioLbl = e.getActionCommand();
		Scenarios s = Scenarios.getScenariosByLabel(scenarioLbl);
		switch(s) {
		case BadMIDScenario:
			scenarioFrame.buildForm(BadMIDScenario.getDataType());
			break;
		case BadPositionScenario:
			scenarioFrame.buildForm(BadPositionScenario.getDataType());
			break;
		case ChangeDataScenario:
			scenarioFrame.buildForm(ChangeDataScenario.getDataType());
			break;
		case ChangeSpeedScenario:
			scenarioFrame.buildForm(ChangeSpeedScenario.getDataType());
			break;
		case DeAisScenario:
			scenarioFrame.buildForm(DeAisScenario.getDataType());
			break;
		case GhostScenario:
			scenarioFrame.buildForm(GhostScenario.getDataType());
			break;
		case PathOnGroundScenario:
			scenarioFrame.buildForm(PathOnGroundScenario.getDataType());
			break;
		case TeleportScenario:
			scenarioFrame.buildForm(TeleportScenario.getDataType());
			break;
		case VesselSameIDScenario:
			scenarioFrame.buildForm(VesselSameIDScenario.getDataType());
			break;
		default:break;
		}
	}

}

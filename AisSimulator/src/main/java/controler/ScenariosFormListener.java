package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import model.Coordinate;
import model.Simulation;
import model.scenario.BadMIDScenario;
import model.scenario.BadPositionScenario;
import model.scenario.ChangeDataScenario;
import model.scenario.ChangeSpeedScenario;
import model.scenario.DataEnum;
import model.scenario.DeAisScenario;
import model.scenario.GhostScenario;
import model.scenario.PathOnGroundScenario;
import model.scenario.Scenario;
import model.scenario.TeleportScenario;
import model.scenario.VesselSameIDScenario;
import view.PopupManager;
import view.ScenarioFormFrame;

/**
 * Contrôleur listener permettant de créé un scénario en fonction de celui choisi et de re-calculer la simulation si necessaire
 */
public class ScenariosFormListener implements ActionListener {

	private Simulation simulation;
	private ScenarioFormFrame frame;
	private TimedSimulationListener timedSimuListener;
	
	public ScenariosFormListener(Simulation simulation) {
		this.simulation = simulation;
	}
	
	public void setFrame(ScenarioFormFrame frame) {
		this.frame = frame;
	}
	
	public void setTimedSimuListener(TimedSimulationListener timedlistener) {
		timedSimuListener = timedlistener;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if(((JButton)event.getSource()).getName().equals("Cancel")) {
			frame.setVisible(false);
		}
		if(((JButton)event.getSource()).getName().equals("Ok")) {
			int startTime = 0, duration = 0;
			Scenario scenario = null;
			try {
				switch(frame.getScenario()) {
				case BadMIDScenario:
					startTime = Integer.parseInt(((JTextField)frame.getComponentByName("arrivalTime")).getText());
					duration = Integer.parseInt(((JTextField)frame.getComponentByName("duration")).getText());
					scenario = new BadMIDScenario(startTime, duration);
					break;
				case BadPositionScenario:
					startTime = Integer.parseInt(((JTextField)frame.getComponentByName("arrivalTime")).getText());
					duration = Integer.parseInt(((JTextField)frame.getComponentByName("duration")).getText());
					scenario = new BadPositionScenario(startTime, duration);
					break;
				case ChangeDataScenario:
					startTime = Integer.parseInt(((JTextField)frame.getComponentByName("arrivalTime")).getText());
					duration = Integer.parseInt(((JTextField)frame.getComponentByName("duration")).getText());
					DataEnum type = (DataEnum) ((JComboBox<?>)frame.getComponentByName("dataType")).getSelectedItem();
					Object value = (type.getType() instanceof Object[] ? ((JComboBox<?>)frame.getComponentByName("value")).getSelectedItem() : ((JTextField)frame.getComponentByName("value")).getText());
					scenario = new ChangeDataScenario(startTime, duration, type, value);
					break;
				case ChangeSpeedScenario:
					startTime = Integer.parseInt(((JTextField)frame.getComponentByName("arrivalTime")).getText());
					double speed = Double.parseDouble(((JTextField)frame.getComponentByName("speed")).getText());
					scenario = new ChangeSpeedScenario(startTime, speed);
					break;
				case GhostScenario:
					startTime = Integer.parseInt(((JTextField)frame.getComponentByName("arrivalTime")).getText());
					duration = Integer.parseInt(((JTextField)frame.getComponentByName("duration")).getText());
					scenario = new GhostScenario(startTime, duration);
					break;
				case TeleportScenario:
					startTime = Integer.parseInt(((JTextField)frame.getComponentByName("arrivalTime")).getText());
					duration = Integer.parseInt(((JTextField)frame.getComponentByName("duration")).getText());
					scenario = new TeleportScenario(startTime, duration);
					break;
				case VesselSameIDScenario:
					startTime = Integer.parseInt(((JTextField)frame.getComponentByName("arrivalTime")).getText());
					duration = Integer.parseInt(((JTextField)frame.getComponentByName("duration")).getText());
					scenario = new VesselSameIDScenario(startTime, duration);
					break;
				case DeAisScenario:
					Coordinate c = new Coordinate(
										Double.parseDouble(((JTextField)frame.getComponentByName("latitude")).getText()),
										Double.parseDouble(((JTextField)frame.getComponentByName("longitude")).getText())
									);
					int length = Integer.parseInt(((JTextField)frame.getComponentByName("length")).getText());
					if(length < 1000) length = 1000;
					scenario = new DeAisScenario(c, length);
					break;
				case PathOnGroundScenario:
					String path = (String) ((JComboBox<?>)frame.getComponentByName("paths")).getSelectedItem();
					scenario = new PathOnGroundScenario(PathOnGroundScenario.getPathByName(path));
					break;
				default:break;
				}
			} catch(NumberFormatException nfe) {
				PopupManager.errorMessage("Erreur de saisie", nfe.getMessage());
			}
			
			if(startTime < 0 || startTime >= simulation.getSize()-1) {
				scenario = null;
				PopupManager.errorMessage("Erreur de saisie", "La seconde saisie n'est pas valide. Doit être compris entre 0 et "+(simulation.getSize()-1));
			}
			if(duration < 0 || duration >= simulation.getSize()-1-startTime) {
				scenario = null;
				PopupManager.errorMessage("Erreur de saisie", "La durée saisie n'est pas valide. Doit être compris entre 0 et "+(simulation.getSize()-1-startTime));
			}
			
			if(scenario != null) {
				simulation.addScenario(scenario);
				if(scenario.isCompute()) {
					simulation.compute(scenario.getStartTime());
					timedSimuListener.setDuration(simulation.getSize()-1);
				} else scenario.apply();
			}
			frame.setVisible(false);
		}
	}

}

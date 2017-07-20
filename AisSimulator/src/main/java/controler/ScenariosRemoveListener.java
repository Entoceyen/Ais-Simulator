package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Simulation;
import model.scenario.Scenario;

/**
 * Contrôleur listener permettant de supprimer un scenario choisi et de recalculer la simulation si necessaire
 */
public class ScenariosRemoveListener implements ActionListener {

	private Scenario scenario;
	private Simulation simulation;
	private TimedSimulationListener timedSimuListener;
	
	public ScenariosRemoveListener(Simulation simulation) {
		this.simulation = simulation;
	}
	
	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}
	
	public void setTimedSimuListener(TimedSimulationListener timedSimuListener) {
		this.timedSimuListener = timedSimuListener;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		scenario.remove();
		if(scenario.isCompute()) {
			simulation.compute(scenario.getStartTime());
			timedSimuListener.setDuration(simulation.getSize()-1);
		}
	}

}

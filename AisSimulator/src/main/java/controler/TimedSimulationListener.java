package controler;

import java.util.ArrayList;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Simulation;
import model.scenario.Scenario;
import view.DataPreviewPanel;
import view.TimedSimulationPanel;

/**
 * Contrôleur listener permettant de modifier la vue de prévisualisation en fonction de la position de l'indicateur dans la barre de minutage
 */
public class TimedSimulationListener implements ChangeListener {
	
	private TimedSimulationPanel panel;
	private DataPreviewPanel previewPanel;
	private Simulation simulation;
	
	public TimedSimulationListener(Simulation simulation) {
		this.simulation = simulation;
	}

	public void setPanel(TimedSimulationPanel panel) {
		this.panel = panel;
	}
	
	public void setPreviewPanel(DataPreviewPanel panel) {
		this.previewPanel = panel;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		int instant = ((JSlider)e.getSource()).getValue();
		previewPanel.setDescription("Temps : "+instant+ " s" +simulation.getInstant(instant).description());
		ArrayList<Scenario> list = new ArrayList<Scenario>();
		for(Scenario s : simulation.getScenarios())
			if(s.getStartTime() <= instant && instant <= s.getStartTime()+s.getDuration())
				list.add(s);
		Scenario[] scenarios = new Scenario[list.size()];
		for(int i=0 ; i<list.size() ; i++) scenarios[i] = list.get(i);
		previewPanel.setListScenario(list.toArray(scenarios));
	}
	
	public void setDuration(int sec) {
		panel.setDuration(sec);
	}
}

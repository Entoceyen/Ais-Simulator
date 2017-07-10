package controler;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Simulation;
import view.TimedSimulationPanel;

public class TimedSimulationListener implements ChangeListener {
	
	private TimedSimulationPanel panel;
	private Simulation simulation;
	
	public TimedSimulationListener(Simulation simulation) {
		this.simulation = simulation;
	}

	public void setPanel(JPanel panel) {
		this.panel = (TimedSimulationPanel) panel;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		int instant = ((JSlider)e.getSource()).getValue();
		System.out.println(simulation.getInstant(instant));
	}
	
	public void setDuration(int sec) {
		panel.setDuration(sec);
	}
}

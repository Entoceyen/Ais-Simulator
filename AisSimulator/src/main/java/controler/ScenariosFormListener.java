package controler;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import model.Simulation;
import view.ScenarioFormFrame;

public class ScenariosFormListener implements ActionListener {

	private Simulation simulation;
	private ScenarioFormFrame frame;
	private HashMap<String, Object> dataType;
	
	public ScenariosFormListener(Simulation simulation) {
		this.simulation = simulation;
	}
	
	public void setFrame(ScenarioFormFrame frame) {
		this.frame = frame;
	}
	
	public void setDataType(HashMap<String, Object> dataType) {
		this.dataType = dataType;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(((JButton)event.getSource()).getName().equals("Cancel")) {
			frame.setVisible(false);
		}
		if(((JButton)event.getSource()).getName().equals("Ok")) {
			dataType.get("SCENARIO");
			for(Component c : frame.getComponents()) {
				if(c instanceof JComboBox) {
					String s = (String) ((JComboBox<?>) c).getSelectedItem();
				} else if(c instanceof JTextField) {
					if(dataType.get(c.getName()).equals(Integer.class)) {}
				}
			}
		}
	}

}

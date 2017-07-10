package view;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import controler.ScenariosListener;
import model.scenario.Scenario.Scenarios;

public class ScenariosPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public ScenariosPanel(ScenariosListener listener) {
		setLayout(new GridLayout(0, 1));
		TitledBorder border = BorderFactory.createTitledBorder("Scenarios");
		setBorder(border);
		for(Scenarios s : listener.getScenarios2()) {
			JButton scenarioBtn = new JButton(s.getLabel());
			scenarioBtn.setName(s.getLabel());
			scenarioBtn.addActionListener(listener);
			add(scenarioBtn);
		}
	}

}

package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import controler.ScenariosListener;
import model.scenario.Scenario.Scenarios;

/**
 * Vue affichant la liste des boutons d'ajout des scénarios
 */
public class ScenariosPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public ScenariosPanel(ScenariosListener listener) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setMinimumSize(new Dimension((int)(screenSize.getWidth()/10),(int)screenSize.getHeight()/4));
		setLayout(new GridLayout(0, 1, 0, (int) (screenSize.getHeight()/100)));
		TitledBorder border = BorderFactory.createTitledBorder("Sc\u00E9narios");
		setBorder(border);
		for(Scenarios s : listener.getScenarios()) {
			JButton scenarioBtn = new JButton(s.getLabel());
			scenarioBtn.setName(s.getLabel());
			scenarioBtn.addActionListener(listener);
			add(scenarioBtn);
		}
		setActive(false);
	}
	
	public void setActive(boolean b) {
		for(Component c : getComponents()) c.setEnabled(b);
	}

}

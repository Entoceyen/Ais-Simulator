package view;

import javax.swing.JPanel;
import javax.swing.JTextPane;

import controler.ScenariosRemoveListener;
import model.scenario.ChangeSpeedScenario;
import model.scenario.Scenario;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Vue affichant la zone de prévisualisation des données pour une seconde de la simulation
 */
public class DataPreviewPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JPanel scenarioList;
	private JTextPane descriptionPane;
	private String description = "";
	private ScenariosRemoveListener scenarioRemoveListener;
//	private int fontSize;
	
	public DataPreviewPanel(ScenariosRemoveListener scenarioRemoveListener) {
		this.scenarioRemoveListener = scenarioRemoveListener;
		setLayout(new BorderLayout());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setMinimumSize(new Dimension((int)(screenSize.getWidth()/10),(int)screenSize.getHeight()/4));
		descriptionPane = new JTextPane();
		descriptionPane.setEditable(false);
		descriptionPane.setContentType("text/html");
		add(descriptionPane, BorderLayout.CENTER);
		
		scenarioList = new JPanel();
		scenarioList.setLayout(new GridLayout(0, 2, 5, 5));
		add(scenarioList,BorderLayout.SOUTH);
	}
	
	public void setDescription(String text) {
		description = text;
		descriptionPane.setText("<html>"+description+"</html>");
	}
	
	public void setListScenario(Scenario[] s) {
		scenarioList.removeAll();
		for(Scenario scenario : s) {
			if(scenario instanceof ChangeSpeedScenario) continue;
			scenarioList.add(new JLabel(scenario.description()));
			JButton removeBtn = new JButton("Supprimer");
			scenarioRemoveListener.setScenario(scenario);
			removeBtn.addActionListener(scenarioRemoveListener);
			scenarioList.add(removeBtn);
		}
	}
	
//	@Override
//	public void setFont(Font f) {
//		super.setFont(f);
//		fontSize = f.getSize();
//		if(descriptionPane != null)
//			descriptionPane.setText("<html><font size='"+fontSize+"'>"+description+"</font></html>");
//	}
	
}

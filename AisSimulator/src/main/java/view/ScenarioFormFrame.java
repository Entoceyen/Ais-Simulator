package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controler.ScenariosFormListener;
import model.scenario.DataEnum;
import model.scenario.PathOnGroundScenario;
import model.scenario.Scenario.Scenarios;

/**
 * Vue et fenêtre affichant le formulaire correspondant à l'ajout d'un scénario
 */
public class ScenarioFormFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JButton btnOk;
	private JButton btnCancel;
	private Scenarios scenario;

	public ScenarioFormFrame(ScenariosFormListener listener) {
		setTitle("Nouveau sc\u00E9nario");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width / 3;
		int height = screenSize.height / 4;
        int xpos = (screenSize.width - width) / 2;
        int ypos = (screenSize.height - height) / 2;
        setBounds(xpos, ypos, width, height);
        
		btnOk = new JButton("Ok");
		btnOk.setName("Ok");
		btnOk.addActionListener(listener);
		btnCancel = new JButton("Annuler");
		btnCancel.setName("Cancel");
		btnCancel.addActionListener(listener);
		setLayout(new GridLayout(0, 2));
		setVisible(false);
		
		addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	setVisible(false);
            }
        });
	}
	
	public Scenarios getScenario() {
		return scenario;
	}
	
	/**
	 * Contruit le formulaire correspondant à un scénario choisi
	 * @param scenario
	 */
	public void buildForm(Scenarios scenario) {
		resetForm();
		this.scenario = scenario;
		JLabel arrivalTimeLbl = new JLabel("Moment d'arriv\u00E9e (second)");
		JTextField arrivalTime = new JTextField();
		arrivalTime.setName("arrivalTime");
		JLabel durationLbl = new JLabel("Dur\u00E9e (second)");
		JTextField duration = new JTextField();
		duration.setName("duration");
		switch(scenario) {
		case BadMIDScenario:
			add(arrivalTimeLbl);
			add(arrivalTime);
			add(durationLbl);
			add(duration);
			break;
		case BadPositionScenario:
			add(arrivalTimeLbl);
			add(arrivalTime);
			add(durationLbl);
			add(duration);
			break;
		case ChangeDataScenario:
			add(arrivalTimeLbl);
			add(arrivalTime);
			add(durationLbl);
			add(duration);
			add(new JLabel("Donn\u00E9e"));
			JComboBox<DataEnum> list = new JComboBox<DataEnum>(DataEnum.values());
			list.addActionListener(new ActionListener() {
				private Component c;
				private JLabel lbl;
				@Override
				public void actionPerformed(ActionEvent e) {
					if(c != null) { remove(c); remove(lbl); }
					DataEnum data = (DataEnum) ((JComboBox<?>)e.getSource()).getSelectedItem();
					add(lbl = new JLabel(data.getLabel()));
					if((data.getType()) instanceof Object[]) {
						add(c = new JComboBox<>((Object[])data.getType()));
					} else {
						c = new JTextField();
						add(c);
					}
					c.setName("value");
					revalidate();
				}
			});
			list.setName("dataType");
			add(list);
			break;
		case ChangeSpeedScenario:
			add(arrivalTimeLbl);
			add(arrivalTime);
			add(new JLabel("Vitesse (kts)"));
			JTextField speed = new JTextField();
			speed.setName("speed");
			add(speed);
			break;
		case GhostScenario:
			add(arrivalTimeLbl);
			add(arrivalTime);
			add(durationLbl);
			add(duration);
			break;
		case TeleportScenario:
			add(arrivalTimeLbl);
			add(arrivalTime);
			add(durationLbl);
			add(duration);
			break;
		case VesselSameIDScenario:
			add(arrivalTimeLbl);
			add(arrivalTime);
			add(durationLbl);
			add(duration);
			break;
		case DeAisScenario:
			add(new JLabel("Latitude (d\u00E9cimale)"));
			JTextField lat = new JTextField();
			lat.setName("latitude");
			add(lat);
			add(new JLabel("Longitude (d\u00E9cimale)"));
			JTextField lon = new JTextField();
			lon.setName("longitude");
			add(lon);
			add(new JLabel("Longueur totale (metre))"));
			JTextField length = new JTextField();
			length.setName("length");
			add(length);
			break;
		case PathOnGroundScenario:
			add(new JLabel("Trajet \u00E9 terre"));
			JComboBox<String> paths = new JComboBox<String>(PathOnGroundScenario.getPathsOnGround());
			paths.setName("paths");
			add(paths);
			break;
		default:break;
		}
		
		add(btnCancel);
		add(btnOk);
		setVisible(true);
	}
	
	/**
	 * Réinitialise le formulaire
	 */
	public void resetForm() {
		scenario = null;
		for(Component c : getContentPane().getComponents()) {
			if(c instanceof JButton) continue;
			remove(c);
		}
	}
	
	public Component getComponentByName(String name) {
		for(Component c : getContentPane().getComponents())
			if(c.getName() != null && c.getName().equals(name)) return c;
		return null;
	}

}

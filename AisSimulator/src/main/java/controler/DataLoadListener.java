package controler;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import dk.dma.ais.message.NavigationalStatus;
import dk.dma.ais.message.ShipTypeCargo;
import model.StaticData;
import model.datavessel.AisVersion;
import model.datavessel.PositionFixType;
import model.datavessel.SpecialManIndicator;
import model.Simulation;
import model.DynamicData;
import model.PositionReference;
import view.FormPanel;
import view.PopupManager;

/**
 * Contrôleur listener permettant de vérifier et d'enregister en mémoire les données depuis le formulaire
 */
public class DataLoadListener extends DataManager implements ActionListener {
	
	private TimedSimulationListener timedSimuListener;
	private ScenariosListener scenariosListener;
	private AisStreamListener streamListener;
	
	public DataLoadListener(Simulation simulation) {
		super(simulation);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StaticData staticData = new StaticData();
		DynamicData dynData = new DynamicData();
		PositionReference posRef = new PositionReference();
		
		String errors = "";
		for(Component c : panel.getComponents()) {
			if(c instanceof JTextField) {
				String value = ((JTextField)c).getText();
				String name = c.getName();
				try {
					switch(name) {
					case "mmsi":
						if(value.equals("")) value = "0";
						else if(Integer.parseInt(value) < 0) {
							errors += incorrectValue(name, value);
							break;
						}
						staticData.setMmsi(Integer.parseInt(value));
						break;
					case "imo":
						if(value.equals("")) value = "0";
						else if(Integer.parseInt(value) < 0) {
							errors += incorrectValue(name, value);
							break;
						}
						staticData.setImo(Integer.parseInt(value));
						break;
					case "callSign":
						if(value.equals("")) value = "@@@@@@@";
						else if(value.length() != 7) {
							errors += incorrectValue(name, value);
							break;
						}
						staticData.setCallSign(value);
						break;
					case "name":
						if(value.equals("")) value = "@@@@@@@@@@@@@@@@@@@@";
						else if(value.length() > 20) {
							errors += incorrectValue(name, value);
							break;
						}
						staticData.setName(value);
						break;
					case "dimBow":
						if(value.equals("")) value = "0";
						if(Integer.parseInt(value) < 0) {
							errors += incorrectValue(name, value);
							break;
						}
						if(Integer.parseInt(value) > 511) value = "511";
						posRef.setDimBow(Integer.parseInt(value));
						break;
					case "dimStern":
						if(value.equals("")) value = "0";
						if(Integer.parseInt(value) < 0) {
							errors += incorrectValue(name, value);
							break;
						}
						if(Integer.parseInt(value) > 511) value = "511";
						posRef.setDimStern(Integer.parseInt(value));
						break;
					case "dimPort":
						if(value.equals("")) value = "0";
						if(Integer.parseInt(value) < 0) {
							errors += incorrectValue(name, value);
							break;
						}
						if(Integer.parseInt(value) > 63) value = "63";
						posRef.setDimPort(Integer.parseInt(value));
						break;
					case "dimStarboard":
						if(value.equals("")) value = "0";
						if(Integer.parseInt(value) < 0) {
							errors += incorrectValue(name, value);
							break;
						}
						if(Integer.parseInt(value) > 63) value = "63";
						posRef.setDimStarboard(Integer.parseInt(value));
						break;
					case "draught":
						if(value.equals("")) value = "0";
						if(Double.parseDouble(value) < 0) {
							errors += incorrectValue(name, value);
							break;
						}
						if(Double.parseDouble(value) > 25.5) value = "25.5";
						staticData.setDraught((int)(Double.parseDouble(value)*10));
						break;
					case "destination":
						if(value.equals("")) value = "@@@@@@@@@@@@@@@@@@@@";
						else if(value.length() > 20) {
							errors += incorrectValue(name, value);
							break;
						}
						staticData.setDestination(value);
						break;
					case "speed":
						if(value.equals("") || Double.parseDouble(value) < 0) {
							errors += incorrectValue(name, value);
							break;
						}
						if(Double.parseDouble(value) > 102.2) value = "102.2";
						dynData.setSpeed((int)(Double.parseDouble(value)*10));
						break;
					default:
					}
				} catch (NumberFormatException nbfe) {
					errors += "un nombre est attendu pour le champ "+name+". '"+value+"' trouvé\n";
					continue;
				}
			} else if(c instanceof JComboBox) {
				String value = (String) ((JComboBox<?>)c).getSelectedItem();
				int id = Integer.parseInt(value.split(" ")[0]);
				switch(c.getName()) {
				case "aisVersion":
					staticData.setAisVersion(AisVersion.getByID(id));
					break;
				case "shipType":
					staticData.setShipType(new ShipTypeCargo(id));
					break;
				case "posAcc":
					dynData.setPosAcc(id);
					break;
				case "posType":
					staticData.setPosType(PositionFixType.getByID(id));
					break;
				case "dte":
					if(id == 1) staticData.setDte(false);
					else staticData.setDte(true);
				case "navStat":
					dynData.setNavStat(NavigationalStatus.get(id));
					break;
				case "specialManIndicator":
					dynData.setSpecialManIndicator(SpecialManIndicator.getByID(id));
					break;
				default:
				}
			}
		}
		
		if(errors.length() > 0) {
			PopupManager.errorMessage("Chargement des données", errors);
			return;
		}
		
		staticData.setPosRef(posRef);
		dynData.setRaim(0);
		
		Calendar c = panel.getDateTime();
		
		try {
			simulation.init(staticData, dynData, c);
		} catch (Exception e1) {
			PopupManager.errorMessage("trajet manquant", "Veuillez saisir un trajet");
			return;
		}
		timedSimuListener.setDuration(simulation.getSize()-1);
		
		// DataPanel et PathPanel non éditable
		((FormPanel) panel.getParent()).disable();
		scenariosListener.setEnablePanel(true);
		streamListener.setEnablePanel(true);
		
		System.out.println(simulation.getSize()+"\n"+simulation);
	}
	
	/**
	 * Message d'erreur de champ de texte classique
	 * @param name nom du champ
	 * @param value valeur du champ
	 * @return le message d'erreur
	 */
	private String incorrectValue(String name, String value) {
		return "La valeur '"+value+"' est incorrecte pour le champ '"+name+"'\n";
	}

	public void setTimedSimulationListener(TimedSimulationListener timedSimuListener) {
		this.timedSimuListener = timedSimuListener;
	}
	
	public void setScenariosListener(ScenariosListener listener) {
		this.scenariosListener = listener;
	}

	public void setStreamListener(AisStreamListener listener) {
		this.streamListener = listener;
	}
}

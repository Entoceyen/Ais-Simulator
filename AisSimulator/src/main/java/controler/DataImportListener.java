package controler;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import dk.dma.ais.message.NavigationalStatus;
import model.Simulation;
import model.datavessel.AisVersion;
import model.datavessel.PositionFixType;
import model.datavessel.SpecialManIndicator;
import model.io.DataIO;
import view.PopupManager;

/**
 * Contrôleur listener permettant de vérifier et d'importer les données depuis un fichier
 */
public class DataImportListener extends DataManager implements ActionListener {

	public DataImportListener(Simulation simulation) {
		super(simulation);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		File f = panel.getDataFile();
		HashMap<String,String> data;
		try {
			data = DataIO.readDatas(f);
		} catch (Exception e1) {
			PopupManager.errorMessage("Lecture du fichier", "Une erreur de lecture du fichier est survenue.\n"+e1.getMessage());
			return;
		}
		String error = "";
		for(Component c : panel.getComponents()) {
			String name = c.getName();
			String value = data.get(name);
			if((c instanceof JTextField || c instanceof JComboBox) && value == null) {
				error += "Erreur dans le contenu du fichier : champ "+name+" introuvable\n";
				continue;
			}
			try {
				if(c instanceof JTextField) {
					switch(name) {
					case "mmsi":
						if(value.equals("0")) value = "";
						else if(Integer.parseInt(value) < 0) {
							error += incorrectValue(name, value);
							break;
						}
						((JTextField) c).setText(value);
						break;
					case "imo":
						if(value.equals("0")) value = "";
						else if(Integer.parseInt(value) < 0) {
							error += incorrectValue(name, value);
							break;
						}
						((JTextField) c).setText(value);
						break;
					case "callSign":
						if(value.equals("@@@@@@@")) value = "";
						else if(value.length() != 7) {
							error += incorrectValue(name, value);
							break;
						}
						((JTextField) c).setText(value);
						break;
					case "name":
						if(value.equals("@@@@@@@@@@@@@@@@@@@@")) value = "";
						else if(value.length() > 20) {
							error += incorrectValue(name, value);
							break;
						}
						((JTextField) c).setText(value);
						break;
					case "dimBow":
						if(value.equals("0")) value = "";
						if(Integer.parseInt(value) < 0) {
							error += incorrectValue(name, value);
							break;
						}
						((JTextField) c).setText(value);
						break;
					case "dimStern":
						if(value.equals("0")) value = "";
						if(Integer.parseInt(value) < 0) {
							error += incorrectValue(name, value);
							break;
						}
						((JTextField) c).setText(value);
						break;
					case "dimPort":
						if(value.equals("0")) value = "";
						if(Integer.parseInt(value) < 0) {
							error += incorrectValue(name, value);
							break;
						}
						((JTextField) c).setText(value);
						break;
					case "dimStarboard":
						if(value.equals("0")) value = "";
						if(Integer.parseInt(value) < 0) {
							error += incorrectValue(name, value);
							break;
						}
						((JTextField) c).setText(value);
						break;
					case "draught":
						if(value.equals("0")) value = "";
						if(Double.parseDouble(value) < 0) {
							error += incorrectValue(name, value);
							break;
						}
						((JTextField) c).setText(value);
						break;
					case "destination":
						if(value.equals("@@@@@@@@@@@@@@@@@@@@")) value = "";
						else if(value.length() > 20) {
							error += incorrectValue(name, value);
							break;
						}
						((JTextField) c).setText(value);
						break;
					case "speed":
						if(value.equals("") && Double.parseDouble(value) < 0) {
							error += incorrectValue(name, value);
							break;
						}
						((JTextField) c).setText(String.valueOf((Double.parseDouble(value)/10)));
						break;
					default:
					}
				} else if(c instanceof JComboBox) {
					int id = Integer.parseInt(value);
					switch(c.getName()) {
					case "aisVersion":
						if(id < 0 || id > 3) {
							error += comboBoxOutOfRange(name,id);
							break;
						}
						((JComboBox<?>) c).setSelectedItem(AisVersion.getByID(id).toString());
						break;
					case "shipType":
						if(id < 20 || id > 99 && id != 0)  {
							error += comboBoxOutOfRange(name,id);
							break;
						}
						((JComboBox<?>) c).setSelectedItem(getShipTypeByID(id));
						break;
					case "posAcc":
						if(id < 0 || id > 1)  {
							error += comboBoxOutOfRange(name,id);
							break;
						}
						((JComboBox<?>) c).setSelectedItem(getPosAccByID(id));
						break;
					case "posType":
						if(id < 0 || id > 8 && id != 15)  {
							error += comboBoxOutOfRange(name,id);
							break;
						}
						((JComboBox<?>) c).setSelectedItem(PositionFixType.getByID(id).toString());
						break;
					case "dte":
						if(id < 0 || id > 1)  {
							error += comboBoxOutOfRange(name,id);
							break;
						}
						((JComboBox<?>) c).setSelectedItem(getDteByID(id));
					case "navStat":
						if((id < 0 || id > 8) && (id < 14 || id > 15))  {
							error += comboBoxOutOfRange(name,id);
							break;
						}
						String navStat = NavigationalStatus.get(id).getCode() +" - "+ NavigationalStatus.get(id).toString();
						((JComboBox<?>) c).setSelectedItem(navStat);
						break;
					case "specialManIndicator":
						if(id < 0 || id > 2)  {
							error += comboBoxOutOfRange(name,id);
							break;
						}((JComboBox<?>) c).setSelectedItem(SpecialManIndicator.getByID(id).toString());
						break;
					default:
					}
				}
			} catch (NumberFormatException nbfe) {
				error += "Erreur dans le contenu du fichier : un nombre est attendu pour le champ "+name+". '"+value+"' trouv�\n";
				continue;
			}
		}
		if(error.length() > 0) PopupManager.errorMessage("Importation", error);
	}
	
	/**
	 * Message d'erreur de liste déroulante
	 * @param name nom du champ
	 * @param id valeur du champ
	 * @return le message d'erreur
	 */
	private String comboBoxOutOfRange(String name, int id) {
		return "Erreur dans le contenu du fichier : id "+id+" incorrecte pour le champ '"+name+"'\n";
	}
	
	/**
	 * Message d'erreur de champ de texte classique
	 * @param name nom du champ
	 * @param value valeur du champ
	 * @return le message d'erreur
	 */
	private String incorrectValue(String name, String value) {
		return "Erreur dans le contenu du fichier : valeur '"+value+"' incorrecte pour le champ '"+name+"'\n";
	}

}

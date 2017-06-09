package controler;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import model.Simulation;
import model.io.DataIO;
import view.PopupManager;

/**
 * Controleur listener permettant d'exporter les données saisies dans le formulaire sour forme d'un ficher csv
 */
public class DataExportListener extends DataManager implements ActionListener {

	public DataExportListener(Simulation simulation) {
		super(simulation);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		HashMap<String,String> data = new HashMap<String,String>();
		
		for(Component c : panel.getComponents()) {
			String name = c.getName();
			if(c instanceof JTextField) {
				String value = ((JTextField)c).getText();
				switch(name) {
				case "mmsi":
					if(value.equals("")) value = "0";
					else if(Integer.parseInt(value) < 0) break; //message erreur
					data.put(name, value);
					break;
				case "imo":
					if(value.equals("")) value = "0";
					else if(Integer.parseInt(value) < 0) break; //message erreur
					data.put(name, value);
					break;
				case "callSign":
					if(value.equals("")) value = "@@@@@@@";
					else if(value.length() != 7) break; //message erreur
					data.put(name, value);
					break;
				case "name":
					if(value.equals("")) value = "@@@@@@@@@@@@@@@@@@@@";
					else if(value.length() > 20) break; //message erreur
					data.put(name, value);
					break;
				case "dimBow":
					if(value.equals("")) value = "0";
					if(Integer.parseInt(value) < 0) break; //message erreur
					if(Integer.parseInt(value) > 511) value = "511";
					data.put(name, value);
					break;
				case "dimStern":
					if(value.equals("")) value = "0";
					if(Integer.parseInt(value) < 0) break; //message erreur
					if(Integer.parseInt(value) > 511) value = "511";
					data.put(name, value);
					break;
				case "dimPort":
					if(value.equals("")) value = "0";
					if(Integer.parseInt(value) < 0) break; //message erreur
					if(Integer.parseInt(value) > 63) value = "63";
					data.put(name, value);
					break;
				case "dimStarboard":
					if(value.equals("")) value = "0";
					if(Integer.parseInt(value) < 0) break; //message erreur
					if(Integer.parseInt(value) > 63) value = "63";
					data.put(name, value);
					break;
				case "draught":
					if(value.equals("")) value = "0";
					if(Double.parseDouble(value) < 0) break; //message erreur
					if(Double.parseDouble(value) > 25.5) value = "25.5";
					data.put(name, value);
					break;
				case "destination":
					if(value.equals("")) value = "@@@@@@@@@@@@@@@@@@@@";
					else if(value.length() > 20) break; //message erreur
					data.put(name, value);
					break;
				case "speed":
					if(value.equals("")) break; //message erreur
					if(Double.parseDouble(value) < 0) break; //message erreur
					if(Double.parseDouble(value) > 102.2) value = "102.2";
					data.put(name, String.valueOf((int) (Double.parseDouble(value)*10)));
					break;
				default:
				}
			} else if(c instanceof JComboBox) {
				String value = (String) ((JComboBox<?>)c).getSelectedItem();
				value = value.split(" ")[0];
				data.put(name, value);
			}
		}
		
		File f;
		try {
			f = DataIO.writeDatas(data);
		} catch (Exception e1) {
			PopupManager.errorMessage("Lecture du fichier", "Une erreur lors de l'éciture du fichier est survenue.\n"+e1.getMessage());
			return;
		}
		if(panel.saveDataFile(f))
			PopupManager.infoMessage("Exportation", "Le fichier a été exporté avec succès.");
		else
			PopupManager.errorMessage("Exportation", "Une erreur est survenue lors de l'exportation du fichier.");
	}

}

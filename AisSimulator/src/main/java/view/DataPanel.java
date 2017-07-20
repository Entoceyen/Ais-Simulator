package view;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import controler.DataExportListener;
import controler.DataImportListener;
import controler.DataLoadListener;
import controler.DataManager;
import model.datavessel.AisVersion;
import model.datavessel.PositionFixType;
import model.datavessel.SpecialManIndicator;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.time.LocalDate;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 * Vue correspondant au formulaire de saisie des données
 */
public class DataPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel selectLbl;
	private JButton selectBtn;
	
	private JTextField textField_mmsi;
	private JTextField textField_imo;
	private JTextField textField_callsign;
	private JTextField textField_name;
	private JTextField textField_dimStern;
	private JTextField textField_dimBow;
	private JTextField textField_dimPort;
	private JTextField textField_dimStarboard;
	private JTextField textField_draught;
	private JTextField textField_destination;
	private JTextField textField_speed;
	private JComboBox<String> comboBox_aisVersion;
	private JComboBox<String> comboBox_shipType;
	private JComboBox<String> comboBox_posFixType;
	private JComboBox<String> comboBox_posAcc;
	private JComboBox<String> comboBox_dte;
	private JComboBox<String> comboBox_navStat;
	private JComboBox<String> comboBox_specialManIndicator;
	
	private DateTimePanel dateTimePanel;
	
	private JButton exportBtn;
	private JButton validateBtn;
	
	public DataPanel(DataImportListener importListener, DataExportListener exportListener, DataLoadListener loadListener) {
		TitledBorder border = BorderFactory.createTitledBorder("Donn\u00E9es initiales");
		setBorder(border);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLayout(new GridLayout(0, 2, (int) (screenSize.getWidth()/100), (int) (screenSize.getHeight()/150)));
		
		selectLbl = new JLabel("Choisir un fichier de donn\u00E9e bateau");
		add(selectLbl);
		
		selectBtn = new JButton("Parcourir");
		add(selectBtn);
		selectBtn.addActionListener(importListener);
		
		JLabel lblMmsi = new JLabel("MMSI");
		add(lblMmsi);
		
		textField_mmsi = new JTextField();
		textField_mmsi.setName("mmsi");
		add(textField_mmsi);
		
		JLabel lblVersionAis = new JLabel("version AIS");
		add(lblVersionAis);
		
		comboBox_aisVersion = new JComboBox<String>(AisVersion.toStringArray());
		comboBox_aisVersion.setName("aisVersion");
		add(comboBox_aisVersion);
		
		JLabel lblNumroOmi = new JLabel("Num\u00E9ro OMI");
		add(lblNumroOmi);
		
		textField_imo = new JTextField();
		textField_imo.setName("imo");
		add(textField_imo);
		
		JLabel lblIndicatifDappel = new JLabel("Indicatif d'appel");
		add(lblIndicatifDappel);
		
		textField_callsign = new JTextField();
		textField_callsign.setName("callSign");
		add(textField_callsign);
		
		JLabel lblNom = new JLabel("Nom");
		add(lblNom);
		
		textField_name = new JTextField();
		textField_name.setName("name");
		add(textField_name);
		
		JLabel lblType = new JLabel("Type");
		add(lblType);
		
		comboBox_shipType = new JComboBox<String>(DataManager.shipTypeToStringArray());
		comboBox_shipType.setName("shipType");
		add(comboBox_shipType);
		
		JLabel lblNewLabel = new JLabel("Distance de la proue");
		add(lblNewLabel);
		
		textField_dimBow = new JTextField();
		textField_dimBow.setName("dimBow");
		add(textField_dimBow);
		
		JLabel lblDistanceDeLa = new JLabel("Distance de la poupe");
		add(lblDistanceDeLa);
		
		textField_dimStern = new JTextField();
		textField_dimStern.setName("dimStern");
		add(textField_dimStern);
		
		JLabel lblDistanceDeBabord = new JLabel("Distance de babord");
		add(lblDistanceDeBabord);
		
		textField_dimPort = new JTextField();
		textField_dimPort.setName("dimPort");
		add(textField_dimPort);
		
		JLabel lblDistanceDeTribord = new JLabel("Distance de tribord");
		add(lblDistanceDeTribord);
		
		textField_dimStarboard = new JTextField();
		textField_dimStarboard.setName("dimStarboard");
		add(textField_dimStarboard);
		
		JLabel lblTypeDeDispositif = new JLabel("Type de dispositif position");
		add(lblTypeDeDispositif);
		
		comboBox_posFixType = new JComboBox<String>(PositionFixType.toStringArray());
		comboBox_posFixType.setName("posType");
		add(comboBox_posFixType);
		
		JLabel lblPosAcc = new JLabel("Pr\u00E9cision de position");
		add(lblPosAcc);
		
		comboBox_posAcc = new JComboBox<String>(DataManager.posAccToStringArray());
		comboBox_posAcc.setName("posAcc");
		add(comboBox_posAcc);
		
		JLabel lblTirantDeau = new JLabel("Tirant d'eau");
		add(lblTirantDeau);
		
		textField_draught = new JTextField();
		textField_draught.setName("draught");
		add(textField_draught);
		
		JLabel lblDestination = new JLabel("Destination");
		add(lblDestination);
		
		textField_destination = new JTextField();
		textField_destination.setName("destination");
		add(textField_destination);
		
		JLabel lblDte = new JLabel("DTE");
		add(lblDte);
		
		comboBox_dte = new JComboBox<String>(DataManager.dteToStringArray());
		comboBox_dte.setName("dte");
		add(comboBox_dte);
		
		JLabel lblVitessenoeud = new JLabel("Vitesse (noeud)");
		add(lblVitessenoeud);
		
		textField_speed = new JTextField();
		textField_speed.setName("speed");
		add(textField_speed);
		
		JLabel lblStatutDeNavigation = new JLabel("Statut de navigation");
		add(lblStatutDeNavigation);
		
		comboBox_navStat = new JComboBox<String>(DataManager.navStatToStringArray());
		comboBox_navStat.setName("navStat");
		add(comboBox_navStat);
		
		JLabel lblSpecialManIndicator = new JLabel("Indicateur de manoeuvre particulière");
		add(lblSpecialManIndicator);
		
		comboBox_specialManIndicator = new JComboBox<String>(SpecialManIndicator.toStringArray());
		comboBox_specialManIndicator.setName("specialManIndicator");
		add(comboBox_specialManIndicator);
		
		dateTimePanel = new DateTimePanel();
		add(dateTimePanel);
		
		JLabel lblNull = new JLabel();
		add(lblNull);
		
		exportBtn = new JButton("Exporter");
		exportBtn.addActionListener(exportListener);
		add(exportBtn);
		
		validateBtn = new JButton("Valider");
		validateBtn.addActionListener(loadListener);
		add(validateBtn);
	}
	
	/**
	 * Permet de selectionner un fichier de données
	 * @return File
	 */
	public File getDataFile() {
		File f = PopupManager.fileChooser("Selectionner un fichier", "Fichiers csv.", "csv");
		if(f != null) selectLbl.setText("Selectionn\u00E9 : "+f.getName());
		return f;
	}
	
	/**
	 * Permet de sauvegarder un fichier
	 * @param f File
	 * @return true si le fichier a été sauvegardé, false sinon
	 */
	public boolean saveDataFile(File f) {
		return PopupManager.fileSaver("Enregistrer sous", f);
	}
	
	/**
	 * Desactive la modification de la vue
	 */
	public void disable() {
		dateTimePanel.setActive(false);
		for(Component c : getComponents()) c.setEnabled(false);
	}
	
	/**
	 * Active la modification de la vue
	 */
	public void enable() {
		dateTimePanel.setActive(false);
		for(Component c : getComponents()) c.setEnabled(true);
	}
	
	/**
	 * Retourne sous forme d'un objet Calendar la date et l'heure saisies dans le formulaire
	 * @return Calendar
	 */
	public Calendar getDateTime() {
		if(!dateTimePanel.isTimeStamp()) return null;
		LocalDate date = dateTimePanel.getDate();
		int[] time = dateTimePanel.getTime();
		Calendar c = Calendar.getInstance();
		c.set(date.getYear(), date.getMonthValue()-1, date.getDayOfMonth(), time[0], time[1], time[2]);
		return c;
	}
	
}

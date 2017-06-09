package view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;

/**
 * Vue affichant un formulaire de saisie de date et heure
 */
public class DateTimePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private DatePicker datePicker;
	private JTextField txtFieldHour;
	private JTextField txtFieldMin;
	private JTextField txtFieldSec;
	private JCheckBox chbxTimestamp;
	
	public DateTimePanel() {
		chbxTimestamp = new JCheckBox("Heure de départ");
		chbxTimestamp.setName("timeStamp");
		chbxTimestamp.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
	    		setActive(chbxTimestamp.isSelected());
	    	}
	    });
		add(chbxTimestamp);
		
		datePicker = new DatePicker();
		add(datePicker);
		
		txtFieldHour = new JTextField();
		txtFieldHour.setToolTipText("hh");
		txtFieldHour.setName("hour");
		add(txtFieldHour);
		txtFieldHour.setColumns(2);
		
		JLabel label_2 = new JLabel(":");
		add(label_2);
		
		txtFieldMin = new JTextField();
		txtFieldMin.setToolTipText("mm");
		txtFieldMin.setName("min");
		add(txtFieldMin);
		txtFieldMin.setColumns(2);
		
		JLabel label_3 = new JLabel(":");
		add(label_3);
		
		txtFieldSec = new JTextField();
		txtFieldSec.setToolTipText("ss");
		txtFieldSec.setName("sec");
		add(txtFieldSec);
		txtFieldSec.setColumns(2);
		
		setActive(chbxTimestamp.isSelected());		
	}
	
	/**
	 * Verifie si la checkbox est cochée
	 * @return boolean
	 */
	public boolean isTimeStamp() {
		return chbxTimestamp.isSelected();
	}
	
	/**
	 * Retourne la date saisie en un objet LocalDate
	 * @return LocalDate
	 */
	public LocalDate getDate() {
		if(!isTimeStamp()) return null;
		return datePicker.getDate();
	}
	
	/**
	 * Retourne l'heure saisie sous forme d'un tableau de 3 entiers
	 * @return tableau d'entier
	 */
	public int[] getTime() {
		if(!isTimeStamp()) return null;
		try {
			return new int[]{
					Integer.parseInt(txtFieldHour.getText()),
					Integer.parseInt(txtFieldMin.getText()),
					Integer.parseInt(txtFieldSec.getText())
			};
		} catch(NumberFormatException e) {
			return new int[]{-1,-1,-1};
		}
		
	}
	
	/**
	 * Active ou désactive la vue
	 * @param b : boolean
	 */
	private void setActive(boolean b) {
		datePicker.setEnabled(b);
		txtFieldHour.setEnabled(b);
		txtFieldMin.setEnabled(b);
		txtFieldSec.setEnabled(b);
	}

}

package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controler.ScenariosFormListener;
import model.scenario.DataEnum;

public class ScenarioFormFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JButton btnOk;
	private JButton btnCancel;
	private ScenariosFormListener listener;

	public ScenarioFormFrame(ScenariosFormListener listener) {
		this.listener = listener;
		setTitle("Nouveau scénario");
		
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
	
	public void buildForm(HashMap<String,Object> dataType) {
		resetForm();
		listener.setDataType(dataType);
		for(Entry<String,Object> e : dataType.entrySet()) {
			add(new JLabel(e.getKey()));
			if(e.getValue() == DataEnum.class) {
				JComboBox<DataEnum> cbType = new JComboBox<DataEnum>(DataEnum.values());
				cbType.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						DataEnum data = (DataEnum) ((JComboBox<?>)e.getSource()).getSelectedItem();
						add(new JLabel(data.getLabel()));
						if((data.getType()) instanceof Object[]) {
							add(new JComboBox<>((Object[])data.getType()));
						} else {
							JTextField txtField = new JTextField();
							txtField.setName(data.getLabel());
							add(txtField);
						}
						revalidate();
					}
				});
				add(cbType);
			} else if(e.getValue().getClass().isArray()) {
				JComboBox<Object> combobox = new JComboBox<Object>((Object[])e.getValue());
				combobox.setName(e.getKey());
				add(combobox);
			} else {
				JTextField txtField = new JTextField();
				txtField.setName(e.getKey());
				add(txtField);
			}
		}
		add(btnCancel);
		add(btnOk);
		setVisible(true);
	}
	
	public void resetForm() {
		for(Component c : getContentPane().getComponents()) {
			if(c instanceof JButton) continue;
			remove(c);
		}
	}

}

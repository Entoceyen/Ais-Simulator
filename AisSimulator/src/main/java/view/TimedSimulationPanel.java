package view;

import java.awt.GridLayout;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;

import controler.TimedSimulationListener;

public class TimedSimulationPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JSlider slider;
	private static Hashtable<Integer, JLabel> labelTable;
	
	static {
		labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(0, new JLabel("0min"));
		for(int i=60 ; i<24*60*60 ; i+=60)
			labelTable.put(i, new JLabel(i/60+"min"));
	}

	public TimedSimulationPanel(TimedSimulationListener listener) {
		setLayout(new GridLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));
		slider = new JSlider();
	    slider.setMinimum(0);
	    slider.setMaximum(0);
	    slider.setValue(0);
	    slider.setPaintTicks(true);
	    slider.setPaintLabels(true);
	    slider.setMinorTickSpacing(10);
	    slider.setMajorTickSpacing(60);
	    slider.setLabelTable(labelTable);
		slider.addChangeListener(listener);
		add(slider);
	}
	
	public void setDuration(int sec) {
		slider.setMaximum(sec);
	}
	
}

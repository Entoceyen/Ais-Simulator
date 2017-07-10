package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JButton;

import dk.dma.ais.message.NavigationalStatus;
import model.DynamicData;
import model.Simulation;
import model.TCPClient;
import model.aismessages.AisMessageCreator;
import model.aismessages.AisStream;
import model.aismessages.TimeIntervalCondition;
import view.AisPanel;
import view.PopupManager;

public class AisStreamListener implements ActionListener {

	private Simulation simulation;
	private AisPanel panel;
	
	public AisStreamListener(Simulation simulation) {
		this.simulation = simulation;
	}
	
	public void setPanel(AisPanel panel) {
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(simulation.getAisStream() == null) 
			try {
				simulation.setAisStream(computeAisStream());
			} catch (Exception e1) {
				PopupManager.errorMessage("Calcul du flux AIS", e1.getMessage());
			}
		System.out.println(simulation.getAisStream());
		
		if(((JButton)e.getSource()).getName().equals("Compute")) {
		}
		if(((JButton)e.getSource()).getName().equals("Export")) {
			File file = null;
			try { file = simulation.getAisStream().export(); } 
			catch (IOException e1) { e1.printStackTrace(); }
			if(!panel.saveDataFile(file)) PopupManager.errorMessage("Sauvegarde du fichier", "Echec lors de la sauvegarde du fichier.");
			else PopupManager.infoMessage("Sauvegarde du fichier", "Fichier sauvegardé avec succès.");
		}
		if(((JButton)e.getSource()).getName().equals("Run")) {
			AisStream stream = simulation.getAisStream();
			for(int i=0 ; i<stream.size() ; i++) {
				try {
					TCPClient.sendMessage(stream.getMessage(i).substring(stream.getMessage(i).indexOf("!")));
				} catch (IOException e2) { e2.printStackTrace(); }
				int delay = stream.getTimeStamp(stream.getMessage(i+1)) - stream.getTimeStamp(stream.getMessage(i));
				try { Thread.sleep(delay); }
				catch (InterruptedException e1) {}
			}
		}

	}
	
	private AisStream computeAisStream() throws Exception {
		int time = 0, msg5Delay = 0, msg1Delay = 0, x = 0;
		AisStream stream = new AisStream();
		if(simulation.getStartTime() == null) simulation.setStartTime(Calendar.getInstance());
		simulation.etaEncoder();
		Calendar c = (Calendar) simulation.getStartTime().clone();
		for(int i=0 ; i<simulation.getSize() ; i++) {
			if(simulation.getInstant(i).isCut()) continue;
			if(!simulation.getInstant(i).isSendable()) { time++; continue; }
			c.add(Calendar.SECOND, 1);
			String tagBlock = AisMessageCreator.createTagBlockMs(c);
			if(msg5Delay == 0 || !simulation.getInstant(i).getStaticData().equals(simulation.getInstant(i-1).getStaticData())) {
				String[] msg5 = AisMessageCreator.create(5, simulation, time, i);
				for(String msg : msg5) stream.addMessage(tagBlock+msg);
				msg5Delay = 360000;
			}
			TimeIntervalCondition condition = getTimeIntervalCondition(i);
			if(msg1Delay < 1000 || condition != getTimeIntervalCondition(i-1)) {
				if(msg1Delay == 333) {
					x++;
					int d = x*333; // décalage 0.333 > 0.666 > 0.999
					if(d == 999) { x = -1; time++; msg5Delay -= 1000; continue; }
					Calendar c1 = (Calendar) c.clone();
					c1.add(Calendar.MILLISECOND, /*msg1Delay*/d);
					tagBlock = AisMessageCreator.createTagBlockMs(c1);
				}
				String[] msg1 = AisMessageCreator.create(1, simulation, time, i);
				for(String msg : msg1) stream.addMessage(tagBlock+msg);
				msg1Delay = condition.getInterval();
			}
			msg5Delay -= 1000;
			msg1Delay -= 1000;
			time++;
			if(simulation.getInstant(i).isVesselSameID()) {
				// TODO envoyer msg 1 avec mm mmsi
			}
		}
		return stream;
	}
	
	private TimeIntervalCondition getTimeIntervalCondition(int instant) {
		DynamicData data = simulation.getInstant(instant).getDynamicData();
		double speed = data.getSpeed()/10;
		boolean anchor = (data.getNavStat() == NavigationalStatus.AT_ANCHOR || data.getNavStat() == NavigationalStatus.MOORED) ? true:false;
		boolean turning = false;
		int max = instant < 20 ? instant : 20;
		for(int i=0 ; i<max ; i++) turning |= simulation.getInstant(instant-i).isChangeRoute();
		return TimeIntervalCondition.get(speed, turning, anchor);
	}

}

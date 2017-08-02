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
import model.aismessages.AisMessageCreator;
import model.aismessages.AisStream;
import model.aismessages.TimeIntervalCondition;
import model.scenario.ChangeDelayMessageScenario;
import model.scenario.VesselSameIDScenario;
import view.AisPanel;
import view.ManualDelayMessageFrame;
import view.PopupManager;

/**
 * Contrôleur listener permettant de gérer le flux de messages AIS
 */
public class AisStreamListener implements ActionListener {

	private Simulation simulation;
	private AisPanel panel;
	private ManualDelayMessageFrame delayMessageFrame;
	private MessageSender sender;
	
	public AisStreamListener(Simulation simulation, ManualDelayMessageFrame delayMessageFrame) {
		this.simulation = simulation;
		this.delayMessageFrame = delayMessageFrame;
	}
	
	public void setPanel(AisPanel panel) {
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*---Scenario de changement manuel de la fréquence d'�mission---*/
		if(((JButton)e.getSource()).getName().equals("ManualDelayMessage")) {
			delayMessageFrame.setVisible(true);
			return;
		}
		
		/*---Calcule du flux AIS---*/
		try {
			simulation.setAisStream(computeAisStream());
		} catch (Exception e1) {
			PopupManager.errorMessage("Calcul du flux AIS", e1.getMessage());
		}
		
		/*---Exporte le flux AIS sous forme d'un fichier log timestampé---*/
		if(((JButton)e.getSource()).getName().equals("Export")) {
			File file = null;
			try { file = simulation.getAisStream().export(); } 
			catch (IOException e1) { e1.printStackTrace(); }
			if(!panel.saveDataFile(file)) PopupManager.errorMessage("Sauvegarde du fichier", "Echec lors de la sauvegarde du fichier.");
			else PopupManager.infoMessage("Sauvegarde du fichier", "Fichier sauvegardé avec succès.");
			return;
		}
		
		/*---D�marre la simulation temps réelle sur serveur TCP---*/
		if(((JButton)e.getSource()).getName().equals("Run")) {
			sender = new MessageSender(simulation.getAisStream());
			sender.start();
			panel.setVisibleStop(true);
			panel.setVisibleRun(false);
			return;
		}
		
		/*---Stop la simulation temps réelle sur serveur TCP---*/
		if(((JButton)e.getSource()).getName().equals("Stop")) {
			System.out.println("stop");
			sender.cancel();
			panel.setVisibleStop(false);
			panel.setVisibleRun(true);
			return;
		}

	}
	
	/**
	 * Calcule la suite de messages AIS à partir des données de la simulation
	 * @return AisStream le flux AIS
	 * @throws Exception
	 */
	private AisStream computeAisStream() throws Exception {
		int time = 0, msg5Delay = 0, msg1Delay = 0, x = 0;
		AisStream stream = new AisStream();
		
		/*---Préparation de l'heure de départ et calcule de l'ETA---*/
		if(simulation.getStartTime() == null) simulation.setStartTime(Calendar.getInstance());
		simulation.etaEncoder();
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(simulation.getStartTime().getTimeInMillis());
		
		/*---Récupération du scénario de changement de fréquence d'émission---*/
		ChangeDelayMessageScenario changeDelayScenario = simulation.getChangeDelayScenario();
		
		/*---Parcours des instants de la simulation et création du flux---*/
		for(int i=0 ; i<simulation.getSize() ; i++) {
			
			/*---Condition des scenarios AIS off et position successives incohérentes---*/
			if(simulation.getInstant(i).isCut()) continue;
			if(!simulation.getInstant(i).isSendable()) { time++; continue; }
			
			/*---Calcule du tagBlock timestampé---*/
			String tagBlock = AisMessageCreator.createTagBlockMs(c);
			
			/*---Condition d'émission du message 5---*/
			if(msg5Delay == 0 || !simulation.getInstant(i).getStaticData().equals(simulation.getInstant(i-1).getStaticData())) {
				String[] msg5 = AisMessageCreator.create(5, simulation, time, i);
				for(String msg : msg5) stream.addMessage(tagBlock+msg);
				if(changeDelayScenario != null && changeDelayScenario.isDelayMessage5())
					msg5Delay = changeDelayScenario.getDelayMessage5()*1000;
				else msg5Delay = 360000;
			}
			
			/*---Condition d'émission du message 1---*/
			TimeIntervalCondition condition = getTimeIntervalCondition(i);
			if(msg1Delay < 1000 || condition != getTimeIntervalCondition(i-1)) {
				/*---Ajout du décalage de timestamp lors de la fr�quence de 3 1/3 s---*/
				if(msg1Delay == 333) {
					x++;
					int d = x*333; // décalage 0.333 > 0.666 > 0.999
					if(d == 999) { x = -1; time++; msg5Delay -= 1000; continue; }
					Calendar c1 = (Calendar) c.clone();
					c1.add(Calendar.MILLISECOND, d);
					tagBlock = AisMessageCreator.createTagBlockMs(c1);
				}
				
				/*---Calcul du message 1---*/
				String[] msg1 = AisMessageCreator.create(1, simulation, time, i);
				for(String msg : msg1) stream.addMessage(tagBlock+msg);
				if(changeDelayScenario != null && changeDelayScenario.isDelayMessage1())
					msg1Delay = changeDelayScenario.getDelayMessage1()*1000;
				else msg1Delay = condition.getInterval();
			
				/*---Condition du scénario Navire même MMSI---*/
				if(simulation.getInstant(i).isVesselSameID()) {
					AisMessageCreator.create(VesselSameIDScenario.getDynData(), simulation.getInstant(i).getStaticData().getMmsi());
					for(String msg : msg1) stream.addMessage(tagBlock+msg);
				}
			}
			
			/*---Décrémentation des délais des message et incrémentation du temps---*/
			msg5Delay -= 1000;
			msg1Delay -= 1000;
			time++;
			c.add(Calendar.SECOND, 1);
		}
		return stream;
	}
	
	/**
	 * Pour une seconde de la simulation donnée, retourne la condition et le délai associé
	 * @param instant
	 * @return TimeIntervalCondition la condition et le délai associé
	 */
	private TimeIntervalCondition getTimeIntervalCondition(int instant) {
		DynamicData data = simulation.getInstant(instant).getDynamicData();
		double speed = data.getSpeed()/10;
		boolean anchor = (data.getNavStat() == NavigationalStatus.AT_ANCHOR || data.getNavStat() == NavigationalStatus.MOORED) ? true:false;
		boolean turning = false;
		int max = instant < 20 ? instant : 20;
		for(int i=0 ; i<max ; i++) turning |= simulation.getInstant(instant-i).isChangeRoute();
		return TimeIntervalCondition.get(speed, turning, anchor);
	}
	
	public void setEnablePanel(boolean b) {
		panel.setActive(b);
	}

}

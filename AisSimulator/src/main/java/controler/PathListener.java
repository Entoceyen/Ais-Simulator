package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import model.Path;
import model.Simulation;
import model.io.PathIO;
import view.PathPanel;
import view.PopupManager;

/**
 * Controleur listener permettant de charger un fichier de trajet
 */
public class PathListener implements ActionListener {

	private PathPanel panel;
	private Simulation simulation;
	
	public PathListener(Simulation simulation) {
		this.simulation = simulation;
	}
	
	protected void setPanel(PathPanel panel) {
		this.panel = panel;
	}
	
	public void actionPerformed(ActionEvent e) {
		File f = panel.getPathFile();
		Path path = null;
		try {
			path = PathIO.readPath(f);
		} catch (Exception e1) {
			PopupManager.errorMessage("Lecture du fichier", "Une erreur de lecture du fichier est survenue.\n"+e1.getMessage());
			return;
		}
		if(path.getNbSteps() < 2) PopupManager.errorMessage("Trajet incorrecte", "Le trajet doit comporter au minimum 2 points.");
		simulation.setPath(path);
		System.out.println(path.toString());
	}

}

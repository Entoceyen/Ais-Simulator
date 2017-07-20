package model.scenario;

import model.Path;

/**
 * Modèle scénario générant une changement de trajet de la simulation
 * Nécessite de re-calculer la simulation
 */
public abstract class ChangePathScenario extends Scenario {
	
	private Path path;
	private Path oldPath;

	public ChangePathScenario(Path path) {
		super(0, 0, true);
		this.path = path;
	}

	/**
	 * Applique la nouveau trajet à la simulation et sauvegarde l'ancien
	 */
	@Override
	public void apply() {
		oldPath = getSimulation().getPath();
		getSimulation().setPath(path);
		getSimulation().getInstant(0).getDynamicData().setPosition(path.getStep(0));
	}
	
	/**
	 * Restaure l'ancien trajet sur la simulation et supprime le scénario
	 */
	@Override
	public void remove() {
		getSimulation().setPath(oldPath);
		getSimulation().getInstant(0).getDynamicData().setPosition(oldPath.getStep(0));
		super.remove();
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.oldPath = this.path;
		this.path = path;
	}

	public Path getOldPath() {
		return oldPath;
	}

	public void setOldPath(Path oldPath) {
		this.oldPath = oldPath;
	}
	
}

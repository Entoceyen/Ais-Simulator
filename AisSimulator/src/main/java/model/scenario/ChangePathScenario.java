package model.scenario;

import model.Path;

public abstract class ChangePathScenario extends Scenario {
	
	private Path path;
	private Path oldPath;

	public ChangePathScenario(Path path) {
		super(0, 0, true);
		this.oldPath = this.path;
		this.path = path;
	}

	@Override
	public void apply() {
		getSimulation().setPath(path);
		getSimulation().compute(0);
	}
	
	@Override
	public void remove() {
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

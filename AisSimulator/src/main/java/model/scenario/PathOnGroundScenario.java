package model.scenario;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import model.Path;
import model.io.PathIO;
import model.scenario.Scenario.Scenarios;

public class PathOnGroundScenario extends ChangePathScenario {
	
	private static ArrayList<Path> pathsOnGround;
	
	static {
		try {
			pathsOnGround = loadPathsOnGround();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PathOnGroundScenario(Path path) {
		super(path);
	}

	private static ArrayList<Path> loadPathsOnGround() throws Exception {
		ArrayList<Path> paths = new ArrayList<Path>();
		URL url = ClassLoader.getSystemResource("pathOnGround");
		File rep = new File(url.toURI());
		File[] pathFiles = rep.listFiles();
		for(File f : pathFiles)	paths.add(PathIO.readPath(f));
		return paths;
	}
	
	public static HashMap<String,Object> getDataType() {
		String[] paths = new String[pathsOnGround.size()];
		for(int i=0 ; i<paths.length ; i++) paths[i] = pathsOnGround.get(i).getName();
		
		HashMap<String,Object> dataType = new HashMap<String,Object>();
		dataType.put("SCENARIO", Scenarios.PathOnGroundScenario);
		dataType.put("Trajets à terre", paths);
		return dataType;
	}

}

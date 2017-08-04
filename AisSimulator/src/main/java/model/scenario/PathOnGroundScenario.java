package model.scenario;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.Coordinate;
import model.Path;
import model.io.PathIO;
import view.PopupManager;

/**
 * Modèle scénario permettant de choisir un trajet passant à terre dans une liste de trajet
 * Nécessite de re-calculer la simulation
 */
public class PathOnGroundScenario extends ChangePathScenario {
	
	/**
	 * Liste de trajet Path passant à terre
	 */
	private static ArrayList<Path> pathsOnGround;
	
	static {
		try {
			pathsOnGround = loadPathsOnGround();
		} catch (Exception e) {
			PopupManager.errorMessage("Lecture des fichiers", e.toString());
		}
	}

	public PathOnGroundScenario(Path path) {
		super(path);
	}

	/**
	 * Charge en mémoire l'ensemble des chemins passant à terre à partir de fichier contenus dans le dossier pathOnGround
	 * @return ArrayList de Path
	 * @throws Exception
	 */
	private static ArrayList<Path> loadPathsOnGround() throws Exception {
		ArrayList<Path> paths = new ArrayList<Path>();
		File rep;
		InputStream is;
		BufferedReader br;
		try { 
			is = PathOnGroundScenario.class.getResourceAsStream("/resources/pathOnGround"); 
			br = new BufferedReader(new InputStreamReader(is));
			int i = 0;
			while (br.lines().toArray().length != i) {
				InputStream is1 = PathOnGroundScenario.class.getResourceAsStream("/resources/pathOnGround/"+(String)br.lines().toArray()[i]);
				BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
				String l = null;
				Path p = new Path();
				while ((l = br.readLine()) != null) {
					String[] parts = l.split(";");
					Coordinate c = new Coordinate(
							Double.parseDouble(parts[0]),
							Double.parseDouble(parts[1]));
					p.addStep(c);
				}
				br1.close();
				paths.add(p);
				i++;
			}
		} catch(NullPointerException e) {
			rep = new File(ClassLoader.getSystemResource("pathOnGround").toURI());
			File[] pathFiles = rep.listFiles();
			for(File f : pathFiles)	paths.add(PathIO.readPath(f));
		}
		
		return paths;
	}
	
	public static String[] getPathsOnGround() {
		String[] paths = new String[pathsOnGround.size()];
		for(int i=0 ; i<paths.length ; i++) paths[i] = pathsOnGround.get(i).getName();
		return paths;
	}
	
	public static Path getPathByName(String name) {
		for(Path path : pathsOnGround) 
			if(path.getName().equals(name)) return path;
		return null;
	}
	
	public String description() {
		return "Trajet à terre";
	}

}

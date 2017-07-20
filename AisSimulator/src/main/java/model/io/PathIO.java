package model.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import model.Coordinate;
import model.Path;

/**
 * Modèle permettant de lire un fichier de trajet et de retourner un objet Path
 */
public abstract class PathIO {
	
	public static Path readPath(File f) throws Exception {
		Path path = new Path();
		path.setName(f.getName());
		FileInputStream fis = new FileInputStream(f);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	 
		String line = null;
		while ((line = br.readLine()) != null) {
			String[] parts = line.split(";");
			Coordinate c = new Coordinate(
					Double.parseDouble(parts[0]),
					Double.parseDouble(parts[1]));
			path.addStep(c);
		}
		br.close();
		
		return path;
	}
	
}

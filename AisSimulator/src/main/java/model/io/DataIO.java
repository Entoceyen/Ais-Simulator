package model.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Modèle permettant de lire et d'écrire les données statiques et dynamique d'un fichier
 */
public abstract class DataIO {
	
	/**
	 * Lit dans un fichier les données statiques et dynamiques et les renvoie via une Map
	 * @param f : File - le fichier
	 * @return Map<String, String> : descrition : valeur
	 * @throws Exception
	 */
	public static HashMap<String,String> readDatas(File f) throws Exception {
		HashMap<String,String> datas = new HashMap<String,String>();
		FileInputStream fis = new FileInputStream(f);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	 
		String line = null;
		while ((line = br.readLine()) != null) {
			String[] parts = line.split(";");
			datas.put(parts[0], parts[1]);
		}
		br.close();
		
		return datas;
	}
	
	/**
	 * Ecrit dans un fichier les données passées en paramètre dans l'objet Map
	 * @param Map<String,String> data
	 * @return le fichier File
	 * @throws Exception
	 */
	public static File writeDatas(HashMap<String,String> data) throws Exception {
		File f = new File("data.csv");
		FileOutputStream fos = new FileOutputStream(f);
		BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fos));
		
		for(Entry<String, String> e : data.entrySet()) {
			String line = e.getKey()+";"+e.getValue();
			br.write(line);
			br.newLine();
		}
		br.close();
		
		return f;
	}
	
}

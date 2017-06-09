package view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Vue permettant de gérer tout les affichages de type pop-up
 */
public class PopupManager {
	
	/**
	 * Permet d'afficher pop-up de selection d'un fichier
	 * @param title - titre du pop-up
	 * @param filterDescription
	 * @param extensions - extension de fichier possible
	 * @return le fichier selectionné
	 */
	public static File fileChooser(String title, String filterDescription, String... extensions) {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle(title);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(filterDescription, extensions);
        chooser.addChoosableFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
		int res = chooser.showOpenDialog(null);
		 
		if (res == JFileChooser.APPROVE_OPTION) { 
			return chooser.getSelectedFile();
		}
		return null;
	}
	
	/**
	 * Permet d'afficher pop-up de sauvegarde d'un fichier
	 * @param title - titre du pop-up
	 * @param file - fichier à sauvegarder
	 * @return true si la sauvegarde a été un succès, false sinon
	 */
	public static boolean fileSaver(String title, File file) {
		JFileChooser saver = new JFileChooser();
		saver.setDialogTitle(title);
		saver.setSelectedFile(file);
		int res = saver.showSaveDialog(null);
		 
		if (res == JFileChooser.APPROVE_OPTION) { 
			return true;
		}
		return false;
	}
	
	/**
	 * Affiche un pop-up d'erreur
	 * @param title - titre du pop-up
	 * @param message à afficher
	 */
	public static void errorMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, "ERREUR : " + title, JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Affiche un pop-up d'information
	 * @param title - titre du pop-up
	 * @param message à afficher
	 */
	public static void infoMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, "Info : " + title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Affiche un pop-up d'options
	 * @param title - titre du pop-up
	 * @param message à afficher
	 * @param options - liste des options à afficher
	 */
	public static int optionsMessage(String title, String message, String... options) {
		return JOptionPane.showOptionDialog(null, message, title, JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
	}

}

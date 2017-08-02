package view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.sun.awt.AWTUtilities;
import com.sun.awt.AWTUtilities.Translucency;

/**
 * Vue et fenêtre concernant l'affichage du logo au démarrage de l'application
 */
public class InitFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ImagePanel imgPanel;
	
	public InitFrame(ImageIcon img) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = img.getIconWidth();
		int height = img.getIconHeight();
        int xpos = (screenSize.width - width) / 2;
        int ypos = (screenSize.height - height) / 2;
		setBounds(xpos, ypos, width, height);
		
		setUndecorated(true);
		imgPanel = new ImagePanel(img.getImage());
		setContentPane(imgPanel);
		
		try {
			// On vérifie d'abord la pr�sence de la classe AWTUtilities :
			Class.forName("com.sun.awt.AWTUtilities");
			// On vérifie que le système permettent ces effets :
			if ( AWTUtilities.isTranslucencySupported(Translucency.PERPIXEL_TRANSLUCENT) && 
					AWTUtilities.isTranslucencyCapable(getGraphicsConfiguration()) ) {
				// Puis on rend la fenêtre non-opaque :
				AWTUtilities.setWindowOpaque(this, false);
			}
		} catch (ClassNotFoundException e) {}
		
		setVisible(true);
	}
}

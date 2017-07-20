package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * Vue affichant une image, utilisée pour afficher le logo
 */
public class ImagePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private Image img;
		
		public ImagePanel(Image image) {
			img = image;
			setSize(new Dimension(img.getWidth(this), img.getHeight(this)));
			setBackground(new Color(1,1,1,0));
		}
		
		@Override
		public void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    if (img != null) {
		        g.drawImage(img, 0, 0, new Color(1,1,1,0), this);
		    }
		}
	}
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.sun.awt.AWTUtilities;
import com.sun.awt.AWTUtilities.Translucency;

/**
 * Vue fenêtre à propos
 */
public class AboutFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ImagePanel imgPanel;
	private String appName = "SIMULATEUR DéAIS";
	private String version = "v1.0";
	private String date = "07.2017";
	private String enterprise = "Cerema Eau Mer Fleuves";
	private String autor = "Nicolas BERNARD";
	private String partner = "Partenaire : Ecole Navale";

	public AboutFrame(ImageIcon img) {
		setUndecorated(true);
		setIconImage(img.getImage());
		setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	setVisible(false);
            }
        });
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(1, 2, 0, 0));
		contentPanel.setBackground(new Color(1,1,1,0));
		
		imgPanel = new ImagePanel(img.getImage());
		contentPanel.add(imgPanel);
		
		setMinimumSize(new Dimension(imgPanel.getWidth()*2, imgPanel.getHeight()));
		setLocation((screenSize.width-getWidth())/2, (screenSize.height-getHeight())/2);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
		infoPanel.setBackground(Color.WHITE);
		infoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		final JLabel lblSimulateurDais = new JLabel(appName);
		lblSimulateurDais.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(getFont() != null) {
					lblSimulateurDais.setFont(new Font("Tahoma",Font.BOLD,getFont().getSize()*2));
					lblSimulateurDais.revalidate();
				}
			}
		});
		lblSimulateurDais.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(lblSimulateurDais);
		
		JLabel lblV = new JLabel(version+"    "+date);
		lblV.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(lblV);
		
		JLabel lblCrdit = new JLabel("");
		infoPanel.add(lblCrdit);
		
		JLabel lblCeremaEauMer = new JLabel(enterprise);
		lblCeremaEauMer.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(lblCeremaEauMer);
		
		JLabel lblNicolasBernard = new JLabel(autor);
		lblNicolasBernard.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(lblNicolasBernard);
		
		JLabel lblNewLabel = new JLabel(partner);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(lblNewLabel);
		
		JLabel label = new JLabel("");
		infoPanel.add(label);
		
		JPanel p = new JPanel();
		p.setBackground(new Color(1,1,1,0));
		JButton btnClose = new JButton("Fermer");
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		p.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		p.add(btnClose);
		infoPanel.add(p);
		
		contentPanel.add(infoPanel);
		
		try {
			Class.forName("com.sun.awt.AWTUtilities");
			if(AWTUtilities.isTranslucencySupported(Translucency.PERPIXEL_TRANSLUCENT) && 
					AWTUtilities.isTranslucencyCapable(getGraphicsConfiguration()))
				AWTUtilities.setWindowOpaque(this, false);
		} catch (ClassNotFoundException e) {}
		
		setContentPane(contentPanel);
		
		setVisible(false);
	}
	
}

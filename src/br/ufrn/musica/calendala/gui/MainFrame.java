package br.ufrn.musica.calendala.gui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import br.ufrn.musica.calendala.io.ResourceIO;
import br.ufrn.musica.calendala.mandala.Mandala;

/**
 * @author Felipe Cortez de Sá
 * @version 0.1
 * @since 0.1
 */

public class MainFrame extends JFrame {
	public static final int WIDTH = 600;
	private static final long serialVersionUID = 1L;
	private static final String version = "0.3.0";
	private static MainFrame instance;
	
	private MainFrame() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image appIcon = kit.createImage(ResourceIO.app512Icon);
		setIconImage(appIcon);	
		
		Mandala mandala = Mandala.getInstance();
		mandala.init();
		
		setTitle("Calendala (v" + version + ") - " + mandala.getTitle());
		setJMenuBar(FacadeSwing.singleton().getMenuBar());
		add(FacadeSwing.singleton().getMandalaPanel(), BorderLayout.PAGE_START);
		pack();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setResizable(false);
	}
	
	public static MainFrame getInstance() {
		return instance;
	}	
	public void updateTitle() {
		setTitle("Calendala (v" + version + ") - " + Mandala.getInstance().getTitle());
	}
	
	public String getVersion() {
		return version;
	}

	public static void main(String args[]) {
    	java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				//OSX-specific
				System.setProperty("apple.laf.useScreenMenuBar", "true");
				System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Calendala");
				instance = new MainFrame();
				instance.setVisible(true);
			}
		});
	}	
}
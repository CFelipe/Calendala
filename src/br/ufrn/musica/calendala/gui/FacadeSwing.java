package br.ufrn.musica.calendala.gui;

import javax.swing.JFileChooser;

/**
 * @author Felipe Cortez de Sá
 * @version 0.1
 * @since 0.1
 */

public class FacadeSwing {
	private static FacadeSwing singleton;
	private static MenuBar menuBar;
	private static MandalaPanel mandalaPanel;
	private static InfoPanel infoPanel;
	private static ColorPanel colorPanel;
	private static JFileChooser openFileChooser, saveFileChooser;

	public static FacadeSwing singleton() {
		if (singleton == null)
			singleton = new FacadeSwing();
		return singleton;
	}

	private FacadeSwing() {
		menuBar = new MenuBar();
		mandalaPanel = new MandalaPanel();
		infoPanel = new InfoPanel();
		colorPanel = new ColorPanel();
	}

	public MenuBar getMenuBar() {
		return menuBar;
	}

	public MandalaPanel getMandalaPanel() {
		return mandalaPanel;
	}
	
	public InfoPanel getInfoPanel() {
		return infoPanel;
	}
	
	public ColorPanel getColorPanel() {
		return colorPanel;
	}

	public JFileChooser getOpenFileChooser() {
		return openFileChooser;
	}

	public JFileChooser getSaveFileChooser() {
		return saveFileChooser;
	}
	
}

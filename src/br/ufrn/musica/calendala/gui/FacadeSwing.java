package br.ufrn.musica.calendala.gui;

/**
 * @author Felipe Cortez de Sá
 * @version 0.1
 * @since 0.1
 */

public class FacadeSwing {
	private static FacadeSwing singleton;
	private static MenuBar menuBar;
	private static MandalaPanel mandalaPanel;

	public static FacadeSwing singleton() {
		if (singleton == null)
			singleton = new FacadeSwing();
		return singleton;
	}

	private FacadeSwing() {
		menuBar = new MenuBar();
		mandalaPanel = new MandalaPanel();
	}

	public MenuBar getMenuBar() {
		return menuBar;
	}

	public MandalaPanel getMandalaPanel() {
		return mandalaPanel;
	}
	
}

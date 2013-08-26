package br.ufrn.musica.calendala.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import br.ufrn.musica.calendala.controller.MainController;

/**
 * @author Felipe Cortez de Sá
 * @version 0.1
 * @since 0.1
 */

public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	public MenuBar() {
		super();
		
		MainController ctrl = MainController.getInstance();
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(new JMenuItem(ctrl.openFileAction));
		fileMenu.add(new JMenuItem(ctrl.saveFileAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(ctrl.exportAsPNGAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(ctrl.changeMandalaTitleAction));
		add(fileMenu);
		
		JMenu editMenu = new JMenu("Edit");
		editMenu.add(new JMenuItem(ctrl.editSliceAction));
		editMenu.addSeparator();
		editMenu.add(new JMenuItem(ctrl.insertRingUPAction));
		editMenu.add(new JMenuItem(ctrl.insertRingDOWNAction));
		editMenu.addSeparator();
		editMenu.add(new JMenuItem(ctrl.insertSliceAfterSelectionAction));
		editMenu.add(new JMenuItem(ctrl.insertSliceBeforeSelectionAction));
		editMenu.addSeparator();
		editMenu.add(new JMenuItem(ctrl.insertGroupAfterSelectionAction));
		editMenu.add(new JMenuItem(ctrl.insertGroupBeforeSelectionAction));
		editMenu.addSeparator();
		editMenu.add(new JMenuItem(ctrl.shiftRingCWAction));
		editMenu.add(new JMenuItem(ctrl.shiftRingCCWAction));
		editMenu.addSeparator();
		editMenu.add(new JMenuItem(ctrl.removeRingAction));
		editMenu.add(new JMenuItem(ctrl.removeSlicesAction));
		editMenu.addSeparator();
		editMenu.add(new JMenuItem(ctrl.cloneRingUPAction));
		editMenu.add(new JMenuItem(ctrl.cloneRingDOWNAction));
		editMenu.add(new JMenuItem(ctrl.insideOutAction));
		editMenu.add(new JMenuItem(ctrl.enumerateSelectionAction));
		add(editMenu);
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(new JMenuItem(ctrl.toggleHelpAction));
		helpMenu.add(new JMenuItem(ctrl.showAboutDialogAction));
		add(helpMenu);
	}
	
}
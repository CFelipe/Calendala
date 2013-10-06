package br.ufrn.musica.calendala.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import br.ufrn.musica.calendala.controller.MainController;

/**
 * @author Felipe Cortez de Sá
 * @version 0.2
 * @since 0.1
 */

public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	public MenuBar() {
		super();
		
		MainController ctrl = MainController.getInstance();
		
		JMenu menu;
		JMenuItem menuItem;
		
		
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		
		menuItem = new JMenuItem(ctrl.openFileAction);
		menuItem.setMnemonic(KeyEvent.VK_O);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		menuItem = new JMenuItem(ctrl.saveFileAction);
		menuItem.setMnemonic(KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem(ctrl.exportAsPNGAction);
		menuItem.setMnemonic(KeyEvent.VK_E);
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem(ctrl.changeMandalaTitleAction);
		menuItem.setMnemonic(KeyEvent.VK_T);
		menu.add(menuItem);
		
		add(menu);
		
		
		menu = new JMenu("Edit");
		menu.setMnemonic(KeyEvent.VK_E);

		menu.addSeparator();
		
		menuItem = new JMenuItem(ctrl.editSliceAction);
		menuItem.setMnemonic(KeyEvent.VK_E);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		menu.add(menuItem);

		menu.addSeparator();
		
		menuItem = new JMenuItem(ctrl.insertRingUPAction);
		menuItem.setMnemonic(KeyEvent.VK_I);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		menuItem = new JMenuItem(ctrl.insertRingDOWNAction);
		menuItem.setMnemonic(KeyEvent.VK_I);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem(ctrl.insertSliceAfterSelectionAction);
		menuItem.setMnemonic(KeyEvent.VK_I);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0));
		menu.add(menuItem);
		
		menuItem = new JMenuItem(ctrl.insertSliceBeforeSelectionAction);
		menuItem.setMnemonic(KeyEvent.VK_I);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, ActionEvent.SHIFT_MASK));
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem(ctrl.shiftRingCWAction);
		menuItem.setMnemonic(KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		menuItem = new JMenuItem(ctrl.shiftRingCCWAction);
		menuItem.setMnemonic(KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem(ctrl.removeRingAction);
		menuItem.setMnemonic(KeyEvent.VK_R);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		menuItem = new JMenuItem(ctrl.removeSlicesAction);
		menuItem.setMnemonic(KeyEvent.VK_R);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem(ctrl.cloneRingUPAction);
		menuItem.setMnemonic(KeyEvent.VK_C);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0));
		menu.add(menuItem);
		
		menuItem = new JMenuItem(ctrl.cloneRingDOWNAction);
		menuItem.setMnemonic(KeyEvent.VK_C);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.SHIFT_MASK));
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem(ctrl.insideOutAction);
		menuItem.setMnemonic(KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		menuItem = new JMenuItem(ctrl.enumerateSelectionAction);
		menuItem.setMnemonic(KeyEvent.VK_E);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0));
		menu.add(menuItem);

		menuItem = new JMenuItem(ctrl.colorSelectionAction);
		menuItem.setMnemonic(KeyEvent.VK_P);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
		menu.add(menuItem);
		
		add(menu);
		

		menu = new JMenu("Selection");
		menu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem(ctrl.rotateSelectionCWAction);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
		menu.add(menuItem);

		menuItem = new JMenuItem(ctrl.rotateSelectionCCWAction);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
		menu.add(menuItem);

		menuItem = new JMenuItem(ctrl.shiftRingSelectionUPAction);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
		menu.add(menuItem);

		menuItem = new JMenuItem(ctrl.shiftRingSelectionDOWNAction);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
		menu.add(menuItem);

		menuItem = new JMenuItem(ctrl.changeSelectionCWAction);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, ActionEvent.SHIFT_MASK));
		menu.add(menuItem);

		menuItem = new JMenuItem(ctrl.changeSelectionCCWAction);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, ActionEvent.SHIFT_MASK));
		menu.add(menuItem);

		add(menu);

		
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);
		
		menuItem = new JMenuItem(ctrl.toggleHelpAction);
		menuItem.setMnemonic(KeyEvent.VK_H);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		menu.add(menuItem);
		
		menuItem = new JMenuItem(ctrl.showAboutDialogAction);
		menuItem.setMnemonic(KeyEvent.VK_A);
		menu.add(menuItem);
		
		add(menu);
		
	}
	
}
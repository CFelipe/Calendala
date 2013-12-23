package br.ufrn.musica.calendala.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import br.ufrn.musica.calendala.gui.FacadeSwing;
import br.ufrn.musica.calendala.gui.MainFrame;
import br.ufrn.musica.calendala.io.FileIO;
import br.ufrn.musica.calendala.io.FileIO.Extension;
import br.ufrn.musica.calendala.io.ResourceIO;
import br.ufrn.musica.calendala.mandala.Mandala;
import br.ufrn.musica.calendala.mandala.Ring.Direction;

public class MainController {
	private static MainController controller;
	
	public static MainController getInstance() {
		if (controller == null) {
			MainController.controller = new MainController();
		}
		return controller;
	}
	
	public Action 
	
	editSliceAction = new EditSliceAction(
			"Edit slice", 
			"Edits the selected slice's text",
			new ImageIcon(ResourceIO.pencilIcon)),
	
	changeSelectedRingUPAction = new ChangeSelectedRingAction(
			"Move selection up",
			"Moves the selection one ring up", 
			Direction.UP),
	
	changeSelectedRingDOWNAction = new ChangeSelectedRingAction(
			"Move selection down", 
			"Moves the selection one ring down", 
			Direction.DOWN),
	
	rotateRingCWAction = new RotateRingAction(
			"Rotate ring (CW)", 
			"Rotates the ring clockwise", 
			Direction.CW,
			new ImageIcon(ResourceIO.cwIcon)),
	
	rotateRingCCWAction = new RotateRingAction(
			"Rotate ring (CCW)", 
			"Rotates the ring counterclockwise", 
			Direction.CCW,
			new ImageIcon(ResourceIO.ccwIcon)),
			
	rotateSelectionCWAction = new RotateSelectionAction(
			"Move selection (CW)", 
			"Rotates the selection clockwise",
			Direction.CW),
	
	rotateSelectionCCWAction = new RotateSelectionAction(
			"Move selection (CCW)", 
			"Rotates the selection counterclockwise", 
			Direction.CCW),
	
	
	changeSelectionCWAction = new ChangeSelectionRangeAction(
			"Move selection CW (multiple selection)", 
			"Changes the selection clockwise", 
			Direction.CW),
	
	changeSelectionCCWAction = new ChangeSelectionRangeAction(
			"Move selection CCW (multiple selection)", 
			"Changes the selection counterclockwise", 
			Direction.CCW),
	
	insertSliceBeforeSelectionAction = new InsertSliceAction(
			"Insert slice (CCW)", 
			"Inserts a slice before the selection", 
			new ImageIcon(ResourceIO.addSliceIcon),
			Direction.CCW),
	
	insertSliceAfterSelectionAction = new InsertSliceAction(
			"Insert slice (CW)", 
			"Inserts a slice after the selection", 
			new ImageIcon(ResourceIO.addSliceIcon),
			Direction.CW),
			
	removeSlicesAction = new RemoveSlicesAction(
			"Remove slice", 
			new ImageIcon(ResourceIO.removeSliceIcon),
			"Removes selected slice or slices"),
	
	removeRingAction = new RemoveRingAction(
			"Remove ring", 
			new ImageIcon(ResourceIO.removeRingIcon),
			"Removes selected ring"),
	
	insertRingUPAction = new InsertRingAction(
			"Insert ring outward", 
			new ImageIcon(ResourceIO.addRingIcon),
			Direction.UP),
	
	insertRingDOWNAction = new InsertRingAction(
			"Insert ring inward", 
			new ImageIcon(ResourceIO.addRingIcon),
			Direction.DOWN),
	
	cloneRingUPAction = new CloneRingAction(
			"Clone ring (up)", 
			"Clones a ring and adds it above the selected one", 
			new ImageIcon(ResourceIO.cloneIcon),
			Direction.UP),
	
	cloneRingDOWNAction = new CloneRingAction(
			"Clone ring (below)", 
			"Clones a ring and adds it below the selected one", 
			new ImageIcon(ResourceIO.cloneIcon),
			Direction.DOWN),

	mergeSlicesAction = new MergeSlicesAction(
			"Merge slices", 
			"Merges selected slices"),

	unmergeSlicesAction = new UnmergeSlicesAction(
			"Unmerge slices", 
			"Unmerges selected slices"),
	
	saveFileAction = new SaveFileAction(
			"Save as...", 
			"Saves a file",
			new ImageIcon(ResourceIO.disquetteIcon)),
			
			/** TODO */
			/*
	saveFileAction = new SaveFileAction(
			"Save...", 
			"Saves a file"),
			*/
			
	exportAsPNGAction = new ExportAsPNGAction(
			"Export as PNG", 
			new ImageIcon(ResourceIO.picturesIcon),
			"Exports the mandala as a .png file"),
	
	openFileAction = new OpenFileAction(
			"Open file", 
			"Opens a file",
			new ImageIcon(ResourceIO.folderIcon)),
	
	toggleHelpAction = new ToggleHelpAction(
			"Toggle help", 
			new ImageIcon(ResourceIO.helpIcon),
			"Toggles the help overlay"),
	
	insideOutAction = new InsideOutAction(
			"Turn inside out", 
			new ImageIcon(ResourceIO.insideOutIcon),
			"Turns the mandala inside out"),
	
	enumerateSelectionAction = new EnumerateSelectionAction(
			"Enumerate selection", 
			"Enumerates the selected slices"),
			
	colorSelectionAction = new ColorSelectionAction(
			"Color selection", 
			"Colors the selected slices with the currently selected color"),
	
	showAboutDialogAction = new ShowAboutDialogAction(
			"About", 
			new ImageIcon(ResourceIO.paintBrushIcon)),
			
	changeMandalaTitleAction = new ChangeMandalaTitleAction(
			"Change title", 
			"Opens up a dialog for changing the mandala's title"),
	
	toggleInnerRingAction = new ToggleInnerRingAction(
			"Toggle inner ring",
			"Toggles an inner ring for tidying up");
    	
	 public class EditSliceAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public EditSliceAction(String name, String shortDescription, Icon icon) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	FacadeSwing.singleton().getMandalaPanel().editField();
	    }
	  }
		
	 public class RotateSelectionAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Direction direction;
	
		public RotateSelectionAction(String name, String shortDescription, Direction direction) {
	      super(name);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	      this.direction = direction;
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	if(direction == Direction.CW)
		    	Mandala.getInstance().changeSelectionStart(1);
	    	else
	    		Mandala.getInstance().changeSelectionStart(-1);

	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
	  }
	 
	 public class RotateRingAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Direction direction;
	
		public RotateRingAction(String name, String shortDescription, 
				Direction direction, Icon icon) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	      this.direction = direction;
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().getSelectedRing().rotate(direction);
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
	  }
	 
	 public class ChangeSelectedRingAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Direction direction;
	
		public ChangeSelectedRingAction(String name, String shortDescription, Direction direction) {
	      super(name);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	      this.direction = direction;
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().changeSelectedRing(direction);
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
	  }
	 
	 public class ChangeSelectionRangeAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Direction direction;
	
		public ChangeSelectionRangeAction(String name, String shortDescription, Direction direction) {
	      super(name);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	      this.direction = direction;
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().changeSelectionRange(direction);
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
	  }
	 
	 public class InsertSliceAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Direction direction;
	
		public InsertSliceAction(String name, String shortDescription, Icon icon, Direction direction) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	      this.direction = direction;
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().insertSlice(direction);
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
	  }
	 
	 public class RemoveSlicesAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public RemoveSlicesAction(String name, Icon icon, String shortDescription) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
		    Mandala.getInstance().removeSelectedSlices();
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
	  }
	 
	 public class RemoveRingAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public RemoveRingAction(String name, Icon icon, String shortDescription) {
			super(name, icon);
			putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
		    Mandala.getInstance().removeSelectedRing();
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
		}
	}
	 
	 public class InsertRingAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Direction direction;
	
		public InsertRingAction(String name, Icon icon, Direction direction) {
	      super(name, icon);
	      this.direction = direction;
	    }
		
		public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().insertRing(direction);
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
		 
	 }
	 
	 public class CloneRingAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Direction direction;
	
		public CloneRingAction(String name, String shortDescription, Icon icon, Direction direction) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	      this.direction = direction;
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().cloneRing(direction);
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
		 
	 }

	 public class MergeSlicesAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public MergeSlicesAction(String name, String shortDescription) {
	      super(name);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().mergeSelectedSlices();
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
		 
	 }

	 public class UnmergeSlicesAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public UnmergeSlicesAction(String name, String shortDescription) {
	      super(name);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().unmergeSelectedSlices();
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
		 
	 }
	 
	public class OpenFileAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public OpenFileAction(String name, String shortDescription, Icon icon) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
			FileDialog openFileDialog = new FileDialog(
					MainFrame.getInstance(), 
					"Open file", 
					FileDialog.LOAD);
			openFileDialog.setFile("*.cdl");
			openFileDialog.setVisible(true);
			
			if(openFileDialog.getFile() != null) {
			FileIO.openFile(new File(
			openFileDialog.getDirectory() + 
			System.getProperty("file.separator") + 
			openFileDialog.getFile()));
			FacadeSwing.singleton().getMandalaPanel().repaint();
			}
	    }
   }
	 
	 public class SaveFileAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public SaveFileAction(String name, String shortDescription, Icon icon) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	FileDialog saveFileDialog = new FileDialog(
	    					MainFrame.getInstance(), 
	    					"Save as...", 
	    					FileDialog.SAVE);
	    	saveFileDialog.setFile("*.cdl");
	    	saveFileDialog.setVisible(true);
	    	
	    	if(saveFileDialog.getFile() != null) {
		    	FileIO.saveFile(new File(
		    			saveFileDialog.getDirectory() + 
		    			System.getProperty("file.separator") + 
		    			saveFileDialog.getFile()), Extension.CDL);
		    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    	}
		}
	}
	 
	public class ExportAsPNGAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public ExportAsPNGAction(String name, Icon icon, String shortDescription) {
			super(name, icon);
			putValue(SHORT_DESCRIPTION, shortDescription);
		}
		
	    public void actionPerformed(ActionEvent e) {
	    	//Hides the overlay
			if(FacadeSwing.singleton().getMandalaPanel().getShowHelpOverlay())
				FacadeSwing.singleton().getMandalaPanel().toggleHelpOverlay();
			
	    	FacadeSwing.singleton().getMandalaPanel().setShowSelection(false);
	    	FacadeSwing.singleton().getMandalaPanel().drawMandala();
			
	    	FileDialog saveFileDialog = new FileDialog(
	    					MainFrame.getInstance(), 
	    					"Export PNG", 
	    					FileDialog.SAVE);
	    	saveFileDialog.setFile("*.png");
	    	saveFileDialog.setVisible(true);
	    	
	    	if(saveFileDialog.getFile() != null) {
		    	FileIO.saveFile(new File(
		    			saveFileDialog.getDirectory() + 
		    			System.getProperty("file.separator") + 
		    			saveFileDialog.getFile()), Extension.PNG);
		    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    	}
			
			FacadeSwing.singleton().getMandalaPanel().setShowSelection(true);
			FacadeSwing.singleton().getMandalaPanel().repaint();
		}
	}
	 
	public class ToggleHelpAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public ToggleHelpAction(String name, Icon icon, String shortDescription) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	FacadeSwing.singleton().getMandalaPanel().toggleHelpOverlay();
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
		}
	}
	
	public class InsideOutAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public InsideOutAction(String name, Icon icon, String shortDescription) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	//Mandala.getInstance().insideOut();
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
		}
	}
	
	public class EnumerateSelectionAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public EnumerateSelectionAction(String name, String shortDescription) {
	      super(name);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().enumerateSelection();
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	   	}
	}
	
	public class ChangeMandalaTitleAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public ChangeMandalaTitleAction(String name, String shortDescription) {
	      super(name);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	String r = (String) JOptionPane.showInputDialog("Enter a title for the mandala");
	    	if(r != null && !r.equals("")) {
	    		Mandala.getInstance().setTitle(r);
		    	MainFrame.getInstance().updateTitle();
	    	}
	   	}
	}
	
	public class ColorSelectionAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public ColorSelectionAction(String name, String shortDescription) {
			super(name);
			putValue(SHORT_DESCRIPTION, shortDescription);
		}
		
	    public void actionPerformed(ActionEvent e) {
		    Mandala.getInstance().paintSelection();
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
		}
	}
	
	public class ToggleInnerRingAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public ToggleInnerRingAction(String name, String shortDescription) {
			super(name);
			putValue(SHORT_DESCRIPTION, shortDescription);
		}
		
	    public void actionPerformed(ActionEvent e) {
		    Mandala.getInstance().toggleInnerRing();
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
		}
	}
	
	public class ShowAboutDialogAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public ShowAboutDialogAction(String name, Icon icon) {
			super(name, icon);
		}
		
	    public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(
					MainFrame.getInstance(), 
					"Calendala is a free, open-source mandala editor\n" +
					"distributed under the GNU/GPL v3.0\n\n" +
					"Team: Alexandre Reche, Felipe Cortez de Sá\n\n" +
					"Entypo pictograms by Daniel Bruce — www.entypo.com",
					"About", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ResourceIO.cogIcon));
		}
	}
}
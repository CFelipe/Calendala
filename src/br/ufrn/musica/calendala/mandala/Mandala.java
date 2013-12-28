package br.ufrn.musica.calendala.mandala;

import java.util.ArrayList;
import java.util.Stack;

import br.ufrn.musica.calendala.mandala.Ring.Direction;

/**
 * @author Felipe Cortez de Sá
 * @version 0.1
 * @since 0.1
 */

public class Mandala {
	private static Mandala mandala;		// For now, only a single mandala can be open
	private ArrayList<Ring> rings;
	private String title;
	private Ring selectedRing;
	private int selectionStart = 0;
	private int selectionRange = 0;
	private boolean innerRing = true;	// Not implemented yet
	
	public Mandala() {
		title = "Untitled";
		rings = new ArrayList<Ring>();
	}
	
	public Mandala(String title) {
		this.title = title;
		rings = new ArrayList<Ring>();
	}
	
	public void setMandala(Mandala mandala) {
		Mandala.mandala = mandala;
	}
	
	public static Mandala getInstance() {
		if (mandala == null) {
			Mandala.mandala = new Mandala();
		}
		return mandala;
	}
	
	public ArrayList<Ring> getRings() {
		return rings;
	}

	public void setRings(ArrayList<Ring> rings) {
		this.rings = rings;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setSelectionRange(int selectionRange) {
		this.selectionRange = selectionRange;
	}

	public void setSelectionStart(int selectionStart) {
		this.selectionStart = selectionStart;
	}
	
	public Ring getSelectedRing() {
		return selectedRing;
	}
	
	public int getSelectionStart() {
		return selectionStart;
	}

	public int getSelectionRange() {
		return selectionRange;
	}
	
	public Slice getSelectedSlice() {
		return selectedRing.getSlices().get(selectionStart);
	}
	
	public void changeSelectionStart(int change) {
		// -x for CCW, x for CW
		if(change > 0) {
			selectionStart = (selectionStart + 
					selectedRing.getSlices().get(selectionStart).getMergeSize())
					% selectedRing.getSubdivisions();
		} else if (change < 0) {
			selectionStart -= 1;
			if(selectionStart < 0) {
				selectionStart += mandala.selectedRing.getSubdivisions();
			}
			selectionStart = mandala.selectedRing.getSlices().get(selectionStart).getStart();
		} else {
			selectionStart = mandala.selectedRing.getSlices().get(selectionStart).getStart();
		}
	}
	
	public void changeSelectedRing(Direction dir) {
		int selectedIndex = rings.indexOf(selectedRing);
		int oldsubdivs = selectedRing.getSubdivisions();
		if(dir == Direction.UP) {
			if(selectedIndex > 0)
				selectedRing = rings.get(selectedIndex - 1);
		} else if(dir == Direction.DOWN) {
			if(selectedIndex < rings.size() - 1)
				selectedRing = rings.get(selectedIndex + 1);
		}
		int newsubdivs = selectedRing.getSubdivisions();
		selectionStart *= (float) newsubdivs/oldsubdivs;
		selectionStart = selectedRing.getSlices().get(selectionStart).getStart();
	}
	
	public void changeSelectionRange(Direction dir) {
		if(dir == Direction.CW) {
			if(selectionRange < selectedRing.getTotalDivs() - 1)
				selectionRange++;
		} else if(dir == Direction.CCW) {
			if(selectionRange * -1 < selectedRing.getTotalDivs() - 1)
				selectionRange--;
		}
	}
	
	public Ring insertRing(Direction dir, int subdivisions) {
		Ring ring = new Ring(subdivisions);
		int selectedIndex = rings.indexOf(selectedRing);
		if(dir == Direction.UP) {
			rings.add(rings.indexOf(selectedRing), ring);
			selectedRing = rings.get(selectedIndex);
		} else if(dir == Direction.DOWN) {
			rings.add(rings.indexOf(selectedRing) + 1, ring);
			selectedRing = rings.get(selectedIndex + 1);
		}
		return ring;
	}

	public Ring insertRing(Direction dir) {
		return insertRing(dir, 12); 
	}

	
	public void cloneRing (Direction direction) {
	}
	
	public boolean removeSelectedRing() {
		int selectedIndex = rings.indexOf(selectedRing);
		if(rings.size() > 1) {
			rings.remove(selectedRing);
			selectedRing = selectedIndex <= 0 ? rings.get(selectedIndex) : rings.get(selectedIndex - 1);;
			return true;
		}
		return false;
	}
	
	public void mergeSelectedSlices() {
		if(Mandala.getInstance().getSelectionRange() != 0) {
			Direction orientation = 
					(Mandala.getInstance().getSelectionRange() >= 0) ? Direction.CW : Direction.CCW;
			int range = Math.abs(Mandala.getInstance().getSelectionRange()) + 1;
			selectedRing.mergeCells(selectionStart, range, orientation);
		}
	}

	public void unmergeSelectedSlices() {
		Direction orientation = 
				(Mandala.getInstance().getSelectionRange() >= 0) ? Direction.CW : Direction.CCW;
		int range = Math.abs(Mandala.getInstance().getSelectionRange()) + 1;
		selectedRing.unmergeCells(selectionStart, range, orientation);;
	}
	
	public void paintSelection() {
	}
	
	public void enumerateSelection() {
	}
	
	public void insideOut() {
		if(rings.size() > 1) {
			Stack<Ring> ringStack = new Stack<Ring>();
			while(!rings.isEmpty())
				ringStack.push(rings.remove(0));
			while(!ringStack.isEmpty())
				rings.add(ringStack.pop());
		}
	}
	
	public void toggleInnerRing() {
		innerRing = !innerRing;
	}
	
	public void init() {
		Ring r1 = insertRing(Direction.DOWN);
		selectedRing = r1;
	}
	 
}

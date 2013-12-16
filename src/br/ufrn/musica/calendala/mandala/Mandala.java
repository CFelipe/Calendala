package br.ufrn.musica.calendala.mandala;

import java.util.ArrayList;
import java.util.Stack;

import br.ufrn.musica.calendala.controller.MainController.MergeSlicesAction;
import br.ufrn.musica.calendala.mandala.Ring.Direction;
import br.ufrn.musica.calendala.util.CircularArrayList;
import br.ufrn.musica.calendala.util.MathUtils;

/**
 * @author Felipe Cortez de Sá
 * @version 0.1
 * @since 0.1
 */

public class Mandala {
	private static Mandala mandala;		// For now, only a single mandala can be opened
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
	
	public Ring getSelectedRing() {
		return selectedRing;
	}
	
	public int getSelectionStart() {
		return selectionStart;
	}
	
	public int getSelectionRange() {
		return selectionRange;
	}
	
	public void changeSelectionStart(int change) {
		// -x for CCW, x for CW
		if(change > 0) {
			selectionStart = (selectionStart + 
					selectedRing.getSlices().get(selectionStart).getMergeSize())
					% selectedRing.getSubdivisions();
		} else {
			if(selectionStart - 1 < 0) 
			selectionStart = (selectionStart - 1 +
					selectedRing.getSlices().get(selectionStart).getMergeSize())
					% selectedRing.getSubdivisions();
		}

		if(selectionStart < 0) 
		System.out.println(selectionStart);
	}
	
	public void changeSelectedRing(Direction dir) {
		int selectedIndex = rings.indexOf(selectedRing);
		if(dir == Direction.UP) {
			if(selectedIndex > 0)
				selectedRing = rings.get(selectedIndex - 1);
		} else if(dir == Direction.DOWN) {
			if(selectedIndex < rings.size() - 1)
				selectedRing = rings.get(selectedIndex + 1);
		}
	}
	
	public void changeSelectionRange(Direction dir) {
		if(dir == Direction.CW) {
			if(selectionRange < selectedRing.getSubdivisions() - 1)
				selectionRange++;
		} else if(dir == Direction.CCW) {
			if(selectionRange * -1 < selectedRing.getSubdivisions() - 1)
			selectionRange--;
		}
	}
	
	public Ring insertRing(Direction dir) {
		Ring ring = new Ring();
		if(dir == Direction.UP) {
			rings.add(ring);
		} else if(dir == Direction.DOWN) {
			rings.add(rings.indexOf(selectedRing), ring);
		}
		return ring;
	}

	public Ring insertRing(Direction dir, int subdivisions) {
		Ring ring = new Ring(subdivisions);
		if(dir == Direction.UP) {
			rings.add(ring);
		} else if(dir == Direction.DOWN) {
			rings.add(rings.indexOf(selectedRing), ring);
		}
		return ring;
	}
	
	public void cloneRing (Direction direction) {
	}
	
	public void insertSlice(Direction d) {
	}
	
	public void removeSelectedRing() {
		if(rings.size() > 1) {
			rings.remove(selectedRing);
		} else {
			System.out.println("Cannot remove ring");
		}
	}
	
	public void removeSelectedSlices() {
	}
	
	public boolean removeRing(Ring r) {
		return true;
	}
	
	public void mergeSelectedSlices() {
	}

	public void unmergeSelectedSlices() {
		
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
		Ring r1 = insertRing(Direction.UP);
		r1.addMerge(3, 3);
		r1.addMerge(9, 5);
		insertRing(Direction.UP, 28);
		insertRing(Direction.UP);
		selectedRing = r1;
	}
	 
}

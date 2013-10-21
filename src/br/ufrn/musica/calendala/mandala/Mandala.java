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
	private static Mandala mandala;
	private ArrayList<Ring> rings;
	private String title;
	private Ring selectedRing;
	private int selectionStart = 0;
	private int selectionRange = 0;
	
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
	
	public void changeSelectedRing(Direction direction) {
		if(direction == Direction.UP) {
		} else if(direction == Direction.DOWN) {
		}
	}
	
	public void changeSelectionRange(Direction dir) {
		if(dir == Direction.CW) {
			selectionRange++;
		} else if(dir == Direction.CCW) {
			selectionRange--;
		}
	}
	
	public Ring insertRing(Direction dir) {
		Ring ring = new Ring();
		if(dir == Direction.UP) {
			rings.add(ring);
		} else if(dir == Direction.DOWN) {
			rings.add(ring);
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
	
	public void init() {
		Ring r1 = insertRing(Direction.UP);
	}
	
	 
}

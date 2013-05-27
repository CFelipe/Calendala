package br.ufrn.musica.calendala.mandala;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
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
	private LinkedList<Slice> selectedSlices;
	private Direction selectionDirection = Direction.NONE;
	
	public Mandala() {
		title = "Untitled";
		rings = new ArrayList<Ring>();
		selectedSlices = new LinkedList<Slice>();
	}
	
	public Mandala(String title) {
		this.title = title;
		rings = new ArrayList<Ring>();
		selectedSlices = new LinkedList<Slice>();
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
	
	public void setSelectedRing(Ring selectedRing) {
		
	}
	
	public Slice getFirstSelectedSlice() {
		return selectedSlices.getFirst();
	}
	
	public Slice getLastSelectedSlice() {
		return selectedSlices.getLast();
	}
	
	public LinkedList<Slice> getSelectedSlices() {
		return selectedSlices;
	}

	public void setSelectedSlices(LinkedList<Slice> selectedSlices) {
		this.selectedSlices = selectedSlices;
	}
	
	private Slice getCWSlice() {
		Slice s = selectedSlices.getLast();
		Group g = s.getGroup();
		if(s.getGroup().getSlices().getLast() == s) {
			int gIndex = selectedRing.getGroups().indexOf(g);
			selectedRing.getGroups().moveTo(gIndex);
			Group nextG = selectedRing.getGroups().next();
			return nextG.getSlices().getFirst();
		} else {
			int nextSliceIndex = s.getGroup().getSlices().indexOf(s) + 1;
			return s.getGroup().getSlices().get(nextSliceIndex);
		}
	}
	
	private Slice getCCWSlice() {
		Slice s = selectedSlices.getLast();
		Group g = s.getGroup();
		if(s.getGroup().getSlices().getFirst() == s) {
			int gIndex = selectedRing.getGroups().indexOf(g);
			selectedRing.getGroups().moveTo(gIndex);
			Group nextG = selectedRing.getGroups().previous();
			return nextG.getSlices().getLast();
		} else {
			int prevSliceIndex = s.getGroup().getSlices().indexOf(s) - 1;
			return s.getGroup().getSlices().get(prevSliceIndex);
		}
	}
	
	public void rotateSelectedSlice(Direction direction) {
		Slice s = getCWSlice();
		if(direction == Direction.CW) {
			if(selectionDirection == Direction.NONE) {
				s = getCWSlice();
			} else if(selectionDirection == Direction.CW) {
				s = getCWSlice();
			} else if(selectionDirection == Direction.CCW) {
				s = getCCWSlice();
			}
		} else if(direction == Direction.CCW) {
			if(selectionDirection == Direction.NONE) {
				s = getCCWSlice();
			} else if(selectionDirection == Direction.CW) {
				s = getCWSlice();
			} else if(selectionDirection == Direction.CCW) {
				s = getCCWSlice();
			}
		} else {
			s = getCCWSlice();
		}
		selectedSlices.clear();
		selectedSlices.add(s);
		selectionDirection = Direction.NONE;
	}	
	
	public void changeSelection(Direction dir) {
		boolean add = true;
		if(selectionDirection == Direction.NONE) {
			selectionDirection = dir;
		} else if(selectionDirection != dir) {
			add = false;
			selectedSlices.removeLast();
			if(selectedSlices.size() == 1)
				selectionDirection = Direction.NONE;
		} 
		
		if(add) {
			if(dir == Direction.CW) {
				if(getCWSlice() != selectedSlices.getFirst())
					selectedSlices.addLast(getCWSlice());
			} else if(dir == Direction.CCW) {
				if(getCCWSlice() != selectedSlices.getFirst())
					selectedSlices.addLast(getCCWSlice());
			}
		} 
	}
	
	public void insertRing() {
		rings.add(new Ring(" "));
	}
	
	public void insertRing(Ring r, Direction d, boolean clone) {
		int index = rings.indexOf(selectedRing);
		rings.add(index, r);
	}
	
	public void insertGroup(Ring r, Group g) {
		r.getGroups().add(g);
	}
	
	public void insertGroup(Direction d) {
		int index = selectedRing.getGroups().
				indexOf(selectedSlices.getFirst().getGroup());
		if(d == Direction.CCW) {
			index++;
		}
		selectedRing.getGroups().
		add(index, new Group(selectedRing, true));
	}
	
	public void insertSlice(Group g) {
		g.getSlices().add(new Slice(g, "nil"));
	}
	
	public void insertSlice(Direction d) {
		Group g = selectedSlices.getFirst().getGroup();
		g.insertSlice(new Slice(g));
	}
	
	public void insertSlice(Group g, String title) {
		g.getSlices().add(new Slice(g, title));
	}
	
	public void removeSelectedRing() {
		if(rings.size() > 1) {
			rings.remove(selectedRing);
		} else {
			System.out.println("Cannot remove ring");
		}
	}
	
	public void removeSelectedSlices() {
		for(Slice s : selectedSlices) {
			if(removeSlice(s)) {
				
			}
		}
	}
	
	public boolean removeRing(Ring r) {
		if(rings.size() > 1) {
			rings.remove(r);
			return true;
		} else {
			System.out.println("Cannot remove ring");
			return false;
		}
	}
	
	public boolean removeGroup(Group g) {
		if(g.getRing().getGroups().size() > 1) {
			g.getRing().getGroups().remove(g);
			return true;
		} else {
			removeRing(g.getRing());
			return false;
		}
	}
	
	public boolean removeSlice(Slice s) {
		if(s.getGroup().getSlices().size() > 1) {
			s.getGroup().getSlices().remove(s);
			return true;
		} else {
			removeGroup(s.getGroup());
			return false;
		}
	}
	
	public void paintSelection() {
		for(Slice s : selectedSlices)
			s.setColor(Color.red);
	}
	
	public void enumerateSelection() {
		if(selectedSlices.size() > 1) {
			int i = 1;
			for(Slice s : selectedSlices) {
				s.setTitle(Integer.toString(i));
				i++;
			}
		}
	}
	
	public void cloneRing (Direction direction) {
		Ring r = new Ring(getSelectedRing());
		insertRing(r, direction, true);
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
	
	/*
	
	public void shiftRingSelection(Direction direction) {
		float oldSlice = getSelectedSlice();
		float oldSlices = getRings().get(getSelectedRing()).getSlices().size();
		float newSlice = 0, newSlices;
		
		if(direction == Direction.UP) {
			setSelectedRing((getSelectedRing() - 1));
			newSlices = getRings().get(getSelectedRing()).getSlices().size();
			newSlice = ((newSlices/oldSlices) * oldSlice) + ((newSlices/oldSlices) / 2);
		} else if(direction == Direction.DOWN) {
			setSelectedRing((getSelectedRing() + 1));
			newSlices = getRings().get(getSelectedRing()).getSlices().size();
			newSlice = (oldSlice / (oldSlices/newSlices)) + ((newSlices/oldSlices) / 2);
		}
		
		setSelectedSlice((int) newSlice);
	}
		
	
	
	*/

	public void init() {
		setTitle("Mandala");
		
		//New format:
		Ring ring1 = new Ring();
		Group group1 = new Group(ring1, false);
		group1.insertSlice("1/1");
		Group group2 = new Group(ring1, false);
		Slice sl = new Slice(group2, "2/1");
		group2.insertSlice(sl);
		selectedSlices.add(sl);
		group2.insertSlice("2/2");
		
		Group group3 = new Group(ring1, false);
		group3.insertSlice("3/1");
		
		Group group4 = new Group(ring1, false);
		group4.insertSlice("4/1");
		
		insertGroup(ring1, group1);
		insertGroup(ring1, group2);
		insertGroup(ring1, group3);
		insertGroup(ring1, group4);
		
		selectedRing = ring1;
		getRings().add(ring1);
	}
	
	 
}

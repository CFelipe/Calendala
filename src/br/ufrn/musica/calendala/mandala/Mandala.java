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
	private double selectionStart;
	private double selectionExtent;
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
	
	public void shiftRingSelection(Direction direction) {
		int ringIndex = rings.indexOf(selectedRing);
		if(direction == Direction.UP) {
			if(ringIndex > 0) {
				updateSelectionValues();
				selectedRing = rings.get(ringIndex - 1);
			}
		} else if(direction == Direction.DOWN) {
			if(ringIndex < rings.size() - 1) {
				updateSelectionValues();
				selectedRing = rings.get(ringIndex + 1);
			}
		}
	}

	
	public void updateSelectionValues() {
		double angle = 0;
		double groupAngle = 360 / selectedRing.getGroups().size();
		Slice firstSelected = getFirstSelectedSlice();
		Slice lastSelected = getLastSelectedSlice();
		if(firstSelected == lastSelected) {
			angle = groupAngle / firstSelected.getGroup().getSlices().size();
		} else {
			Slice currentlySelected = firstSelected;
			if(selectionDirection == Direction.CW) {
				while(currentlySelected != lastSelected) {
					angle += groupAngle / currentlySelected.getGroup().getSlices().size();
					currentlySelected = getCWSlice(currentlySelected);
				}
			} else if(selectionDirection == Direction.CCW) {
				while(currentlySelected != lastSelected) {
					angle += groupAngle / currentlySelected.getGroup().getSlices().size();
					currentlySelected = getCCWSlice(currentlySelected);
				}
			}
			angle += groupAngle / currentlySelected.getGroup().getSlices().size();
		}
		
		selectionExtent = angle;
		
		angle = 0;
		double sliceAngle;
		for(Group g : selectedRing.getGroups()) {
			sliceAngle = groupAngle / g.getSlices().size();
			for(Slice s : g.getSlices()) {
				if(s == selectedSlices.getFirst()) {
					selectionStart = angle;
					if(selectionDirection == Direction.CCW)
						selectionStart += sliceAngle;
					selectionStart %= 360;
					return;
				}
				angle += sliceAngle;
			}
		}
	}
	
	public void updateSelectedSlices() {
		selectedSlices.clear();
		double angle = 0;
		double extent = selectionExtent;
		double groupAngle = 360 / selectedRing.getGroups().size();
		Slice s1 = selectedRing.getGroups().get(0).getSlices().get(0);
		Slice s2 = s1;
		do {
			if(angle >= selectionStart) {
				selectedSlices.add(s2);
				extent -= groupAngle / s2.getGroup().getSlices().size();
			}
			s2 = getCWSlice(s2);
			angle += groupAngle / s2.getGroup().getSlices().size();
		} while(extent > 0);
	}
	
	private Slice getCWSlice(Slice s) {
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
	
	private Slice getCCWSlice(Slice s) {
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
		Slice s = selectedSlices.getFirst();
		if(direction == Direction.CW) {
			if(selectionDirection == Direction.NONE) {
				s = getCWSlice(selectedSlices.getFirst());
			} else if(selectionDirection == Direction.CW) {
				//s = getCWSlice(selectedSlices.getLast());
				s = selectedSlices.getLast();
			} else if(selectionDirection == Direction.CCW) {
				s = selectedSlices.getFirst();
			}
		} else if(direction == Direction.CCW) {
			if(selectionDirection == Direction.NONE) {
				s = getCCWSlice(selectedSlices.getFirst());
			} else if(selectionDirection == Direction.CW) {
				s = selectedSlices.getFirst();
			} else if(selectionDirection == Direction.CCW) {
				//s = getCCWSlice(selectedSlices.getLast());
				s = selectedSlices.getLast();
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
	
	public void insertRing(Direction dir) {
		Ring r = new Ring(" ");
		if(dir == Direction.UP) {
			rings.add(rings.indexOf(selectedRing), r);
		} else if(dir == Direction.DOWN) {
			rings.add(rings.indexOf(selectedRing) + 1, r);
		}
		selectedRing = r;
		selectedSlices.clear();
		selectedSlices.add(selectedRing.getGroups().get(0).getSlices().get(0));
	}
	
	public void insertRing(Ring r, Direction dir) {
		if(dir == Direction.UP) {
			rings.add(rings.indexOf(selectedRing), r);
		} else if(dir == Direction.DOWN) {
			rings.add(rings.indexOf(selectedRing) + 1, r);
		}
		selectedRing = r;
		selectedSlices.clear();
		selectedSlices.add(selectedRing.getGroups().get(0).getSlices().get(0));
	}
	
	public void cloneRing (Direction direction) {
		Ring r = new Ring(selectedRing);
		insertRing(r, direction);
	}
	
	
	public void cloneRing(Ring r, Direction dir) {
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
				selectedRing = rings.get(0);
				selectedSlices.clear();
				selectedSlices.add(rings.get(0).getGroups().get(0).getSlices().get(0));
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

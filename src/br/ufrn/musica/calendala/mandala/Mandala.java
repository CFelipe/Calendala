package br.ufrn.musica.calendala.mandala;

import java.util.ArrayList;
import java.util.LinkedList;

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
//	private Direction selectionDirection = Direction.NONE;
	
	public enum Position {BEFORE, AFTER};
	
	public Mandala() {
		title = "Untitled";
		rings = new ArrayList<Ring>();
		//selectedRing = 0;
		selectedSlices = new LinkedList<Slice>();
		selectedSlices.add(rings.get(0).getGroups().get(0).getSlices().get(0));
	}
	
	public Mandala(String title) {
		this.title = title;
		rings = new ArrayList<Ring>();
		//selectedRing = 0;
		selectedSlices = new LinkedList<Slice>();
		selectedSlices.add(rings.get(0).getGroups().get(0).getSlices().get(0));
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
		//?
	}
	
	public void insertRing() {
		rings.add(new Ring("nil"));
	}
	
	public void insertGroup(Ring r) {
		r.getGroups().add(new Group(r));
	}
	
	public void insertSlice(Group g) {
		g.getSlices().add(new Slice(g, "nil"));
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
	
	/*
	private int getCWNext() {
		return (selectedSlices.getLast() + 1) % 
						getRings().get(getSelectedRing()).getSlices().size();
	}
	
	private int getCCWNext() {
		if(selectedSlices.getLast() == 0) {
			return getRings().get(getSelectedRing()).getSlices().size() - 1;
		} else {
			return selectedSlices.getLast() - 1;
		}
		
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
		} else if(selectionDirection == dir) {
			// Nada
		}
		
		if(add) {
			if(dir == Direction.CW) {
				if(getCWNext() != selectedSlices.getFirst())
					selectedSlices.addLast(getCWNext());
			} else if(dir == Direction.CCW) {
				if(getCCWNext() != selectedSlices.getFirst())
					selectedSlices.addLast(getCCWNext());
			}
		} 
	}
	
	public void rotateSelectedSlice(Direction direction) {
		if(direction == Direction.CW) {
			if(selectionDirection == Direction.CW)
				setSelectedSlice((selectedSlices.getLast() + 1));
			else
				setSelectedSlice((selectedSlices.getFirst() + 1));
		} else if(direction == Direction.CCW) {
			if(selectionDirection == Direction.CW)
				setSelectedSlice((selectedSlices.getFirst() - 1));
			else
				setSelectedSlice((selectedSlices.getLast() - 1));
		}
	}	
	
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
		public void insertSlice(Position position) {
		if(position == Position.BEFORE) {
			rings.get(getSelectedRing()).getSlices().add(getSelectedSlice(), new Slice(" "));
			setSelectedSlice((getSelectedSlice()));
		} else if(position == Position.AFTER) {
			rings.get(getSelectedRing()).getSlices().add(getSelectedSlice() + 1, new Slice(" "));
			setSelectedSlice((getSelectedSlice() + 1));
		}
		
	}	
	
	public void insertRing(Direction direction) {
		if(direction == Direction.UP) {
			rings.add(this.getSelectedRing(), new Ring(" "));
			setSelectedRing((getSelectedRing()));
		} else if(direction == Direction.DOWN) {
			rings.add(this.getSelectedRing() + 1, new Ring(" "));
			setSelectedRing((getSelectedRing()) + 1);
		}
		setSelectedSlice(0);
	}
		public void insertRing(Ring r, Direction direction, boolean clone) {
		if(direction == Direction.UP) {
			rings.add(this.getSelectedRing(), r);
			setSelectedRing((getSelectedRing()));
		} else if(direction == Direction.DOWN) {
			rings.add(this.getSelectedRing() + 1, r);
			setSelectedRing((getSelectedRing()) + 1);
		}
		
		if(!clone) {
			setSelectedSlice(0);
		}
	}
		public void removeRing() {
		if(rings.size() > 1) {
			rings.remove(getSelectedRing());
			setSelectedRing(getSelectedRing() - 1);
			setSelectedSlice(0);
		} else {
			// Mandala só tem um anel
			rings.clear();
			rings.add(new Ring(" "));
			setSelectedSlice(0);
		}
	}	
	public void removeGroups() {
		if(rings.get(selectedRing).getSlices().size() > 1) {
			rings.get(selectedRing).getSlices().remove(getSelectedSlice());
			setSelectedSlice(getSelectedSlice() - 1);
			
		} else {
			removeRing();
			setSelectedSlice(0);
		}
	}
	
	public void cloneRing (Direction direction) {
		Ring r = new Ring(getRings().get(getSelectedRing()));
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
	
	public void enumerateSelection() {
		if(selectedSlices.size() > 1) {
			for(int i = 0; i < selectedSlices.size(); i++) {
				rings.get(getSelectedRing()).getSlices().
				get(selectedSlices.get(i)).setTitle(Integer.toString(i));
			}
		}
	}
	
	public void paintSelection() {
		for(int i = 0; i < selectedSlices.size(); i++) {
			rings.get(getSelectedRing()).getSlices().
			get(selectedSlices.get(i)).setColor(Color.red);
		}
	}
	*/

	public void init() {
		setTitle("Mandala");
		
		//Old format:
		/*
		// Circle of fifths
		Ring ring1 = new Ring("C");
		ring1.getSlices().add(new Slice("F"));
		ring1.getSlices().add(new Slice("Bb"));
		ring1.getSlices().add(new Slice("Eb"));
		ring1.getSlices().add(new Slice("Ab"));
		ring1.getSlices().add(new Slice("Db"));
		ring1.getSlices().add(new Slice("Gb"));
		ring1.getSlices().add(new Slice("B"));
		ring1.getSlices().add(new Slice("E"));
		ring1.getSlices().add(new Slice("A"));
		ring1.getSlices().add(new Slice("D"));
		ring1.getSlices().add(new Slice("G"));
		getRings().add(ring1);
		
		Ring ring2 = new Ring("Am");
		ring2.getSlices().add(new Slice("Dm"));
		ring2.getSlices().add(new Slice("Gm"));
		ring2.getSlices().add(new Slice("Cm"));
		ring2.getSlices().add(new Slice("Fm"));
		ring2.getSlices().add(new Slice("Bbm"));
		ring2.getSlices().add(new Slice("Ebm"));
		ring2.getSlices().add(new Slice("Abm"));
		ring2.getSlices().add(new Slice("Dbm"));
		ring2.getSlices().add(new Slice("Gbm"));
		ring2.getSlices().add(new Slice("Bm"));
		ring2.getSlices().add(new Slice("Em"));
		getRings().add(ring2);
		
		Ring ring3 = new Ring(" ");
		getRings().add(ring3);
		
		setSelectedRing(0);
		setSelectedSlice(0);
		*/
		
		/*
		//New format:
		Ring ring1 = new Ring("C");
		ring1.getGroups().get(0).addSlice(new Slice("F"));
		ring1.getGroups().get(0).addSlice(new Slice("Bb"));
		ring1.getGroups().get(0).addSlice(new Slice("Eb"));
		ring1.getGroups().get(0).addSlice(new Slice("Ab"));
		ring1.getGroups().get(0).addSlice(new Slice("Db"));
		ring1.getGroups().get(0).addSlice(new Slice("Gb"));
		ring1.getGroups().get(0).addSlice(new Slice("B"));
		ring1.getGroups().get(0).addSlice(new Slice("E"));
		ring1.getGroups().get(0).addSlice(new Slice("A"));
		ring1.getGroups().get(0).addSlice(new Slice("D"));
		ring1.getGroups().get(0).addSlice(new Slice("G"));
		getRings().add(ring1);
		
		Ring ring2 = new Ring("Am");
		ring2.getGroups().get(0).addSlice(new Slice("Dm"));
		ring2.getGroups().get(0).addSlice(new Slice("Gm"));
		ring2.getGroups().get(0).addSlice(new Slice("Cm"));
		ring2.getGroups().get(0).addSlice(new Slice("Fm"));
		ring2.getGroups().get(0).addSlice(new Slice("Bbm"));
		ring2.getGroups().get(0).addSlice(new Slice("Ebm"));
		ring2.getGroups().get(0).addSlice(new Slice("Abm"));
		ring2.getGroups().get(0).addSlice(new Slice("Dbm"));
		ring2.getGroups().get(0).addSlice(new Slice("Gbm"));
		ring2.getGroups().get(0).addSlice(new Slice("Bm"));
		ring2.getGroups().get(0).addSlice(new Slice("Em"));
		getRings().add(ring2);
		
		Ring ring3 = new Ring(" ");
		getRings().add(ring3);
		*/
		
	}
	
	 
}

package br.ufrn.musica.calendala.mandala;

import java.util.Collections;

import br.ufrn.musica.calendala.util.CircularArrayList;

/**
 * @author Felipe Cortez de Sá
 * @version 0.2
 * @since 0.1
 */

public class Ring {
	private CircularArrayList<Slice> slices;
	private int subdivisions;
	private int totalDivs;

	public enum Direction {CW, CCW, UP, DOWN, NONE}
	
	public Ring() {
		slices = new CircularArrayList<Slice>();
		subdivisions = 12;
		totalDivs = subdivisions;
		for(int i = 0; i < subdivisions; i++) {
			Slice s = new Slice(i, this);
			slices.add(s);
		}
	}

	public Ring(int subdivisions) {
		slices = new CircularArrayList<Slice>();
		this.subdivisions = subdivisions;
		totalDivs = subdivisions;
		for(int i = 0; i < subdivisions; i++) {
			Slice s = new Slice(i, this);
			slices.add(s);
		}
	}
	
	public Ring(int subdivisions, int totalDivs) {
		this.subdivisions = subdivisions;
		this.totalDivs = totalDivs;
		slices = new CircularArrayList<Slice>();
	}

	public int getSubdivisions() {
		return subdivisions;
	}

	public void setSubdivisions(int subdivisions) {
		this.subdivisions = subdivisions;
	}

	public int getTotalDivs() {
		return totalDivs;
	}

	public void addMerge(int start, int size) {
		for(int i = 0; i < size; i++) {
			slices.get(start + i).setStart(start);
			slices.get(start + i).setMergeSize(1);
			slices.get(start + i).setTitle(" ");
		}
		slices.get(start).setMergeSize(size);
	}

	public void mergeCells(int start, int quantity, Direction direction) {
		int size = 0;
		int slice = start;
		if(direction == Direction.CW) {
			int i = 0;
			do {
				size += slices.get(slice).getMergeSize();
				slice = slices.get(slice).getNext();
				i++;
			} while(i < quantity);
			addMerge(start, size);
		} else if(direction == Direction.CCW) {
			// Messy but it works! Fix this later
			int i = 0;
			do {
				size += slices.get(slice).getMergeSize();
				slice = slices.get(slice).getPrev();
				i++;
			} while(i < quantity);
			slice = slices.get(slice).getNext();
			addMerge(slice, size);
			Mandala.getInstance().setSelectionStart(slice);
		}

		totalDivs -= (quantity - 1);
		// Selects the merged cell
		Mandala.getInstance().setSelectionRange(0);
	}
	
	public void enumerateRing(int start, int quantity, Direction direction) {
		int slice = start;
		if(direction == Direction.CW) {
			int i = 0;
			do {
				slices.get(slice).setTitle(Integer.toString(i + 1));
				slice = slices.get(slice).getNext();
				i++;
			} while(i < quantity);
		} else if(direction == Direction.CCW) {
			int i = 0;
			do {
				slices.get(slice).setTitle(Integer.toString(i + 1));
				slice = slices.get(slice).getPrev();
				i++;
			} while(i < quantity);
		}
	}
	
	public void removeMerge(int start) {
		int mergeSize = slices.get(start).getMergeSize();
		if(mergeSize > 1) {
			slices.get(start).setMergeSize(1);
			for(int i = 1; i < mergeSize; i++) {
				slices.get(start + i).setStart(start + i);
				totalDivs++;
			}
		}
	}
	
	public void unmergeCells(int start, int quantity, Direction direction) {
		int slice = start;
		int next;
		int size = 0;
		if(direction == Direction.CW) {
			int i = 0;
			do {
				size += slices.get(slice).getMergeSize();
				next = slices.get(slice).getNext();
				removeMerge(slice);
				slice = next;
				i++;
			} while(i < quantity);
		} else if(direction == Direction.CCW) {
			int i = 0;
			do {
				size += slices.get(slice).getMergeSize();
				next = slices.get(slice).getPrev();
				removeMerge(slice);
				slice = next;
				i++;
			} while(i < quantity);
			slice = slices.get(slice).getNext();
			Mandala.getInstance().setSelectionStart(slice);

		}

		Mandala.getInstance().setSelectionRange(size - 1);
	}
	
	public void rotate(Direction direction) {
		int start;
		if(direction == Direction.CW) {
			for(Slice s : slices) {
				start = s.getStart();
				s.setStart((start + 1) % subdivisions);
			}
			Collections.rotate(slices, 1);
	    	Mandala.getInstance().changeSelectionStart(1);
		} else {
			for(Slice s : slices) {
				start = s.getStart();
				if(start - 1 < 0) {
					s.setStart((start - 1 + subdivisions));
				}
				else {
					s.setStart((start - 1));
				}
			}
			Collections.rotate(slices, -1);
	    	Mandala.getInstance().changeSelectionStart(-1);
		}
	}
	
	public CircularArrayList<Slice> getSlices() {
		return slices;
	}
	
	public void setSlices(CircularArrayList<Slice> slices) {
		this.slices = slices;
	}
}

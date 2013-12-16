package br.ufrn.musica.calendala.mandala;

import br.ufrn.musica.calendala.util.CircularArrayList;

/**
 * @author Felipe Cortez de Sá
 * @version 0.2
 * @since 0.1
 */

public class Ring {
	private CircularArrayList<Slice> slices;
	private int subdivisions;

	public enum Direction {CW, CCW, UP, DOWN, NONE}
	
	public Ring() {
		slices = new CircularArrayList<Slice>(); // Maybe change to ArrayList later
		subdivisions = 12;
		for(int i = 0; i < subdivisions; i++) {
			Slice s = new Slice(i, this);
			slices.add(s);
		}
	}

	public Ring(int subdivisions) {
		slices = new CircularArrayList<Slice>();
		this.subdivisions = subdivisions;
		for(int i = 0; i < subdivisions; i++) {
			Slice s = new Slice(i, this);
			slices.add(s);
		}
	}
	
	public int getSubdivisions() {
		return subdivisions;
	}

	public void setSubdivisions(int subdivisions) {
		this.subdivisions = subdivisions;
	}
	
	public void addMerge(int start, int size) {
		for(int i = 0; i < size; i++) {
			slices.get(start + i).setPos(start);
		}
		slices.get(start).setMergeSize(size);
	}
	
	public void rotate(Direction direction) {
	}
	
	public CircularArrayList<Slice> getSlices() {
		return slices;
	}
}
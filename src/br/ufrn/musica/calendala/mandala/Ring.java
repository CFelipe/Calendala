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
		slices = new CircularArrayList<Slice>();
		subdivisions = 60;
	}
	
	public Ring(String sliceTitle) {
		slices = new CircularArrayList<Slice>();
	}
	
	public Ring(Ring source) {
	}
	
	public int getSubdivisions() {
		return subdivisions;
	}

	public void setSubdivisions(int subdivisions) {
		this.subdivisions = subdivisions;
	}
	
	public void rotate(Direction direction) {
		// Not so easy now!
		/*
		if(direction == Direction.CW) {
			Collections.rotate(slices, 1);
		} else {
			Collections.rotate(slices, -1);
		}
		*/
	}
	
	public CircularArrayList<Slice> getSlices() {
		return slices;
	}
}
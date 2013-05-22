package br.ufrn.musica.calendala.mandala;

import java.util.Collections;

import br.ufrn.musica.calendala.util.CircularArrayList;

/**
 * @author Felipe Cortez de Sá
 * @version 0.1
 * @since 0.1
 */

public class Ring {
	private CircularArrayList<Slice> slices;
	
	public enum Direction {CW, CCW, UP, DOWN, NONE}
	
	public Ring() {
		slices = new CircularArrayList<Slice>();
	}
	
	public Ring(String sliceTitle) {
		slices = new CircularArrayList<Slice>();
		slices.add(new Slice(sliceTitle));
	}
	
	// For copy-pasting and cloning
	public Ring(Ring source) {
		slices = new CircularArrayList<Slice>();
		for(Slice s : source.slices) {
			slices.add(new Slice(s));
		}
	}
	
	/** Rotates elements in an CircularArrayList, e.g.:
	 *<p> S  :  1, 2, 3, 4
	 *<p> S  :  4, 1, 2, 3
	 * @param direction 
	 * Clockwise or counter-clockwise ring rotation
	 */
	public void rotate(Direction direction) {
		if(direction == Direction.CW) {
			Collections.rotate(slices, 1);
		} else {
			Collections.rotate(slices, -1);
		}
	}
	
	public CircularArrayList<Slice> getSlices() {
		return slices;
	}
	
}
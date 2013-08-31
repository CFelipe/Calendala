package br.ufrn.musica.calendala.mandala;

import java.util.LinkedList;

public class Group {
	private LinkedList<Slice> slices;
	private Ring ring;
	
	public Group(Ring ring, boolean createSlice) {
		slices = new LinkedList<Slice>();
		this.ring = ring;
		if(createSlice)
			insertSlice(" ");
	}
	
	public Group(Ring ring, String s) {
		slices = new LinkedList<Slice>();
		this.ring = ring;
		insertSlice(s);
	}

	public Group(Group source, Ring r) {
		slices = new LinkedList<Slice>();
		for(Slice s : source.slices) {
			slices.add(new Slice(s, this));
		}
		ring = r;
	}

	public LinkedList<Slice> getSlices() {
		return slices;
	}

	public void setSlices(LinkedList<Slice> slices) {
		this.slices = slices;
	}
	
	public Ring getRing() {
		return ring;
	}

	public void setRing(Ring ring) {
		this.ring = ring;
	}
	
	public void insertSlice(Slice s) {
		slices.add(s);
	}
	
	public void insertSlice(String s) {
		slices.add(new Slice(this, s));
	}
}

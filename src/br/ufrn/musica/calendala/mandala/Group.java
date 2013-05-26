package br.ufrn.musica.calendala.mandala;

import java.util.ArrayList;

public class Group {
	private ArrayList<Slice> slices;
	private Ring ring;
	
	public Group(Ring ring) {
		slices = new ArrayList<Slice>();
		this.ring = ring;
	}
	
	public Group(Ring ring, String s) {
		slices = new ArrayList<Slice>();
		this.ring = ring;
	}

	//For copy-pasting and cloning
	public Group(Group source) {
		slices = new ArrayList<Slice>();
		for(Slice s : source.slices) {
			slices.add(new Slice(s));
		}
		ring = source.ring;
	}

	public ArrayList<Slice> getSlices() {
		return slices;
	}

	public void setSlices(ArrayList<Slice> slices) {
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

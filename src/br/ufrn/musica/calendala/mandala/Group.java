package br.ufrn.musica.calendala.mandala;

import java.util.ArrayList;

public class Group {
	private ArrayList<Slice> slices;
	
	public Group() {
		slices = new ArrayList<Slice>();
		slices.add(new Slice());
	}
	
	public Group(String s) {
		slices = new ArrayList<Slice>();
		slices.add(new Slice(s));
	}
	
	//For copy-pasting and cloning
	public Group(Group source) {
		slices = new ArrayList<Slice>();
		for(Slice s : source.slices) {
			slices.add(new Slice(s));
		}
	}

	public ArrayList<Slice> getSlices() {
		return slices;
	}

	public void setSlices(ArrayList<Slice> slices) {
		this.slices = slices;
	}
	
	public void addSlice(Slice s) {
		slices.add(s);
	}
	
	public void addSlice(String s) {
		slices.add(new Slice(s));
	}
	
	public void removeFirstSlice() {
		if(slices.size() > 1) {
			slices.remove(0);
		}
	}
	
	public void removeSlice(Slice s) {
		if(slices.size > 1) {
			slices.remove(s);
		}
	}

}

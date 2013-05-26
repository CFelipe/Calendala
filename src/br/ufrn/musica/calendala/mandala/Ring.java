package br.ufrn.musica.calendala.mandala;

import java.util.Collections;

import br.ufrn.musica.calendala.util.CircularArrayList;

/**
 * @author Felipe Cortez de Sá
 * @version 0.1
 * @since 0.1
 */

public class Ring {
	private CircularArrayList<Group> groups;
	
	public enum Direction {CW, CCW, UP, DOWN, NONE}
	
	public Ring() {
		groups = new CircularArrayList<Group>();
	}
	
	public Ring(String sliceTitle) {
		groups = new CircularArrayList<Group>();
		groups.add(new Group(this, sliceTitle));
	}
	
	// For copy-pasting and cloning
	public Ring(Ring source) {
		groups = new CircularArrayList<Group>();
		for(Group s : source.groups) {
			groups.add(new Group(s));
		}
	}
	
	public void rotate(Direction direction) {
		if(direction == Direction.CW) {
			Collections.rotate(groups, 1);
		} else {
			Collections.rotate(groups, -1);
		}
	}
	
	public CircularArrayList<Group> getGroups() {
		return groups;
	}
}
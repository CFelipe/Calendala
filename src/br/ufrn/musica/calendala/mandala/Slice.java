package br.ufrn.musica.calendala.mandala;

import java.awt.Color;

/**
 * @author Felipe Cortez de Sá
 * @version 0.2
 * @since 0.1
 */

public class Slice {
	private String title;
	private Color color;
	private int mergeSize;
	private int pos;
	private Ring ring;
	
	public Slice(int pos, Ring r) {
		// There's a blank character so saving works
		title = " ";
		// Positions are 0-indexed (makes drawing easier)
		this.pos = pos;
		// mergeSize bigger than 1 means there's a merge
		this.mergeSize = 1;
		this.ring = r;
		color = Color.white; // Maybe change to 'transparent' later
	}

	public Slice(int pos, int mergeSize, Ring r) {
		title = " ";
		this.pos = pos;
		this.mergeSize = mergeSize;
		this.ring = r;
		color = Color.white;
	}
	
	public void setMergeSize(int mergeSize) {
		this.mergeSize = mergeSize;
	}

	public int getMergeSize() {
		return mergeSize;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public int getStart() {
		return pos;
	}
	
	public void setPos(int start) {
		this.pos = start;
	}

	public Ring getRing() {
		return ring;
	}
	
}
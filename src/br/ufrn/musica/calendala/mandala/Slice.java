package br.ufrn.musica.calendala.mandala;

import java.awt.Color;

/**
 * @author Felipe Cortez de Sá
 * @version 0.1
 * @since 0.1
 */

public class Slice {
	private String title;
	private Color color;
	private Slice mergedSlice;
	private int mergeSize;
	private Ring ring;
	private int pos;
	
	public Slice(int pos, Ring r) {
		title = " ";
		color = Color.white;
		mergedSlice = this;
		this.pos = pos;
		this.ring = r; 
	}
	
	public void setMergedSlice(Slice mergedSlice) {
		this.mergedSlice = mergedSlice;
	}

	public Slice getMergedSlice() {
		return mergedSlice;
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
	
	public int getPos() {
		return pos;
	}
	
	public void setPos(int pos) {
		this.pos = pos;
	}

	public Ring getRing() {
		return ring;
	}
	
}
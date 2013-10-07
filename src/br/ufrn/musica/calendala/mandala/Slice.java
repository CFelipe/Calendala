package br.ufrn.musica.calendala.mandala;

import java.awt.Color;

/**
 * @author Felipe Cortez de Sá
 * @version 0.1
 * @since 0.5
 */

public class Slice {
	private String title;
	private Color color;
	private Slice mergedSlice;
	
	public Slice() {
		title = " ";
		color = Color.white;
		mergedSlice = null;
	}
	
	public void setMergedSlice(Slice mergedSlice) {
		this.mergedSlice = mergedSlice;
	}
	
	public Slice getMergedSlice() {
		return mergedSlice;
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
	
}
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
	private Group group;
	
	public Slice(Group group) {
		this.title = " ";
		this.color = Color.WHITE;
		this.group = group;
	}
	
	public Slice(Group group, String title) {
		this.title = title;
		this.color = Color.WHITE;
		this.group = group;
	}

	public Slice(Group group, String title, Color color) {
		this.title = title;
		this.color = color;
		this.group = group;
	}
	
	public Slice(Slice source) {
		this.title = source.title;
		this.color = source.color;
		this.group = source.group;
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
	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
}
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
	private int weight;
	private Group group;
	
	public Slice(Group group) {
		this.title = "Untitled";
		this.color = Color.WHITE;
		this.weight = 1;
		this.group = group;
	}
	
	public Slice(Group group, String title) {
		this.title = title;
		this.color = Color.WHITE;
		this.weight = 1;
		this.group = group;
	}

	public Slice(Group group, String title, Color color) {
		this.title = title;
		this.color = color;
		this.weight = 1;
		this.group = group;
	}
	
	public Slice(Group group, String title, Color color, int weight) {
		this.title = title;
		this.color = color;
		this.weight = weight;
		this.group = group;
	}
	
	public Slice(Slice source) {
		this.title = source.title;
		this.color = source.color;
		this.weight = source.weight;
		this.group = source.group;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
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
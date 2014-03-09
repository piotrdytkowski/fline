package com.flyne.menu.highscores;

public class HighScoreEntry {
	private int id;
	private int position;
	private String name;
	private int score;
	public HighScoreEntry(int id, int position, String name, int score) {
		super();
		this.id = id;
		this.position = position;
		this.name = name;
		this.score = score;
	}
	public int getId() {
		return id;
	}
	public int getPosition() {
		return position;
	}
	public String getName() {
		return name;
	}
	public int getScore() {
		return score;
	}
	@Override
	public String toString() {
		return position + ". " + name + ": " + score;
	}
	
}

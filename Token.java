package com.game;

public class Token {
	int position;
	char color;
	boolean isInHomeTerritory;
	boolean hasReachedHome;
	
	public Token(int p, char c, boolean ht,boolean rh)
	{
		position = p;
		color = c; 
		isInHomeTerritory = ht;
		hasReachedHome = rh;
	}
	
	public char getColor() {
		return color;
	}
	public void setColor(char color) {
		this.color = color;
	}
	public boolean hasReachedHome() {
		return hasReachedHome;
	}
	public void setHasReachedHome(boolean hasReachedHome) {
		this.hasReachedHome = hasReachedHome;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public boolean isInHomeTerritory() {
		return isInHomeTerritory;
	}
	public void setIsInHomeTerritory(boolean isInHomeTerritory) {
		this.isInHomeTerritory = isInHomeTerritory;
	}
}

package game;

public class Token {
	int position;
	char color;
	boolean isInHomeTerritory;
	boolean hasReachedHome;
	int startPosition;
	int endPosition;
	
	int distanceTravelled;
	
	
	public Token(int p, char c, boolean ht,boolean rh)
	{
		position = p;
		color = c; 
		isInHomeTerritory = ht;
		hasReachedHome = rh;
		distanceTravelled = 0;
		assignStartAndEndPositions();
	}
	
	public int getDistanceTravelled() {
		return distanceTravelled;
	}

	public void setDistanceTravelled(int distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}

	
	public int getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}

	public int getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(int endPosition) {
		this.endPosition = endPosition;
	}
	
	private void assignStartAndEndPositions()
	{
		if(color == 'r')
		{
			startPosition = 0;
			endPosition = 50;
		}
		else if(color == 'b')
		{
			startPosition = 13;
			endPosition = 11;
		}
		else if(color == 'y')
		{
			startPosition = 26;
			endPosition = 24;
		}
		else
		{
			startPosition = 39;
			endPosition = 37;
		}
	
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

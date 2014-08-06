package game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
	private static ArrayList<Token> redTokens =  new ArrayList<Token>();
	private static ArrayList<Token> blueTokens =  new ArrayList<Token>();
	private static ArrayList<Token> yellowTokens =  new ArrayList<Token>();
	private static ArrayList<Token> greenTokens =  new ArrayList<Token>();
	private static int numOfPlayers = 0;
	private static int dieValue = 0;
	
	public static int getNumOfPlayers()
	{
		do
		{
			System.out.println("Enter the number of players:");
			Scanner scanner = new Scanner(System.in);
			numOfPlayers = scanner.nextInt();
		}while(numOfPlayers >= 5);
		return numOfPlayers;
	}
	
	public static void main(String [] args)
	{
		numOfPlayers = getNumOfPlayers();
		initializeBoard();
		char currentTurn = randomizedStart();
		while(!isGameOver())
		{			
			currentTurn = playGame(currentTurn);
		}
	}

	private static char playGame(char currentTurn) {
		dieValue = getDieValue();
		if(dieValue != 0 ) // triple six foul 
		{
			
			showCurrentStatus();
			System.out.println(currentTurn + " rolled: " +dieValue);
			boolean successfulMove=false;
			if(isMovePossible(dieValue, currentTurn))
			{
				while(!successfulMove)
				{
					int tokenIndex = askForMove(currentTurn);
					tokenIndex -= 1;
					if(isValidMove(currentTurn,tokenIndex))
					{
						if(makeMove(currentTurn, tokenIndex))
						{
							successfulMove = true;
						}
					}
					else
					{
						System.out.println("Please Enter a valid move.");
					}
				}
			}
			else
			{
				System.out.println("No Move Possible. Turn skipped for " + currentTurn);
			}
			currentTurn = getNextTurn(currentTurn);	
		}
		else
		{
			System.out.println("Turn skipped. Next Turn.");
			currentTurn = getNextTurn(currentTurn);
		}
		return currentTurn;
	}

	private static boolean isMovePossible(int dieValue, char currentTurn) {
		
		ArrayList<Token> tokenList =  getPlayerTokenList(currentTurn);
		
		for(Token t: tokenList)
		{
			if(t.getPosition() != -1)
				return true;
		}		
		if(dieValue >= 6)
		{
			return true;
		}
		return false;
	}

	public static int getDieValue() {
		dieValue = rollTheDie();		
		if (dieValue == 6)
		{
			dieValue += rollTheDie();
			if(dieValue == 12)
			{
				dieValue += rollTheDie();
			}
		}
		if (dieValue == 18)
		{
			dieValue = 0;
		}
		return dieValue;
	}
	
	private static boolean willExceedGeneralTerritory(Token currentToken)
	{
		if(currentToken.distanceTravelled + dieValue >=  50 && currentToken.distanceTravelled < 50)
		{
			return true;
		}
		return false;
	}

	public static boolean makeMove(char currentTurn, int tokenIndex) 
	{
		ArrayList<Token> tokenList =  getPlayerTokenList(currentTurn);
		Token currentToken = tokenList.get(tokenIndex);
		
		if(currentToken.isInHomeTerritory() == true)
		{
			if(currentToken.getDistanceTravelled() + dieValue < 56)
			{
				currentToken.setDistanceTravelled(currentToken.getDistanceTravelled()+dieValue);
				currentToken.setPosition(currentToken.getPosition()+dieValue);
			}
			else if(currentToken.getDistanceTravelled() + dieValue == 56)
			{
				currentToken.setDistanceTravelled(currentToken.getDistanceTravelled()+dieValue);
				currentToken.setHasReachedHome(true);
			}
			else
			{
				System.out.println("Not a valid move.");
				return false;
				// TODO Get further input from user if moves are possible else skip turn
			}			
			return true;
		}
		else
		{
			int newPosition=0;
			if(currentToken.getPosition() == -1 && dieValue >= 6)
			{
				dieValue -= 6;
				newPosition = currentToken.startPosition + dieValue;
			}
			else if(currentToken.getPosition() == -1 && dieValue != 6)
			{
				newPosition = -1;
				return false;
			}
			else
			{
				newPosition = currentToken.getPosition() + dieValue;
			}
			newPosition = newPosition % 51;
			char occupiedColor=' ';
			if((occupiedColor = getOccupant(newPosition,currentTurn)) != ' ')
			{
				removeToken(newPosition,occupiedColor);
			}
			if(willExceedGeneralTerritory(currentToken))
			{
				currentToken.setIsInHomeTerritory(true);
				currentToken.setPosition(currentToken.getPosition() + dieValue - currentToken.getEndPosition());
			}
			else
			{
				currentToken.setPosition(newPosition);
			}
			currentToken.distanceTravelled += dieValue;			
			return true;
		}
	}
	
	private static void removeToken(int newPosition, char occupiedColor) 
	{		
		ArrayList<Token> appropriateList = getPlayerTokenList(occupiedColor);
		for(Token r: appropriateList)
		{
			if(r.getPosition() == newPosition)
			{
				r.setPosition(-1);
				r.setDistanceTravelled(0);
			}
		}		
	}

	private static char getOccupant(int newPosition,char currentTurn) 
	{	
		String order = "rbyg".replace(String.valueOf(currentTurn), "");
		for(char c:order.toCharArray())
		{
			ArrayList<Token> appropriateList = getPlayerTokenList(c);
			for(Token r: appropriateList)
			{
				if(r.getPosition() == newPosition)
					return r.getColor();	
			}		
		}
		return ' ';
	}

	public static boolean isValidMove(char currentTurn, int tokenIndex) 
	{		
		ArrayList<Token> tokenList =  getPlayerTokenList(currentTurn);
		Token currentToken = tokenList.get(tokenIndex);
		int newPosition = currentToken.getPosition() + dieValue;

		/*
		if(currentToken.getPosition() == -1 && dieValue >= 6)
		{
			dieValue = dieValue - 6;
			return true;
		}
		*/
		//same color check
		for(Token t: tokenList)
		{
			if(t.getPosition() == newPosition)
				return false;
		}
		return true;
	}

	@SuppressWarnings("resource")
	private static int askForMove(char currentTurn) 
	{
		System.out.println("Player " + currentTurn + " Which token number do you want to use:");
		Scanner ob = new Scanner(System.in);
		int token  = ob.nextInt();
		return token;
	}
	
	private static void displayPlayerTokens(ArrayList<Token> tokens)
	{
		for(int i=0;i<tokens.size();i++)
		{
			System.out.println("token: " + tokens.get(i).getColor() + (i+1) +" position: "+tokens.get(i).getPosition() + " distance : " +tokens.get(i).getDistanceTravelled() + " " + tokens.get(i).isInHomeTerritory());
			
		}
	}

	private static void showCurrentStatus() 
	{
		System.out.println("Current Board:");
		String order = "rbyg";
		String playerArrangement = order.substring(0,numOfPlayers);
		for(char c : playerArrangement.toCharArray())
		{
			displayPlayerTokens(getPlayerTokenList(c));
		}		
	}

	private static int rollTheDie() 
	{
		Random r = new Random();
		int maxValue = 7;
		return r.nextInt(maxValue-1)+1;
	}

	public static char randomizedStart() 
	{
		Random r = new Random();
		int maxValue = numOfPlayers;
		String order = "rbyg";
		String playerArrangement = order.substring(0,numOfPlayers);
		
		return playerArrangement.toCharArray()[r.nextInt(maxValue-1)];
	}

	public static char getNextTurn(char currentTurn) 
	{
		String order = "rbyg";
		String playerArrangement = order.substring(0,numOfPlayers);		
		int index = playerArrangement.indexOf(currentTurn);
		
		return playerArrangement.charAt((index + 1) % numOfPlayers);
	}

	public static boolean isGameOver() 
	{		
		String order = "rbyg";
		String playerArrangement = order.substring(0,numOfPlayers);
		
		for(char c : playerArrangement.toCharArray())
		{
			boolean allReachedHome = false;
			ArrayList<Token> appropriateList = getPlayerTokenList (c);
			for(Token t : appropriateList)
			{
				if (t.hasReachedHome() == true)
				{
					allReachedHome = true;
				}
				else
				{
					allReachedHome = false;
				}
			}
			if(allReachedHome)
			{
				System.out.println("Player " + appropriateList.get(0).getColor() + " has won.");
				return true;
			}
		}
		return false;
	}

	public static void initializeBoard() 
	{		
		String order = "rbyg";
		String playerArrangement = order.substring(0,numOfPlayers);
		for(char c : playerArrangement.toCharArray())
		{
			for(int i=0;i<4;i++)
			{
				Token t1 = new Token(-1,c,false,false);
				ArrayList<Token> appropriateList = getPlayerTokenList (c);
				appropriateList.add(t1);
			}			
		}		
	}

	private static ArrayList<Token> getPlayerTokenList(char c) 
	{		
		if(c == 'r')
			return redTokens;
		else if(c == 'b')
			return blueTokens;
		else if(c=='y')
			return yellowTokens;
		else
			return greenTokens;	
	}	
}

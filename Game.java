package com.game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
	private static ArrayList<Token> redTokens =  new ArrayList<Token>();
	private static ArrayList<Token> blueTokens =  new ArrayList<Token>();
	private static ArrayList<Token> yellowTokens =  new ArrayList<Token>();
	private static ArrayList<Token> greenTokens =  new ArrayList<Token>();
	
	
	private static int numOfPlayers = 0;
	public static int getNumOfPlayers()
	{
		System.out.println("Enter the number of players:");
		Scanner scanner = new Scanner(System.in);
		return scanner.nextInt();
	}
	
	
	public static void main(String [] args)
	{
		numOfPlayers = getNumOfPlayers();
		initializeBoard();
		char currentTurn = randomizedStart();
		while(!isGameOver())
		{
			
			int dieValue = rollTheDie();
			showCurrentStatus();
			boolean successfulMove=false;
			while(!successfulMove)
			{
				int tokenIndex = askForMove();
				if(isValidMove(currentTurn,tokenIndex, dieValue))
				{
					MakeMove(currentTurn, tokenIndex, dieValue);
					successfulMove = true;
				}				
			}			
			currentTurn = getNextTurn(currentTurn);
		}
	}


	private static void MakeMove(char currentTurn, int tokenIndex, int dieValue) {
		ArrayList<Token> tokenList =  getPlayerTokenList(currentTurn);
		Token currentToken = tokenList.get(tokenIndex);
		int newPosition = currentToken.getPosition()+dieValue;
		char occupiedColor=' ';
		if((occupiedColor = isOccupied(newPosition,currentTurn))!=' ')
		{
			removeToken(newPosition,occupiedColor);
		}
		currentToken.setPosition(newPosition);
		
	}


	private static void removeToken(int newPosition, char occupiedColor) {
		
		ArrayList<Token> appropriateList = getPlayerTokenList(occupiedColor);
		for(Token r: appropriateList)
		{
			if(r.getPosition() == newPosition)
				r.setPosition(-1);	
		}		
	}


	private static char isOccupied(int newPosition,char currentTurn) {
		
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


	private static boolean isValidMove(char currentTurn, int tokenIndex,
			int dieValue) {
		
		ArrayList<Token> tokenList =  getPlayerTokenList(currentTurn);
		Token currentToken = tokenList.get(tokenIndex);
		int newPosition = currentToken.getPosition()+dieValue;
		
		//TODO check whether resulting move is a valid move
		
		//same color check
		for(Token t: tokenList)
		{
			if(t.getPosition() == newPosition)
				return false;
		}
		
		
		return false;
	}


	private static int askForMove() {
		System.out.println("Which token number do you want to use:");
		Scanner ob = new Scanner(System.in);
		int token  = ob.nextInt();
		return token;
	}
	
	private static void displayPlayerTokens(ArrayList<Token> tokens)
	{
		for(int i=0;i<tokens.size();i++)
		{
			System.out.println("token: " + tokens.get(i).getColor() + (i+1) +" position: "+tokens.get(i).getPosition());
		}
	}

	private static void showCurrentStatus() {
		String order = "rbyg";
		String playerArrangement = order.substring(0,numOfPlayers);
		for(char c : playerArrangement.toCharArray())
		{
			displayPlayerTokens(getPlayerTokenList(c));
		}
		
		
	}


	private static int rollTheDie() {
		Random r = new Random();
		int maxValue = 6;
		return r.nextInt(maxValue-1)+1;
	}


	public static char randomizedStart() {
		// TODO Auto-generated method stub
		return 0;
	}


	public static char getNextTurn(char currentTurn) {
		// TODO Auto-generated method stub
		return 0;
	}


	public static boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	


	public static void initializeBoard() {
		
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


	private static ArrayList<Token> getPlayerTokenList(char c) {
		
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

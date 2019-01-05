package hangman.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hangman.*;

/**
 * This is a simple Hangman Game in Java with a given dictionary.
 * 
 * It's a one player game. The game is playable in the terminal. 
 * To win, the player has 5 rounds to find the word. Words are not 
 * case sensitive.
 * 
 * @author Vladimir Igumnov
 * @version 1.0
 */
public class HangmanConsole
{
	// file with names and scores of players
	private final static String playersFile = "player.txt";
	
	// default dictionary file (can be overridden by user input)  
	private static String dictionaryFile = "dictionary.txt";
	
	// "n" is to continue the game, "y" is for exit
	private final static String CONTINUE = "y";

	public static void main(String[] args)
	{
		String exitGame = CONTINUE;
		
		if (args.length > 0)
		{
			if (args.length == 2 && args[0].equals("-d"))
				dictionaryFile = args[1];
			else
			{
				System.out.println("Usage: java -jar Hangman.jar [-d dictionary_file]");
				return;
			}				
		}
		
		// To read data from terminal
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			// Load and randomly shuffle words in dictionary file
			HangmanDictionary dictionary = new HangmanDictionary(dictionaryFile);
			dictionary.shuffle(); 
			
			System.out.println("Hi! Welcome to my hangman Game!");
			
			HangmanConsolePlayer player = new HangmanConsolePlayer(playersFile);
			
			while (exitGame.equalsIgnoreCase(CONTINUE))
			{	    
				String currentWord = dictionary.getNextWord();	
				
				HangmanConsoleGame game = new HangmanConsoleGame(currentWord, player);			
				game.play();
				
				// store result into file
				player.saveResult(game); 
				
				System.out.print("Do you want to play another game ? (y/n)\n--> ");
				exitGame = reader.readLine();
				player.addToConversation("Do you want to play another game ? (y/n)\n--> " + exitGame);
			}
		} catch (IOException e)
		{
			System.out.println("IO error!");
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
			return;
		}
		
		System.out.println("Good Bye!");
	}
}

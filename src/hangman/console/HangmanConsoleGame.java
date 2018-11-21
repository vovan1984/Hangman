package hangman.console;

import java.io.BufferedReader;
import java.io.IOException;
import hangman.HangmanGame;
import hangman.HangmanPlayer;

/**
 * Console hangman game in Java.
 * 
 * @author Vladimir Igumnov
 * @version 1.0
 */
public class HangmanConsoleGame extends HangmanGame
{	
	private final HangmanPlayer player;  // player of the game	
	private final BufferedReader reader; // reader from console
	
	/**
	 * Constructor for Console version of a Hangman game.
	 * 
	 * @param word Word to be played.
	 * @param player Player who should guess the word. 
	 * @param reader Input stream to read from console.
	 */
	public HangmanConsoleGame(String word, 
			                  HangmanPlayer player,
			                  BufferedReader reader)
	{
		super(word);
		this.player = player;
	    this.reader = reader;
	}
	
	/**
	 * Adding console interface to the basic implementation of the game.
	 */
	@Override
	public void play()
	{
		System.out.println("Ok, let's start!");		
		System.out.println("Your word has " + getMaskedWord().length() + " letters");
		
		super.play();
		
		System.out.println(this);
	}
	
	@Override
	public String requestInput()
	{
		String input = null;
		
		// Get non-empty input
		while (input == null || input.equals(""))
		{
			System.out.println("The secret word is " + getMaskedWord());
			System.out.print("Your input: ");

			try
			{
				input = reader.readLine();
				if (input.equals(""))
					System.out.println("Please provide non-empty input!");
			} catch (IOException e)
			{
				System.out.println("IO error occured! Please re-enter your choice!");
				e.printStackTrace();
			}
		}
		
		return input;
	}
	
	@Override
	public void showResponse(String input, boolean match)
	{
		if (match)
			System.out.println("Well done, " + player.getFirstName() + "!");
		else
			System.out.println("Sorry, there is no \"" + input + "\" in your secret word :(");		
	}
}

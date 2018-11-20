package hangman.console;

import java.io.BufferedReader;

import hangman.HangmanGame;
import hangman.HangmanPlayer;

/**
 * This class represents player for Hangman game.
 * Game is played via console.
 * 
 * @author Vladimir Igumnov
 * @version 1.0
 *
 */
public class HangmanConsolePlayer extends HangmanPlayer
{
	private final BufferedReader reader;
	
	public HangmanConsolePlayer(String fileName, 
			String firstName, 
			String lastName,
			BufferedReader reader)
	{				
		super(fileName, firstName, lastName);
		this.reader = reader;
	}
	
	// play the game using provided word
	@Override
	public void playGame(String word)
	{
		HangmanGame game = new HangmanConsoleGame(word, this, reader);			
		game.play();
		
		// store result into file
		saveResult(game); 
	}
	
}

package hangman.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import hangman.HangmanGame;
import hangman.HangmanPlayer;

/**
 * This class represents player for Hangman game.<br>
 * Game is played via network.
 * 
 * @author Vladimir Igumnov
 * @version 1.0
 *
 */
public class HangmanNetworkPlayer extends HangmanPlayer
{
	private final BufferedReader reader;  // read to network connection
	private final BufferedWriter writer;  // write to network connection
	
	public HangmanNetworkPlayer(String fileName, 
			String firstName, 
			String lastName,
			BufferedReader reader,
			BufferedWriter writer)
	{				
		super(fileName, firstName, lastName);
		this.reader = reader;
		this.writer = writer;
	}
	
	/**
	 *  Play the game using provided word.
	 */
	@Override
	public void playGame(String word)
	{
		HangmanGame game = new HangmanNetworkGame(word, this, reader, writer);			
		game.play();
		
		// store result into file
		saveResult(game); 
	}
	
}

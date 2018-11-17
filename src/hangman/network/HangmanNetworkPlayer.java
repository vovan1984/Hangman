package hangman.network;

/**
 * This class represents player for Hangman game.
 * Game is played via network.
 * 
 * @author Vladimir Igumnov
 * @version 1.0
 *
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;

import hangman.HangmanGame;
import hangman.HangmanPlayer;

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
	
	// play the game using provided word
	@Override
	public void playGame(String word)
	{
		HangmanGame game = new HangmanNetworkGame(word, this, reader, writer);			
		game.play();
		
		// store result into file
		saveResult(game); 
	}
	
}

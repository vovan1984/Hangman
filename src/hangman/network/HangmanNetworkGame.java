/**
 * 
 */
package hangman.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Logger;

import hangman.HangmanGame;
import hangman.HangmanPlayer;

/** 
 * Network Hangman game in Java.
 * @author Vladimir Igumnov
 *
 */
public class HangmanNetworkGame extends HangmanGame
{
	private final HangmanPlayer player;  // player of the game	
	private final BufferedReader reader; // reader from network
	private final BufferedWriter writer; // write to network
	
	private final static Logger logger = Logger.getLogger(HangmanNetworkGame.class.getName());
	
	public HangmanNetworkGame(String word, 
			                  HangmanPlayer player,
			                  BufferedReader reader,
			                  BufferedWriter writer)
	{
		super(word);
		this.player = player;
	    this.reader = reader;
	    this.writer = writer;
	}

	/* (non-Javadoc)
	 * @see hangman.HangmanGame#showGameGreeting()
	 */
	@Override 
	public void showGameGreeting()
	{
		try
		{
			writer.write("Ok, let's start!" + "\r\n");
			writer.write("Your word has " + maskedWord.length() + " letters" + "\r\n");
			writer.flush();
		} catch (IOException e)
		{
			logger.severe("IO error while writing greeting to the client!");
			e.printStackTrace();
		}		
	}

	/* 
	 * Get substring from the network client
	 */
	@Override
	public String requestInput()
	{
		String input = null;
		
		// Get non-empty input
		while (input == null || input.equals(""))
		{
			try
			{
				writer.write("The secret word is " + maskedWord + "\r\n");
				writer.write("Your input: " + "\r\n");
				writer.flush();
				
				input = reader.readLine();
				if (input == null)
				{
					logger.warning("Client terminated connection abrubtly!");
					break; // leave the loop
				}

				if (input.equals(""))
					writer.write("Please provide non-empty input!" + "\r\n");
			} catch (IOException e)
			{
				logger.warning("IO error while communicating with client!");
				e.printStackTrace();
				break; // leave the loop
			}
		}
		
		return input;
	}

	/* (non-Javadoc)
	 * @see hangman.HangmanGame#showResponse(java.lang.String, boolean)
	 */
	@Override
	public void showResponse(String input, boolean match)
	{
		try
		{
			if (match)
				writer.write("Well done, " + player.getFirstName() + "!" + "\r\n");
			else
				writer.write("Sorry, there is no \"" + input + "\" in your secret word :(" + "\r\n");
			writer.flush();
		} catch (IOException e)
		{
			logger.info("IO error while sending response to client!");
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see hangman.HangmanGame#showGameResult()
	 */
	@Override
	public void showGameResult()
	{
		try
		{
			writer.write(this + "\r\n");
		} catch (IOException e)
		{
			logger.info("IO error while sending game result to client!");
			e.printStackTrace();
		}

	}

}

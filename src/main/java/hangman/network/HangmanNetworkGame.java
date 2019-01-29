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
	
	/**
	 * Play game via network.<br>
	 * Server shows masked word and asks for letters, 
	 * while client tries to guess remained characters. 
	 */
	public void play()
	{
		try
		{
			writer.write("Ok, let's start!" + "\r\n");
			writer.write("Your word has " + getMaskedWord().length() + " letters" + "\r\n");
			writer.flush();

			String input;
			
		    // play while there are letters to be guessed
	        while (canContinueGame())
	        {
	            input = requestInput();

	            // try to find matches of input substring in secret word
	            boolean match = checkPlayerGuess(input);

	            // Communicate match or miss to the user
	            showResponse(input, match); 
	        }   

			writer.write(this + "\r\n");
			writer.flush();
		} 
		catch (IOException e)
		{
			logger.info("IO error while communicating to client!");
			e.printStackTrace();
		}
	}

	/* 
	 * Get substring from the network client
	 */
	public String requestInput()
	{
		String input = null;
		
		// Get non-empty input
		while (input == null || input.equals(""))
		{
			try
			{
				writer.write("The secret word is " + getMaskedWord() + "\r\n");
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

	/**
	 *  Inform player of match/miss and show the secret word with letters guessed by now.
	 *  
     * @param input Input substring provided by player.
     * @param match Result of the substring search in the hidden word:
     *        <ul>
     *           <li><b>true</b>  - substring was found in the word.
     *           <li><b>false</b> - substring was not found.
     *        </ul>
	 */
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
}

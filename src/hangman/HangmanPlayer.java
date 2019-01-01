package hangman;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

/**
 * Abstract class representing player for a Hangman game.
 * Game is played differently depending on the interface,
 * therefore this class is defined as abstract.
 * 
 * @author Vladimir Igumnov
 * @version 1.0
 *
 */
abstract public class HangmanPlayer
{
	private String firstName,
	               lastName;

	private File playersFile; // file with all player names

	/**
	 * Constructor for the player of a Hangman game.
	 * @param fileName File with statistics of users who played the game.
	 * @param firstName First name of the player.
	 * @param lastName Last name of the player.
	 */
	public HangmanPlayer(String fileName, 
			             String firstName, 
			             String lastName)
	{				
		playersFile = new File(fileName);
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
    /**
     * Play Hangman games till user decides to exit.
     */	
	abstract public void play();
	
	/**
	 * Save result of the game into players file.
	 * @param game Instance of the Hangman game which was played.
	 */
	public void saveResult(HangmanGame game)
	{	
		try (
				// create file if it doesn't exist, or append to it if exists.
				PrintStream writer 
				= new PrintStream(new FileOutputStream(playersFile, playersFile.isFile()));
				)
		{	
			String postfix = game.getRounds() > 1 ? " rounds." : " round.";
			writer.println("[" + new Date() + "] " + 
					firstName + " " + lastName + ": " 
					+ game.getScore() + " points in " + game.getRounds() + postfix);
		} catch (IOException e)
		{
			System.out.println("Failed to store result of the game!");
			e.printStackTrace();
		} 
	}

	/**
	 *  Returns first name of the player.
	 * @return First name of the player.
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 *  Returns last name name of the player.
	 * @return Last name of the player.
	 */
	public String getLastName()
	{
		return lastName;
	}
}

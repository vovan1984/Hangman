package hangman;
/**
 * This class represents player for Hangman game.
 * Game is played differently depending on the interface,
 * therefore this class is defined as abstract.
 * 
 * @author Vladimir Igumnov
 * @version 1.0
 *
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

abstract public class HangmanPlayer
{
	private String firstName,
	               lastName;

	private File playersFile; // file with all player names

	public HangmanPlayer(String fileName, 
			             String firstName, 
			             String lastName)
	{				
		playersFile = new File(fileName);
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	// play the game using provided word
	abstract public void playGame(String word);
	
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

	// return first name
	public String getFirstName()
	{
		return firstName;
	}

	// return last name
	public String getLastName()
	{
		return lastName;
	}
}

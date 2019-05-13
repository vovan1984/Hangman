package hangman.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import hangman.*;
import hangman.utils.HangmanFileStats;

/**
 * This class starts Console Hangman Game with a given dictionary.
 * <br>
 * It's a one player game. The game is playable in the terminal. 
 * To win, the player has 10 rounds to find the word. Words are not 
 * case sensitive.
 * <br><br>
 * <b>Logic:</b>
 * <ul>
 *     <li>First, program loads and shuffles dictionary.</li>
 *     <li>Then it creates player.</li>
 *     <li>Finally, in a loop, user plays the game
 *        for the next word from dictionary.</li>
 * </ul>
 * @author Vladimir Igumnov
 * @version 1.0
 */
public class HangmanConsole extends HangmanController
{
	
	/**
	 * @param args Input parameters for running the game in Console:
	 *             <ul>
	 *                <li><b>-p &lt;file_path&gt; </b> - Optional. Full path to the players file</li>
	 *                <li><b>-d &lt;file_path&gt; </b> - Optional. Full path to the dictionary file</li>
	 *             </ul>
	 */
	public static void main(String[] args)
	{	    
		processArguments(args);
			
		// Read data from terminal.
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)))
		{
		    // Load and randomly shuffle words in dictionary file
	        HangmanDictionary dictionary = createHangmanDictionary();
	        
			// Create player
			HangmanPlayer player = createHangmanPlayer(reader, dictionary);
			
			// Play games until user decides to leave
			player.play();	
		}
		catch (IllegalStateException | IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
			return;
		} catch (IOException e)
        {
            System.out.println("Error closing input stream!");
            e.printStackTrace();
        } 
		
		System.out.println("Good Bye!");
	}

	/**
	 * Create user object for the Console game.
	 * 
	 * @param reader Input stream to read user info
	 * @return Object of HangmanPlayer interface (HangmanConsolePlayer)
	 */
    private static HangmanPlayer createHangmanPlayer(
            BufferedReader reader,
            HangmanDictionary dictionary
            )
    {
        String firstName, lastName;
        
        System.out.println("Hi! Welcome to my hangman Game!");
        
        try
        {
        	System.out.print("What's your last name?\n--> ");
        	lastName = reader.readLine();
        	System.out.print("And what's your first name?\n--> ");
        	firstName = reader.readLine();
        } catch (IOException e)
        {
        	System.out.println("I/O error while getting your name! Using default \"John Doe\"");
        	firstName = "John";
        	lastName = "Doe";
        }

        HangmanPlayer player = new HangmanConsolePlayer(
                                                        new HangmanFileStats(playersFile), 
                                                        dictionary,
        		                                        firstName, 
        		                                        lastName, 
        		                                        reader);
        
        System.out.println("Hi " + player.getFirstName() + ", nice to meet you!");
        
        return player;
    }

}

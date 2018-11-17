package hangman.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import hangman.*;

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
 *     <li>Finally, in a loop, it plays the game
 *        for the next word from dictionary.</li>
 * </ul>
 * @author Vladimir Igumnov
 * @version 1.0
 */
public class HangmanConsole
{
	// file with names and scores of players
	private static String playersFile = "player.txt";
	
	// default dictionary file (can be overridden by user input)  
	private static String dictionaryFile = "dictionary.txt";
	
	// "n" is to continue the game, "y" is for exit
	private final static String CONTINUE = "y";

	/**
	 * @param args Input parameters for running the game in Console:
	 *             <ul>
	 *                <li><b>-p &lt;file_path&gt; </b> - Optional. Full path to the players file</li>
	 *                <li><b>-d &lt;file_path&gt; </b> - Optional. Full path to the dictionary file</li>
	 *             </ul>
	 */
	public static void main(String[] args)
	{
		String exitGame = CONTINUE;
		
		// check if players and/or dictionary files are provided in input
		if (args.length > 0)
		{	
			for (int i = 0; i < args.length; i++)
			{
				if (args[i].equals("-d") && i < args.length-1)
				{
					i++;
					dictionaryFile = args[i];
				}
				else if (args[i].equals("-p") && i < args.length-1 )
				{
					i++;
					playersFile = args[i];					
				}
				else
				{
					System.out.println("Usage: java -jar HangmanConsole.jar [-d dictionary_file] [-p players_file]");
					return;				
				}
			}
		} // end of args.length > 0
		
		// To read data from terminal
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			// Load and randomly shuffle words in dictionary file
			HangmanDictionary dictionary = new HangmanDictionary(dictionaryFile);
			dictionary.shuffle(); 
			
			System.out.println("Hi! Welcome to my hangman Game!");
			
			// Create player
			String firstName, lastName;
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

			HangmanPlayer player = new HangmanConsolePlayer(playersFile, 
					                                        firstName, 
					                                        lastName, 
					                                        reader);
			System.out.println("Hi " + player.getFirstName() + ", nice to meet you!");			
			
			// play games for words from dictionary
			while (exitGame.equalsIgnoreCase(CONTINUE))
			{	
				// play game for the next word from shuffled list
				player.playGame(dictionary.getNextWord());
				
				System.out.print("Do you want to play another game ? (y/n)\n--> ");
				exitGame = reader.readLine();
			}
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
			return;
		} catch (IOException e)
		{
			System.out.println("IO error!");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Good Bye!");
	}
}

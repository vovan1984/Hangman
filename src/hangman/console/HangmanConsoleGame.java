package hangman.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import hangman.HangmanGame;


/**
 * Console hangman game in Java.
 * 
 * @author Vladimir Igumnov
 * @version 1.0
 */
public class HangmanConsoleGame extends HangmanGame
{	
	private final HangmanConsolePlayer player;  // player of the game	
	private final BufferedReader reader; // reader from console
	
	/**
	 * Constructor for Console version of a Hangman game.
	 * 
	 * @param word Word to be played.
	 * @param player Player who should guess the word. 
	 * @param reader Input stream to read from console.
	 */
	public HangmanConsoleGame(String word, 
			                  HangmanConsolePlayer player,
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
		player.showAndAddToConversation("Ok, let's start!");		
		player.showAndAddToConversation("Your word has " + getMaskedWord().length() + " letters");
		
		super.play();
		
		player.showAndAddToConversation(this.toString());
	}
	
	@Override
	public String requestInput()
	{
		String input = null;
		
		// Get non-empty input
		while (input == null || input.equals(""))
		{
		    player.showAndAddToConversation("The secret word is " + getMaskedWord());
			System.out.print("Your input: ");

			try
			{
				input = reader.readLine();
				player.addToConversation("Your input: " + input);
				
				if (input.equals(""))
				    player.showAndAddToConversation("Please provide non-empty input!");
			} catch (IOException e)
			{
			    player.showAndAddToConversation("IO error occured! Please re-enter your choice!");
				e.printStackTrace();
			}
		}
		
		return input;
	}
	
	/**
	 * Inform player of match/miss and show the secret word with letters guessed by now.<br>
	 * This method also shows text-graphical stage of the Hangman game.
	 * 
	 * @param input Input substring provided by player.
	 * @param match Result of the substring search in the hidden word:
	 *        <ul>
	 *           <li><b>true</b>  - substring was found in the word.
	 *           <li><b>false</b> - substring was not found.
	 *        </ul>
	 */
	@Override
	public void showResponse(String input, boolean match)
	{		
		// clear terminal screen
		clearScreen();
		
		// display communication up to now
		player.getConversation().forEach(s -> System.out.println(s));
	
	    String response;
	        
	    if (match)
	        response = "Well done, " + player.getFirstName() + "!";
	    else
	        response = "Sorry, there is no \"" + input + "\" in your secret word :(";

	    player.showAndAddToConversation(response + "\n");
	    
		// display current presentation of the gallows
		switch (getRounds())
		{
		    case 0: break;
		    case 1: printFile("/stage1.txt");
		            break;
		    case 2: printFile("/stage2.txt");
                    break;
		    case 3: printFile("/stage3.txt");
                    break;
		    case 4: printFile("/stage4.txt");
                    break;
		    case 5: printFile("/stage5.txt");
                    break;
		    case 6: printFile("/stage6.txt");
                    break;
		    case 7: printFile("/stage7.txt");
                    break;
		    case 8: printFile("/stage8.txt");
                    break;
		    case 9: printFile("/stage9.txt");
                    break;
		    case 10: printFile("/stage10.txt");
                    break;
		    default: break;
		}
	}
	
	/**
	 * Print content of an input file to a console.
	 * 
	 * @param fileName Input file name
	 */
	private void printFile(String fileName)
	{	
		// default charset of resource files
		Charset def = Charset.forName("ISO-8859-1");
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName), def)))
		{
			String line;
			while ( (line = br.readLine()) != null)
				System.out.println(line);
		} 
		catch (IOException e) 
		{
			System.out.println("IO error while processing " + fileName + "!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Clear the console window by ringing corresponding ANSI escape codes
	 * (clear screen, followed by home).
	 * On Windows we do the cleaning by running cls command from cmd terminal.
	 */
	private static void clearScreen()
	{
		for (int i = 0; i<100; i++)
			System.out.println();
		
	    try
	    {
	        final String os = System.getProperty("os.name");

	        if (os.contains("Windows"))
	        {
	        	new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        }
	        else
	        {
	        	// Should work in console supporting ANSI 
	        	System.out.print("\033[H\033[2J");  
	            System.out.flush();  
	        }
	    }
	    catch (IOException e) 
	    {
			System.out.println("IO error while clearing the screen!");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println();
	}
}

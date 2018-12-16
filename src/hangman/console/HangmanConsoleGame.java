package hangman.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import hangman.HangmanGame;

/**
 * Console Hangman game in Java.
 * 
 * @author Vladimir Igumnov
 * @version 1.0
 */
public class HangmanConsoleGame extends HangmanGame
{	
    // Default encoding of resource files.
    private final static Charset DEF_ENC = Charset.forName("ISO-8859-1");
    
    // Directory of resource files in the CLASSPATH.
    private final static String RES_DIR = "/resources/";
    
	private final HangmanConsolePlayer player;  // player of the game	
	private final BufferedReader reader;        // reader from console
	private boolean gameCompleted;              // indication if game completed
	
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
	    gameCompleted = false;
	}
	
	/**
	 * Implementation of the game for console interface.
	 */
	@Override
	public void play()
	{
	    if (gameCompleted)
	        throw new IllegalStateException("You are trying to continue already completed game!");
	        
		player.showAndAddToConversation("Ok, let's start!");		
		player.showAndAddToConversation("Your word has " + getMaskedWord().length() + " letters");
		
		String input;

		// play while there are letters to be guessed
		while (canContinueGame())
		{
		    input = requestInput();

		    // try to find matches of input substring in secret word
		    boolean match = checkPlayerGuess(input.toLowerCase());

		    // Communicate match or miss to the user
		    showResponse(input, match); 
		}       
		
		gameCompleted = true;
		player.showAndAddToConversation(this.toString());
	}
	
	/**
     * Get input characters from player.
     * @return Letter, substring or a full word suggestion from a player.
     */
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
	public void showResponse(String input, boolean match)
	{	
	    /* 
	     * Indication if Hangman stage picture should be stored 
	     * to a conversation history.
	     */
	    boolean addImgToConversation = !canContinueGame(); // if can't continue, then store image.
	    
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
		switch (getFailures())
		{
		    case 0:  break;
		    case 1:  printFile("stage1.txt", addImgToConversation);
		             break;
		    case 2:  printFile("stage2.txt", addImgToConversation);
                     break;
		    case 3:  printFile("stage3.txt", addImgToConversation);
                     break;
		    case 4:  printFile("stage4.txt", addImgToConversation);
                     break;
		    case 5:  printFile("stage5.txt", addImgToConversation);
                     break;
		    case 6:  printFile("stage6.txt", addImgToConversation);
                     break;
		    case 7:  printFile("stage7.txt", addImgToConversation);
                     break;
		    case 8:  printFile("stage8.txt", addImgToConversation);
                     break;
		    case 9:  printFile("stage9.txt", addImgToConversation);
                     break;
		    case 10: printFile("stage10.txt", addImgToConversation);
                     break;
		    default: break;
		}
	}
	
	/**
	 * Print content of an input file to a console.
	 * 
	 * @param fileName Input file name
	 */
	private void printFile(String fileName, boolean addImgToConversation)
	{		    
		try (BufferedReader br = new BufferedReader(
		        new InputStreamReader(getClass().getResourceAsStream(RES_DIR + fileName), DEF_ENC)))
		{
			String line;
			while ( (line = br.readLine()) != null)
			{
			    if(addImgToConversation)
			        player.showAndAddToConversation(line);
			    else
				    System.out.println(line);
			}
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
	    try
	    {
	        final String os = System.getProperty("os.name");

	        if (System.console() != null && os.contains("Windows"))
	        {
	            // Clear screen in Windows
	        	new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        }
	        else if (System.console() != null && System.getenv().get("TERM") != null)
	        {
	        	// Clear screen in console supporting ANSI 
	        	System.out.print("\033[H\033[2J");  
	            System.out.flush();  
	        }
	        else
	        {
	            // just print empty lines to clear the screen
	            for (int i = 0; i<100; i++)
	                System.out.println();
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
	
	/**
     *  Return result of the game in String form.
     */
    @Override
    public String toString()
    {
        String result;
        
        if (gameCompleted)
        {
            result = super.toString();
        }
        else
            result = "Game was not yet played!";
        
        return result;
    }
}

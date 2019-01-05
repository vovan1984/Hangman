package hangman.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Console hangman game in Java.
 * 
 * @author Vladimir Igumnov
 * @version 1.0
 */
public class HangmanConsoleGame
{
	private final static int MAX_ROUNDS = 10;
	
	private String word; // input word in a lower case
	private HangmanConsolePlayer player; // player of the game
	
	private int points;  // points for the game
	private int rounds;  // rounds for the game
	private boolean won; // if player won
	
	BufferedReader reader; // reader from console
	private int[] scores = {100, 50, 25, 10, 5, 0, 0, 0, 0, 0, 0};
	
	public HangmanConsoleGame(String word, HangmanConsolePlayer player)
	{
		this.word = word.toLowerCase(); 
		this.player = player;
		
		points = 0;
		rounds = 0;
		won = false;
		
		// To read data from terminal
	    reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	// score earned in the game
	public int getScore()
	{
		return points;
	}
	
	// number of rounds played in the game
	public int getRounds()
	{
		return rounds;
	}
	
	public void play()
	{
		String input;
		rounds = 0;
		
		player.showAndAddToConversation("Ok, let's start!");
		
		// mask letters with '*'
		char[] tmp = word.toCharArray();
		Arrays.fill(tmp, '*');
		StringBuffer hiddenWord = new StringBuffer(new String(tmp));
		
		player.showAndAddToConversation("Your word has " + hiddenWord.length() + " letters");
		
		// play while there are letters to be guessed
		while (rounds < MAX_ROUNDS && hiddenWord.indexOf("*") != -1)
		{
		    player.showAndAddToConversation("The secret word is " + hiddenWord);
			System.out.print("Your input: ");

			try
			{
				input = reader.readLine();
				String inputLC = input.toLowerCase(); // lower case
				player.addToConversation("Your input: " + input);
				
				// if substring exists in the secret word then replace asterisks with letters 
				boolean match = false;
				int startIdx;
				if( (startIdx = word.indexOf(inputLC)) != -1)
				{
					do
					{
						// replace asterisks with letters in hidden word
						hiddenWord.replace(startIdx, startIdx + inputLC.length(), inputLC);	
						startIdx += inputLC.length();
					}
					// repeat until all matches replaced
					while (startIdx < word.length() && 
							(startIdx = word.indexOf(inputLC, startIdx)) != -1); 
					
					match = true;
				}
				else
				{
					rounds++;			
				}
				
				showResponse(input, match);
					
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		points = scores[rounds];
		if (rounds < MAX_ROUNDS)
		{
			won = true;
			rounds++; // rounds start with zero, so increment the # for display purposes
		}
		
		player.showAndAddToConversation("The word was \"" + new String(word) + "\". " + this);
	}
	
	@Override
	public String toString()
	{
		String postfix = getRounds() > 1 ? " rounds." : " round.";
		String prefix = won ? "You won! " : "";
		return prefix + points + " points earned after " + getRounds() + postfix;
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
}

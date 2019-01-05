package hangman.console;
import java.io.BufferedReader;
/**
 * Class for representing a Hangman player
 * and saving the score to players file.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class HangmanConsolePlayer
{
	private String firstName,
	               lastName;
	private File playersFile; // file with all player names
	
	private List<String> conversation; // conversation with a player
	
	HangmanConsolePlayer(String fileName)
	{				
		playersFile = new File(fileName);
		conversation = new LinkedList<String>();
		
		// To read data from terminal
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				
		try
		{
			System.out.print("What's your last name?\n--> ");
			lastName = reader.readLine();
			addToConversation("What's your last name?\n--> " + lastName);
			
			System.out.print("And what's your first name?\n--> ");
			firstName = reader.readLine();
			addToConversation("And what's your first name?\n--> " + firstName);
		} catch (IOException e)
		{
			System.out.println("I/O error while getting your name! Using default \"John Doe\"");
			firstName = "John";
			lastName = "Doe";
		}

		showAndAddToConversation("Hi " + firstName + ", nice to meet you!");
	}
	
	public List<String> getConversation() {
        return conversation;
    }

	/**
	 * Store message to the conversation history.
	 * @param msg Message to store.
	 */
    public void addToConversation(String msg) 
    {
        conversation.add(msg);
    }
    
    /**
     * Display message and store it to the conversation history.
     * @param msg Message to store.
     */
    public void showAndAddToConversation(String msg) 
    {
        System.out.println(msg);
        conversation.add(msg);
    }

    public void saveResult(HangmanConsoleGame game)
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

}

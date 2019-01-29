package hangman.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import hangman.HangmanDictionary;
import hangman.HangmanPlayer;

/**
 * This class represents player for Hangman game.
 * Game is played via console.
 * 
 * @author Vladimir Igumnov
 * @version 1.1
 *
 */
public class HangmanConsolePlayer extends HangmanPlayer
{
    // "n" is to continue the game, "y" is for exit
    private final static String CONTINUE = "y"; 
    
	private final BufferedReader reader;
		   
	private HangmanDictionary dictionary;
    private List<String> conversation; // conversation with a player
	
	public HangmanConsolePlayer(String fileName, 
			String firstName, 
			String lastName,
			HangmanDictionary dictionary,
			BufferedReader reader)
	{				
		super(fileName, firstName, lastName);
		this.reader = reader;
		this.dictionary = dictionary;
		
	    conversation = new LinkedList<String>();
	}
	
	/**
	 * Play the game using provided word.
	 * @param word Secret word.
	 */
	private void playGame(String word)
	{
		var game = new HangmanConsoleGame(word, this, reader);			
		game.play();
		
		// store result into file
		saveResult(game); 
	}
	
	/**
	 * Play Hangman games for words in Dictionary till user
	 * decides to leave.
	 */
	public void play()
	{
	    String exitGame = CONTINUE;
	    
	    showAndAddToConversation("Hi " + getFirstName() + ", nice to meet you!");    
	       
        // play games for words from dictionary
	    try
	    {
	        while (exitGame.equalsIgnoreCase(CONTINUE))
	        {   
	            // play game for the next word from shuffled list
	            playGame(dictionary.getNextWord());

	            System.out.print("Do you want to play another game ? (y/n)\n--> ");
	            exitGame = reader.readLine();
	            addToConversation("Do you want to play another game ? (y/n)\n--> " + exitGame);
	        }
	    }
	    catch (IOException e)
        {
            System.out.println("IO error!");
            e.printStackTrace();
            return;
        }
	}
	
	/**
     * Return console conversation with a player.   
     * @return Conversation.
     */
    public List<String> getConversation() 
    {
        return conversation;
    }

    /**
     * Store message to a conversation.
     * @param msg Message to add.
     */
    public void addToConversation(String msg) 
    {
        conversation.add(msg);
    }
    
    /**
     * Display a message and store it to a conversation.
     * @param msg Message to add and display.
     */
    public void showAndAddToConversation(String msg) 
    {
        System.out.println(msg);
        conversation.add(msg);
    }
	
}

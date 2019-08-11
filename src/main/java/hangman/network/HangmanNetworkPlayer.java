package hangman.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import hangman.HangmanDictionary;
import hangman.HangmanGame;
import hangman.HangmanPlayer;
import hangman.HangmanStats;
import hangman.shared.Hangman10RoundsGame;

/**
 * This class represents player for a Hangman game.<br>
 * Game is played via network.
 * 
 * @author Vladimir Igumnov
 * @version 1.0
 *
 */
public class HangmanNetworkPlayer extends HangmanPlayer
{
    // "n" is to continue the game, "y" is for exit
    private final static String CONTINUE = "y";
    
    // location to store and retrieve games state
    private final HangmanStats storage; 
    
    // dictionary with secret words
    private final HangmanDictionary dictionary;
    
	private final BufferedReader reader;  // read to network connection
	private final BufferedWriter writer;  // write to network connection
	
	private final String logPrefix;
	private final static Logger logger = Logger.getLogger(HangmanNetworkPlayer.class.getName());
	
	public HangmanNetworkPlayer(HangmanStats storage, 
			String firstName, 
			String lastName,
			HangmanDictionary dictionary,
			BufferedReader reader,
			BufferedWriter writer,
			String logPrefix)
	{				
	    super(firstName, lastName);
		this.storage = storage;
		this.dictionary = dictionary;
		this.reader = reader;
		this.writer = writer;
		this.logPrefix = logPrefix;
	}
	
	/**
	 * Play game via network.<br>
     * Server shows masked word and asks for letters, 
     * while client tries to guess remained characters.
	 * @param word Secret word.
	 */
	private void playGame(HangmanGame game)
	{
	    try
	    {
	        writer.write("Ok, let's start!" + "\r\n");
	        writer.write("Your word has " + game.getMaskedWord().length() + " letters" + "\r\n");
	        writer.flush();

	        String input;

	        // play while there are letters to be guessed
	        while (!game.isGameCompleted())
	        {
	            input = requestInput(game);

	            // try to find matches of input substring in secret word
	            boolean match = game.checkPlayerGuess(input);

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

	/**
     * Play games till user decides to exit.
     */
	@Override
	public void play()
	{
	    String exitGame = CONTINUE;
	    
	    try
	    {
	        // play games for words from a dictionary
	        while (exitGame.equalsIgnoreCase(CONTINUE))
	        {   
	            // play game for the next word from shuffled list
	            var game = new Hangman10RoundsGame(dictionary.getNextWord());
	            playGame(game);
	            
	            // store result into file
	            storage.saveResult(this, game); 

	            writer.write("Do you want to play another game ? (y/n)" + "\r\n");
	            writer.write("--> " + "\r\n");
	            writer.flush();
	            exitGame = reader.readLine();
	        }
	    }
	    catch (IOException e)
        {
            // Just print error message
            logger.log(Level.WARNING, logPrefix + "IO error!", e);
            e.printStackTrace();
        }
	    
	}
   
    /* 
     * Get substring from the network client
     * @return Letter, substring or a full word suggestion from a player.
     * @param game Game for which an answer is requested
     */
    private String requestInput(HangmanGame game)
    {
        String input = null;
        
        // Get non-empty input
        while (input == null || input.equals(""))
        {
            try
            {
                writer.write("The secret word is " + game.getMaskedWord() + "\r\n");
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
    private void showResponse(String input, boolean match)
    {
        try
        {
            if (match)
                writer.write("Well done, " + getFirstName() + "!" + "\r\n");
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

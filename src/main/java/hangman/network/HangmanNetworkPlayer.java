package hangman.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import hangman.HangmanDictionary;
import hangman.HangmanPlayer;
import hangman.HangmanStats;

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
    
    private HangmanDictionary dictionary;
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
		super(storage, firstName, lastName);
		this.dictionary = dictionary;
		this.reader = reader;
		this.writer = writer;
		this.logPrefix = logPrefix;
	}
	
	/**
	 * Play the game using provided word.
	 * @param word Secret word.
	 */
	private void playGame(String word)
	{
		var game = new HangmanNetworkGame(word, this, reader, writer);			
		game.play();
		
		// store result into file
		saveResult(game); 
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
	            playGame(dictionary.getNextWord());

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
	
}

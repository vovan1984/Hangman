package hangman;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.List;

/**
 * This class contains methods to work with
 * Dictionary for a Hangman game.
 * <ul>
 * <li>Constructor loads words from the input file.</li>
 * <li>Method shuffle() shuffles words in random order.</li>
 * </ul>
 * @author Vladimir Igumnov
 * @version 1.0
 * 
 */
public class HangmanDictionary
{
	// List of words from dictionary
	private String[] words;
	private int currentWord; // index of next word to be dealt 
	
	// default charset
	private final static Charset def = Charset.forName("ISO-8859-1");
	//  random number generator
	private final static SecureRandom randomNumber = new SecureRandom();
	
	/**
	 * Constructor for a default charset. 
	 * @param dictionaryPath Path to a dictionary file.
	 */
	public HangmanDictionary(String dictionaryPath)
	{
		this(dictionaryPath, def);
	}
	
	/**
	 *  Constructor for an input charset.
	 * @param dictionaryPath Path to a dictionary file.
	 * @param charset Encoding of a dictionary file.
	 */
	public HangmanDictionary(String dictionaryPath, Charset charset)
	{
		File dictionary = new File(dictionaryPath);
		
		// check if dictionary exists and is a regular file.
		if (!dictionary.isFile())
			throw new IllegalArgumentException("Dictionary file was not found! " +
					"Please provide it with -d option or place to local folder.");
		
		currentWord = 0; // first word dealt will be words[0];
		
		try
		{
			List<String> lines = Files.readAllLines(dictionary.toPath(), charset);			
			words = lines.toArray(new String[lines.size()]);
			
			if (words.length <= 0)
				throw new IllegalArgumentException("Dictionary is empty!" +
			                        "Please fix this before starting the game");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	/**
	 *  This method shuffles words in a random order.
	 */
	public void shuffle()
	{
		// next call to getNextWord should start with words[0] again
		currentWord = 0;
		
		for (int first = 0; first < words.length; first++)
		{
			// select a random number between 0 and number of words
			int second = randomNumber.nextInt(words.length);
			
			String temp = words[first];
			words[first] = words[second];
			words[second] = temp;
		}
	} // end of shuffle

	/**
	 * This method identifies the next word from the dictionary to
	 * be played in a Hangman game.
	 * @return Next word from a randomly shuffled dictionary.
	 */
	public String getNextWord()
	{
		/* determine if any word is left to be dealt, 
		 * and re-shuffle if not */
		
		if (currentWord >= words.length)
		{
			System.out.println("All words were played! Restarting from the beginning.");
			shuffle(); 
		}
		
	    return words[currentWord++];
	}
}

package hangman;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.LinkedList;
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
	private final static Charset DEF_ENC = Charset.forName("ISO-8859-1");
	
	// default dictionary
	private final static String DEF_DICTIONARY = "/resources/dictionary.txt";
    
	//  random number generator
	private final static SecureRandom randomNumber = new SecureRandom();
	
	/**
	 * Constructor for a default charset. 
	 * @param dictionaryPath Path to a dictionary file.
	 */
	public HangmanDictionary(String dictionaryPath)
	{
		this(dictionaryPath, DEF_ENC);
	}
	
	/**
	 *  Constructor for an input charset.
	 * @param dictionaryPath Path to a dictionary file.
	 * @param charset Encoding of a dictionary file.
	 */
	public HangmanDictionary(String dictionaryPath, Charset charset)
	{
	    BufferedReader dictionaryStream = null;
		File dictionary = new File(dictionaryPath);
		
		try
		{
		    // check if dictionary exists and is a regular file.
		    if (!dictionary.isFile())
		    {
		        // dictionary file doesn't exist, so look for default one on the CLASSPATH
		        dictionaryStream = new BufferedReader(
		                new InputStreamReader(getClass().getResourceAsStream(DEF_DICTIONARY), charset));
		    }
		    else
		    {
		        dictionaryStream = new BufferedReader(
		                new InputStreamReader(new FileInputStream(dictionary), charset));
		    }
		    
		    currentWord = 0; // first word dealt will be words[0];
		    
	        // read file content into list of strings
		    String line;
            List<String> lines = new LinkedList<>(); 
            while ( (line = dictionaryStream.readLine()) != null)
                lines.add(line);
            
            words = lines.toArray(new String[lines.size()]);
            
            if (words.length <= 0)
                throw new IllegalArgumentException("Dictionary is empty!" +
                                    "Please fix this before starting the game");
		}
		catch (NullPointerException | FileNotFoundException e)
		{
		    throw new IllegalArgumentException("Dictionary file was not found! " +
		            "Please provide it with -d option or place to a local folder.");
		} catch (IOException e)
        {
            System.out.println("Error while reading content of a dictionary file!");
            e.printStackTrace();
        }
        finally
        {
            if (dictionaryStream != null)
                try
                {
                    dictionaryStream.close();
                } 
                catch (IOException e)
                {
                    System.out.println("Unable to close dictionary file!");
                    e.printStackTrace();
                }
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

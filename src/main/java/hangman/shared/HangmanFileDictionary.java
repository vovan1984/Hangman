package hangman.shared;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;

import hangman.HangmanDictionary;

/**
 * This class contains methods to work with a Dictionary 
 * stored in a file.
 * <ul>
 *     <li>Constructor loads words from an input file.</li>
 *     <li>Method shuffle() shuffles words in a random order.</li>
 * </ul>
 * @author Vladimir Igumnov
 * @since 1.0
 */
public class HangmanFileDictionary implements HangmanDictionary
{
    // List of words from dictionary
    private String[] words;
    private int nextWord; // index of next word to be dealt 
    
    // default charset
    private final static Charset DEF_ENC = Charset.forName("ISO-8859-1");
    
    // default dictionary
    private final static String DEF_DICTIONARY = "/dictionary.txt";
    
    //  random number generator
    private final static SecureRandom randomNumber = new SecureRandom();

    
    /**
     * Default constructor for loading data from a default dictionary.
     * The file containing that dictionary should be located on the CLASSPATH.
     */
    public HangmanFileDictionary()
    {
        try (var dictionaryStream = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(DEF_DICTIONARY), DEF_ENC)))
        {
            loadDictionary(dictionaryStream);
        }
        catch (NullPointerException e)
        {
            throw new IllegalArgumentException("Default dictionary file was not found! " +
                    "Please provide your dictionary with -d option.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IllegalStateException("Error while reading content of a dictionary file!");
        }
    }
    
    /**
     * Constructor for a default charset. 
     * @param dictionaryPath Path to a dictionary file.
     */
    public HangmanFileDictionary(String dictionaryPath)
    {
        this(dictionaryPath, DEF_ENC);
    }
    
    /**
     * Constructor for an input charset.
     * @param dictionaryPath Path to a dictionary file. 
     * @param charset Encoding of a dictionary file.
     */
    public HangmanFileDictionary(String dictionaryPath, Charset charset)
    {
        try (var dictionaryStream = new BufferedReader(
                        new InputStreamReader(new FileInputStream(dictionaryPath), charset));)
        {
            loadDictionary(dictionaryStream);
        }
        catch (FileNotFoundException e)
        {
            throw new IllegalArgumentException("Dictionary file " + dictionaryPath + " was not found! " +
                    "Please provide it with -d option.");
        } 
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IllegalStateException("Error while reading content of a dictionary file!");
        }   
    }

    /**
     * This method loads words from an input stream, 
     * @param dictionaryStream Input stream of dictionary words.
     * @throws IOException
     */
    private void loadDictionary(BufferedReader dictionaryStream) throws IOException
    {
        nextWord = 0; // first word dealt will be words[0];
        
        // read file content into a list of strings
        String line;
        List<String> lines = new LinkedList<>(); 
        while ((line = dictionaryStream.readLine()) != null)
            lines.add(line);
        
        words = lines.toArray(new String[lines.size()]);
        
        if (words.length <= 0)
            throw new IllegalArgumentException("Dictionary is empty!" +
                                "Please fix this before starting the game");
    }

    /**
     *  Fisher–Yates shuffle to rearrange words in a random order.
     */
    private void shuffle()
    {
        // next word to retrieve should be words[0]
        nextWord = 0;
        
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
    @Override
    public String getNextWord()
    {
        /* determine if any word is left to be dealt, 
         * and re-shuffle if not */
        if (nextWord == 0 || nextWord >= words.length)
            shuffle(); 
        
        return words[nextWord++];
    }
    
    /**
     * Load and randomly shuffle words in dictionary file
     * 
     * @return shuffled dictionary
     */
    public static HangmanDictionary createDictionary(String dictionaryFile)
    {
        HangmanDictionary dictionary;
        
        // if dictionary file is not provided, then load a default one.
        if (dictionaryFile != null)
            dictionary = new HangmanFileDictionary(dictionaryFile);
        else
            dictionary = new HangmanFileDictionary();
        return dictionary;
    }
}

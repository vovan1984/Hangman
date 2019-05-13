package hangman;

import hangman.HangmanDictionary;
import hangman.utils.HangmanFileDictionary;

/**
 * Common methods for Hangman game controllers
 * 
 * @author Vladimir Igumnov
 * @since 1.0
 */
public class HangmanController
{
    // file with names and scores of players
    protected static String playersFile = "src/main/webapp/WEB-INF/player.txt";
    
    // name of a dictionary file. If it remains null, then default dictionary will be used.
    protected static String dictionaryFile = null;
    
    /**
     * Process input parameters of the program.
     * @param args Arguments to parse.
     */
    protected static void processArguments(String[] args)
    {
        // check if players and/or dictionary files are provided in input
        if (args.length > 0)
        {   
            for (int i = 0; i < args.length; i++)
            {
                if (args[i].equals("-d") && i < args.length-1)
                {
                    i++;
                    dictionaryFile = args[i];
                }
                else if (args[i].equals("-p") && i < args.length-1 )
                {
                    i++;
                    playersFile = args[i];                  
                }
                else
                {
                    System.out.println("Usage: java -jar HangmanConsole.jar [-d dictionary_file] [-p players_file]");
                    System.exit(0);             
                }
            }
        } // end of args.length > 0
    }
    
    /**
     * Load and randomly shuffle words in dictionary file
     * 
     * @return shuffled dictionary
     */
    protected static HangmanDictionary createHangmanDictionary()
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

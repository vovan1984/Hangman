package hangman.gui;

import javax.swing.SwingUtilities;

import hangman.HangmanDictionary;
import hangman.HangmanStats;

/**
 * This class starts GUI Hangman Game with a given dictionary.
 * <br>
 * It's a one player game. The game is played using GUI interface. 
 * To win, the player has 10 rounds to find the word. Words are not 
 * case sensitive.
 * <br><br>
 * <b>Logic:</b>
 * <ul>
 *     <li>First, program loads and shuffles dictionary.</li>
 *     <li>Then it loads players achievements from a statistics file.</li>
 *     <li>Finally, it opens a window to get player's details and start a game.</li>
 * </ul>
 * @author Vladimir Igumnov
 * @version 1.0
 */

public class HangmanGUI 
{ 
    // file with names and scores of players
    private static String playersFile = "player.txt";
    
    // default dictionary file (can be overridden by user input)  
    private static String dictionaryFile = "dictionary.txt";
    
    /**
     * @param args Input parameters for running the game in Console:
     *             <ul>
     *                <li><b>-p &lt;file_path&gt; </b> - Optional. Full path to the players file</li>
     *                <li><b>-d &lt;file_path&gt; </b> - Optional. Full path to the dictionary file</li>
     *             </ul>
     */
    public static void main(String[] args) 
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
                    return;             
                }
            }
        } // end of args.length > 0
        
        try
        {
            // Load and randomly shuffle words in dictionary file.
            var dictionary = new HangmanDictionary(dictionaryFile);
            dictionary.shuffle();
            
            // Load info about players who played the game previously.
            var playersInfo = new HangmanStats(playersFile);
            
            // Display welcome window
            SwingUtilities.invokeLater( () -> new HangmanWelcomeWindow("Welcome to the Hangman Game!",
                                                                        dictionary,
                                                                        playersInfo));
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
            return;
        } 
    }

}

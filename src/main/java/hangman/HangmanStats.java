package hangman;

/**
 * Interface for saving and loading players' 
 * statistics from a file.
 * 
 * @author Vladimir Igumnov
 *
 * @since 1.0
 */
public interface HangmanStats
{
    /**
     * Get records from statistics file
     * @return Array of data sets ordered as needed. 
     */
    public String[][] getResults();
    
    /**
     * Save result of the game into players file.
     * 
     * @param player User playing the game.
     * @param game Instance of the Hangman game which was played.
     */
    public void saveResult(HangmanPlayer player, HangmanGame game);
}

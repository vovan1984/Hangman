package hangman;

/**
 * This interface represents a game with methods allowing to 
 * retrieve its current stage, secret word, etc.
 * 
 * @author Vladimir Igumnov
 * @since 1.0
 */
public interface HangmanGame
{
    /**
     * Identify if game completed.
     * @return true if game is completed.
     */
    public boolean isGameCompleted();
    
    /**
     * Show current status of a secret word (with asterisks in
     * place of not yet guessed letters).
     * @return Secret word with unknown letters masked with '*'.
     */
    public String getMaskedWord();
    
    /**
     * This method returns score earned in the game.
     * @return Game score.
     */
    public int getScore();

    /**
     * Number of rounds played in the game.
     * @return Number of played rounds.
     */
    public int getRounds();
    
    /**
     * This method returns current number of incorrect guesses in
     * an ongoing Hangman game.
     * @return Number of incorrect guesses.
     */
    public int getFailures();

    
    /**
     * This method searches for matching substrings in a hidden word.
     * 
     * @param input Input substring to search in the hidden word.
     * @return Result of the search:
     *        <ul>
     *           <li><b>true</b>  - substring was found in the word.
     *           <li><b>false</b> - substring was not found.
     *        </ul>
     */
    public boolean checkPlayerGuess(String input);
    
    /**
     * Check if player won the game.
     * @return Result of the game:
     * <ul>
     *     <li><b>true</b>  - player won the game</li>
     *     <li><b>false</b> - player lost the game</li>
     * </ul>
     */
    public boolean hasWon();

}

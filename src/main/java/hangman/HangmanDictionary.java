package hangman;

/**
 * This interface represents a Dictionary to be used in
 * a Hangman game.
 * 
 * @author Vladimir Igumnov
 * @since 1.0
 */
public interface HangmanDictionary
{
    /**
     * This method identifies the next word from the dictionary to
     * be played in a Hangman game.
     * 
     * @return Next word from a randomly shuffled dictionary.
     * @author Maestro
     */
    public String getNextWord();
}

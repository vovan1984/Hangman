package hangman;

/**
 * This interface represents a user playing provided Hangman games.
 * 
 * @author Vladimir Igumnov
 * @since 1.0
 */
public interface HangmanPlayer
{
    /**
     * Play games until and user decides to leave.
     */
    public void play();
    
    /**
     *  Returns first name of the player.
     * @return First name of the player.
     */
    public String getFirstName();

    /**
     *  Returns last name name of the player.
     * @return Last name of the player.
     */
    public String getLastName();
}

package hangman;

/**
 * This class represents a user playing Hangman games.
 * 
 * @author Vladimir Igumnov
 * @since 1.0
 */
public abstract class HangmanPlayer
{
    
    private String firstName, lastName;
    
    public HangmanPlayer(String firstName,
                         String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * Play games until user decides to exit.
     */
    public abstract void play();
    
    /**
     *  Returns first name of the player.
     * @return First name of the player.
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     *  Returns last name name of the player.
     * @return Last name of the player.
     */
    public String getLastName()
    {
        return lastName;
    }
}

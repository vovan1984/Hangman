package hangman;

/**
 * Abstract class representing player for a Hangman game.
 * Game is played differently depending on the interface,
 * therefore this class is defined as abstract.
 * 
 * @author Vladimir Igumnov
 * @version 1.0
 *
 */
abstract public class HangmanPlayer
{
	private String firstName,
	               lastName;

	private HangmanStats playersFile; // file with all player names

	/**
	 * Constructor for the player of a Hangman game.
	 * @param fileName File with statistics of users who played the game.
	 * @param firstName First name of the player.
	 * @param lastName Last name of the player.
	 */
	public HangmanPlayer(HangmanStats playersFile, 
			             String firstName, 
			             String lastName)
	{				
		this.playersFile = playersFile;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
    /**
     * Play Hangman games till user decides to exit.
     */	
	abstract public void play();
	
	/**
	 * Save result of the game into players file.
	 * @param game Instance of the Hangman game which was played.
	 */
	public void saveResult(HangmanGame game)
	{	
		playersFile.saveResult(this, game);
	}

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

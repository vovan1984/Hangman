package hangman;

import java.util.Arrays;

/**
 * Abstract class defining the logic of Hangman game.
 * This logic doesn't depend on the method of user input (e.g. it
 * doesn't matter if input is provided from terminal, Web or GUI).
 * 
 * Methods for communication with player are declared as abstract,
 * and should be overriden by every concrete implementation.
 * 
 * @author Vladimir Igumnov
 *
 */
public abstract class HangmanGame
{
	// max number of rounds to be played
	private final static int MAX_ROUNDS = 10;
	
	private int points;         // points for the game
	private int rounds;         // rounds for the game
	private boolean won;        // if player won
	private boolean gamePlayed; // if game was already played
	
	// scores after each round
	private int[] scores = {100, 50, 25, 10, 5, 0, 0, 0, 0, 0, 0};
	
	/**
	 * Secret word in a lower case
	 */
	protected String word;          
	
	/**
	 * Secret word masked with '*'
	 */
	protected StringBuffer maskedWord; 
	
	public HangmanGame(String word)
	{
		this.word = word.toLowerCase(); 
		
		points = 0;
		rounds = 0;
		won = false;
		gamePlayed = false;
		
		// mask letters with '*' (Unicode basic multilingual plane is assumed)
		char[] tmp = word.toCharArray();
		Arrays.fill(tmp, '*');
		maskedWord = new StringBuffer(new String(tmp));
	}
	
	/**
	 *  Show greeting at the beginning of the game.
	 */
	abstract public void showGameGreeting();
	
	/**
	 *  Get input characters from player.
	 * @return Letter, substring or a full word suggestion from a player.
	 */
	abstract public String requestInput();
	
	/**
	 * Inform player of match/miss and show the secret word with letters guessed by now.<br>
	 * This abstract class show be defined in children classes.
	 * 
	 * @param input Input substring provided by player.
	 * @param match Result of the substring search in the hidden word:
	 *        <ul>
	 *           <li><b>true</b>  - substring was found in the word.
	 *           <li><b>false</b> - substring was not found.
	 *        </ul>
	 */
	abstract public void showResponse(String input, boolean match);
	
	/**
	 *  Show result at the end of the game.
	 */
	abstract public void showGameResult();
	
	/**
	 * Implementation of play() depends on type
	 * of an interaction with user. This interaction is
	 * defined by abstract methods.
	 */
	public void play()
	{
		String input;
		
		// game was already played
		if (gamePlayed) return;
		
		showGameGreeting();
		
		// play while there are letters to be guessed
		while (rounds < MAX_ROUNDS && maskedWord.indexOf("*") != -1)
		{
			input = requestInput();

			// try to find and reveal matches of input substring in secret word
			boolean match = findMatches(input.toLowerCase());
			
			// Communicate match or miss to the user
			showResponse(input, match);	
			
			// Start next round in case of miss
			if (!match) rounds++;
		}	
		
		// Calculate final score
		points = scores[rounds];
		if (rounds < MAX_ROUNDS)
		{
			won = true;
			rounds++; // rounds start with zero, so increment the # for display purposes
		}
		gamePlayed = true;
		
		// Show result of the game
		showGameResult();
	} // end of play()
	
	/**
	 * This method returns score earned in the game.
	 * @return Game score.
	 */
	public int getScore()
	{
		return points;
	}
	
	/**
	 * Number of rounds played in the game.
	 * @return Number of played rounds.
	 */
	public int getRounds()
	{
		return rounds;
	}
	
	/**
	 * Check if player won the game.
	 * @return Result of the game:
	 * <ul>
	 *     <li><b>true</b>  - player won the game</li>
	 *     <li><b>false</b> - player lost the game</li>
	 * </ul>
	 */
	public boolean hasWon()
	{
		return won;
	}
	
	// reveal matching substrings in maskedWord  
	private boolean findMatches(String input)
	{
		boolean matchFound = false;
		
		// if substring exists in the secret word then replace asterisks with letters 
		int startIdx;
		if((startIdx = word.indexOf(input)) != -1)
		{
			do
			{
				// replace asterisks with letters in hidden word
				maskedWord.replace(startIdx, startIdx + input.length(), input);	
				startIdx += input.length();
			}
			// repeat until all matches replaced
			while (startIdx < word.length() && 
					(startIdx = word.indexOf(input, startIdx)) != -1); 
			
			matchFound = true;
		}

		return matchFound;
	}
	
	/**
	 *  Return result of the game in String form.
	 */
	@Override
	public String toString()
	{
		String result;
		
		if (gamePlayed)
		{
		    String postfix = getRounds() > 1 ? " rounds." : " round.";
		    String prefix = hasWon() ? "You won! " : "";
		    result = "The word was \"" + word + "\". "
				+ prefix + getScore() + " points earned after " + getRounds() + postfix;
		}
		else
			result = "Game was not yet played!";
		
		return result;
	}
}

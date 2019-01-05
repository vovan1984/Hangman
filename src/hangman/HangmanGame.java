package hangman;

/**
 * This class defines an instance of the Hangman game.<br>
 * Its content doesn't depend on the method of user input (e.g. it
 * doesn't matter if input is provided from terminal, Web or GUI).
 * 
 * @author Vladimir Igumnov
 *
 */
public class HangmanGame
{
	private final static int MAX_FAILURES = 10; // max number of incorrect guesses
    private final int[] scores = {100, 50, 25, 10, 5, 0, 0, 0, 0, 0}; // scores after each round
	
	private final String word;       // secret word in a lower case
	private StringBuffer maskedWord; // secret word masked with '*'
	private int failures;            // number of incorrect guesses
	
	public HangmanGame(String word)
	{
		this.word = word.toUpperCase(); 
		
		failures = 0;
		
		maskedWord = new StringBuffer(word);
		setMaskedWord(); // mask letters with '*' 
	}

	/**
     *  Return result of the game in String form.
     */
    @Override
    public String toString()
    {
        String result;

        String postfix = getRounds() > 1 ? " rounds." : " round.";
        String prefix = hasWon() ? "You won! " : "";
        result = "The word was \"" + word + "\". "
                + prefix + getScore() + " points earned after " + getRounds() + postfix;
        
        return result;
    }
    
    /**
     * This method returns score earned in the game.
     * @return Game score.
     */
    public int getScore()
    {
        int score;
        
        if (failures < MAX_FAILURES)
            score = scores[failures];
        else
            score = 0;
        
        return score;
    }

    /**
     * Number of rounds played in the game.
     * @return Number of played rounds.
     */
    protected int getRounds()
    {
        /* 
         * If user guessed with 0 failures, then only 1 round was played, 
         * if failed 1 time, then 2 rounds were played, and so on.
         * 
         * However, if player failed 10 times, then 10 rounds were played, 
         * because we don't run iteration #11
         */
        int rounds;
        
        if (failures < MAX_FAILURES)
            rounds = failures + 1;
        else
            rounds = MAX_FAILURES;
        
        return rounds;
    }
    
    /**
     * This method returns current number of incorrect guesses in
     * an ongoing Hangman game.
     * @return Number of incorrect guesses.
     */
    public int getFailures() {
        return failures;
    }

    /**
	 * This method validates if next round of the game can be played.<br>
	 * For this, it checks if number of failed guesses didn't yet exceed
	 * the max, and that the word is not yet discovered.
	 * 
	 * @return Indication if player can proceed with next guess.
	 */
	public boolean canContinueGame()
	{
	    return failures < MAX_FAILURES && maskedWord.indexOf("*") != -1;
	}
	
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
    public boolean checkPlayerGuess(String input)
    {
        // Secret word is stored in upper case.
        input = input.toUpperCase();
        
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

        // Increase number of failed rounds in a case of a miss.
        if (!matchFound) failures++;
        
        return matchFound;
    }
	
	/**
	 * Check if player won the game.
	 * @return Result of the game:
	 * <ul>
	 *     <li><b>true</b>  - player won the game</li>
	 *     <li><b>false</b> - player lost the game</li>
	 * </ul>
	 */
	protected boolean hasWon()
	{
		return failures < MAX_FAILURES;
	}
	
	/**
	 * Show current status of a secret word (with asterisks in
	 * place of not yet guessed letters).
	 * @return Secret word with unknown letters masked with '*'.
	 */
	public String getMaskedWord()
	{
		return maskedWord.toString(); // return unmodifiable copy
	}
	
	// mask letters with '*' (Unicode basic multilingual plane is assumed)
	private void setMaskedWord()
	{
		for (int i=0; i<maskedWord.length(); i++)
			maskedWord.setCharAt(i, '*');
	}
}

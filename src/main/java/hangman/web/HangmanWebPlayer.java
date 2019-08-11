package hangman.web;

import hangman.*;
import hangman.shared.Hangman10RoundsGame;

public class HangmanWebPlayer extends HangmanPlayer
{
    private String input; // player's guess
    
    private HangmanGame game; // current game played by a player
    private final HangmanStats stats;
    private final HangmanDictionary dictionary;

    public HangmanWebPlayer(
            String firstName, 
            String lastName,
            HangmanStats stats,
            HangmanDictionary dictionary)
    {
        super(firstName, lastName);
        this.stats = stats;
        this.dictionary = dictionary;
        this.game = new Hangman10RoundsGame(dictionary.getNextWord());
        input = null;
    }

    /**
     * Main flow of the game
     */
    @Override
    public void play()
    {
        if (game.isGameCompleted())
        {
            game = new Hangman10RoundsGame(dictionary.getNextWord());
        }
        
        if (input != null && input.length() > 0)
            game.checkPlayerGuess(input);

        if (game.isGameCompleted())
        {
            stats.saveResult(this, game);
        }
    }

    /**
     * Return current game played by player
     * 
     * @return game Game played by player
     */
    public HangmanGame getGame()
    {
        return game;
    }

    /**
     * Set user's guess
     * @param input player's input
     */
    public void setInput(String input)
    {
        this.input = input;
    }
}

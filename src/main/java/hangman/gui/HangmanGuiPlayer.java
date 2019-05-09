package hangman.gui;

import hangman.HangmanDictionary;
import hangman.HangmanPlayer;
import hangman.HangmanStats;

/**
 * This class implements a player who can play a game for
 * an input word.
 * 
 * @author Vladimir Igumnov
 *
 */
public class HangmanGuiPlayer extends HangmanPlayer
{
    private HangmanDictionary dictionary;

    public HangmanGuiPlayer(HangmanStats storage, 
                            String firstName, 
                            String lastName,
                            HangmanDictionary dictionary)
    {
        super(storage, firstName, lastName);
        this.dictionary = dictionary;
    }

    /**
     * Create a new game window for an input word.
     */
    private void playGame(String word)
    {
        // Open game window for an input word.
        var gameWindow = new HangmanGameWindow(
                "Welcome to the Hangman Game, " + getFirstName() + "!",
                word,
                this);
        
        gameWindow.setVisible(true); 
    }
    
    /**
     * Play game for the next word from dictionary.
     */
    public void play()
    {
        playGame(dictionary.getNextWord());
    }

}

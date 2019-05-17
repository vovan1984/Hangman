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
    // location to store and retrieve games state
    private final HangmanStats storage; 
    private final HangmanDictionary dictionary;


    public HangmanGuiPlayer(HangmanStats storage, 
                            HangmanDictionary dictionary,
                            String firstName, 
                            String lastName)
    {
        super(firstName, lastName);
        this.storage = storage;
        this.dictionary =  dictionary;
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
                storage,
                this);
        
        gameWindow.setVisible(true); 
    }
    
    /**
     * Play game for the next word from dictionary.
     */
    @Override
    public void play()
    {
        playGame(dictionary.getNextWord());
    }

}

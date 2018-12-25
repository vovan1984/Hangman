package hangman.gui;

import hangman.HangmanPlayer;

/**
 * This class implements a player who can play a game for
 * an input word.
 * 
 * @author Vladimir Igumnov
 *
 */
public class HangmanGuiPlayer extends HangmanPlayer
{

    public HangmanGuiPlayer(String fileName, String firstName, String lastName)
    {
        super(fileName, firstName, lastName);
    }

    /**
     * Create a new game window for an input word.
     */
    @Override
    public void playGame(String word)
    {
        // Open game window for an input word.
        var gameWindow = new HangmanGameWindow(
                "Welcome to the Hangman Game, " + getFirstName() + "!",
                word);
        
        gameWindow.setVisible(true); 
    }

}

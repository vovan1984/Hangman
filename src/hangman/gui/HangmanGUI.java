package hangman.gui;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This class starts GUI Hangman Game with a given dictionary.
 * <br>
 * It's a one player game. The game is played using GUI interface. 
 * To win, the player has 10 rounds to find the word. Words are not 
 * case sensitive.
 * <br><br>
 * <b>Logic:</b>
 * <ul>
 *     <li>First, program loads and shuffles dictionary.</li>
 *     <li>Then it creates player.</li>
 *     <li>Finally, in a loop, user plays the game
 *        for the next word from dictionary.</li>
 * </ul>
 * @author Vladimir Igumnov
 * @version 1.0
 */

public class HangmanGUI {

    public static void main(String[] args) 
    {
        // welcome window to get player details
        Frame welcome = new Frame("Welcome to the Hangman game!");

        welcome.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing (WindowEvent e)
            {
                System.exit(0);
            }
        });
        
        welcome.setSize(500, 500);
        welcome.setVisible(true);
    }

}

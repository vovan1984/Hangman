package hangman.web;

import java.io.PrintWriter;

import hangman.*;
import hangman.utils.Hangman10RoundsGame;

public class HangmanWebPlayer extends HangmanPlayer
{
    private String input; // player's guess
    private PrintWriter output; // stream where to write HTML output
    
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
        output = null;
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
        
        if (input != null)
            game.checkPlayerGuess(input);
        
        output.println("<section>" + game.getMaskedWord() + "</section>");
        
        output.println("<section>");
        output.println("<form action=\"HangmanWeb\" method=\"post\">");
        output.println("Your guess: <input type=\"text\" name=\"UserInput\"");
        output.println("<input type=\"text\"><br>");
        output.println("<input type=\"submit\" value=\"Submit\">");
        output.println("</form>");
        output.println("</section>");
        
        if (game.isGameCompleted())
            stats.saveResult(this, game);
    }

    /**
     * Set user's guess
     * @param input player's input
     */
    public void setInput(String input)
    {
        this.input = input;
    }

    public void setOutput(PrintWriter output)
    {
        this.output = output;
    }
}

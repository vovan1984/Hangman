package hangman.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import hangman.*;
import hangman.utils.Hangman10RoundsGame;
import hangman.utils.HangmanUtils;

/**
 * Implementation of the Console Player able to play provided 
 * Hangman games.
 * 
 * This class implements main console interface with the user.
 * 
 * @author Vladimir Igumnov
 *
 */
public class HangmanConsolePlayer implements HangmanPlayer
{  
    // Default encoding of resource files.
    private final static Charset DEF_ENC = Charset.forName("ISO-8859-1");
    
    // Directory of resource files in the CLASSPATH.
    private final static String RES_DIR = "/console-resources/";
    
    // "n" is to continue the game, "y" is for exit
    private final static String CONTINUE = "y";
    
    // location to store and retrieve games state
    private final HangmanStats storage; 
    
    // dictionary with secret words
    private final HangmanDictionary dictionary;
    
    private String firstName, lastName;

    // indication if user wants to play next game
    private boolean playNext;
    
    private final BufferedReader reader;
           
    private List<String> conversation; // conversation with a player
    
    /**
     * Constructor for the player of a Hangman game.
     * @param fileName File with statistics of users who played the game.
     * @param firstName First name of the player.
     * @param lastName Last name of the player.
     * @param reader Input stream
     */
    public HangmanConsolePlayer(
            HangmanStats storage, 
            HangmanDictionary dictionary,
            String firstName, 
            String lastName,
            BufferedReader reader)
    {  
        this.firstName = firstName;
        this.lastName = lastName;
        this.reader = reader;     
        this.storage = storage;
        this.dictionary = dictionary;
        
        playNext = true;
        conversation = new LinkedList<String>();
    }
    
    /**
     * Play games in a loop until user decides to leave
     */
    @Override
    public void play()
    {
        // Play games in a loop until user decides to leave
        do
        {
            HangmanGame game = new Hangman10RoundsGame(dictionary.getNextWord());
            playGame(game);
        }
        while (wantsToPlayNext());   
    }
    
    /**
     * Play input Hangman game
     * @param game input game
     */
    private void playGame(HangmanGame game)
    {
        String exitGame;
        
        try
        {
            play(game);
            storage.saveResult(this, game);
            
            System.out.print("Do you want to play another game ? (y/n)\n--> ");
            exitGame = reader.readLine();
            addToConversation("Do you want to play another game ? (y/n)\n--> " + exitGame);
            playNext = exitGame.equalsIgnoreCase(CONTINUE);
        }
        catch (IOException e)
        {
            System.out.println("IO error!");
            e.printStackTrace();
            return;
        }
    }

    /**
     * Check if user wants to play next game
     */
    private boolean wantsToPlayNext()
    {
        return playNext;
    }

    /**
     *  Returns first name of the player.
     * @return First name of the player.
     */
    @Override
    public String getFirstName()
    {
        return firstName;
    }

    /**
     *  Returns last name name of the player.
     * @return Last name of the player.
     */
    @Override
    public String getLastName()
    {
        return lastName;
    }
    
    /**
     * Return console conversation with a player.   
     * @return Conversation.
     */
    private List<String> getConversation() 
    {
        return conversation;
    }

    /**
     * Store message to a conversation.
     * @param msg Message to add.
     */
    private void addToConversation(String msg) 
    {
        conversation.add(msg);
    }
    
    /**
     * Display a message and store it to a conversation.
     * @param msg Message to add and display.
     */
    private void showAndAddToConversation(String msg) 
    {
        System.out.println(msg);
        conversation.add(msg);
    }
    
    /**
     * Get input characters from player.
     * @return Letter, substring or a full word suggestion from a player.
     * @param game Game for which an answer is requested
     */
    private String requestInput(HangmanGame game)
    {
        String input = null;
        
        // Get non-empty input
        while (input == null || input.equals(""))
        {
            showAndAddToConversation("The secret word is " + game.getMaskedWord());
            System.out.print("Your input: ");

            try
            {
                input = reader.readLine();
                addToConversation("Your input: " + input);
                
                if (input.equals(""))
                    showAndAddToConversation("Please provide non-empty input!");
            } 
            catch (IOException e)
            {
                showAndAddToConversation("IO error occured! Please re-enter your choice!");
                e.printStackTrace();
            }
        }
        
        return input;
    }

    /**
     * Play the Hangman game
     * @param game game to play
     */
    private void play(HangmanGame game)
    {
        if (game.isGameCompleted())
            throw new IllegalStateException("You are trying to continue already completed game!");
        
        showAndAddToConversation("Ok, let's start!");        
        showAndAddToConversation("Your word has " + game.getMaskedWord().length() + " letters");

        String input;

        // play while there are letters to be guessed
        while (!game.isGameCompleted())
        {
            input = requestInput(game);

            // try to find matches of input substring in secret word
            boolean match = game.checkPlayerGuess(input);

            // Communicate match or miss to the user
            showResponse(game, input, match); 
        }       
        
        showAndAddToConversation(game.toString());
    }
    
    /**
     * Inform player of match/miss and show the secret word with letters guessed by now.<br>
     * This method also shows text-graphical stage of the Hangman game.
     * 
     * @param input Input substring provided by player.
     * @param match Result of the substring search in the hidden word:
     *        <ul>
     *           <li><b>true</b>  - substring was found in the word.
     *           <li><b>false</b> - substring was not found.
     *        </ul>
     */
    private void showResponse(HangmanGame game, String input, boolean match)
    {   
        /* 
         * Indication if Hangman stage picture should be stored 
         * to a conversation history.
         */
        boolean addImgToConversation = game.isGameCompleted(); // if can't continue, then store image.
        
        // clear terminal screen
        HangmanUtils.clearScreen();
        
        // display communication up to now
        getConversation().forEach(s -> System.out.println(s));
    
        String response;
            
        if (match)
            response = "Well done, " + getFirstName() + "!";
        else
            response = "Sorry, there is no \"" + input + "\" in your secret word :(";

        showAndAddToConversation(response + "\n");
        
        // display current presentation of the gallows
        switch (game.getFailures())
        {
            case 0:  break;
            case 1:  printFile("stage1.txt", addImgToConversation);
                     break;
            case 2:  printFile("stage2.txt", addImgToConversation);
                     break;
            case 3:  printFile("stage3.txt", addImgToConversation);
                     break;
            case 4:  printFile("stage4.txt", addImgToConversation);
                     break;
            case 5:  printFile("stage5.txt", addImgToConversation);
                     break;
            case 6:  printFile("stage6.txt", addImgToConversation);
                     break;
            case 7:  printFile("stage7.txt", addImgToConversation);
                     break;
            case 8:  printFile("stage8.txt", addImgToConversation);
                     break;
            case 9:  printFile("stage9.txt", addImgToConversation);
                     break;
            case 10: printFile("stage10.txt", addImgToConversation);
                     break;
            default: break;
        }
    }
    
    /**
     * Print content of an input file to a console.
     * 
     * @param fileName Input file name
     */
    private void printFile(String fileName, boolean addImgToConversation)
    {           
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(RES_DIR + fileName), DEF_ENC)))
        {
            String line;
            while ( (line = br.readLine()) != null)
            {
                if(addImgToConversation)
                    showAndAddToConversation(line);
                else
                    System.out.println(line);
            }
        } 
        catch (IOException e) 
        {
            System.out.println("IO error while processing " + fileName + "!");
            e.printStackTrace();
        }
    }

}

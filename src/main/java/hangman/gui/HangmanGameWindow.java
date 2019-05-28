package hangman.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

import hangman.HangmanGame;
import hangman.HangmanPlayer;
import hangman.HangmanStats;
import hangman.utils.Hangman10RoundsGame;

public class HangmanGameWindow extends HangmanWindow implements ActionListener
{
    private static final long serialVersionUID = 1L;
    
    // Colors of buttons after miss or match.
    private static final Color MATCH_COLOR = LIGHT_BLUE;
    private static final Color MISS_COLOR = Color.RED;
    
    private Label hiddenWordLabel;
    private HangmanGame game;
    private HangmanPlayer player;
    private HangmanStats storage;
    private HangmanStatePicture imageArea;
    private Map<String, JButton> buttons;
    
    public HangmanGameWindow(String title,
                             String word,
                             HangmanStats storage,
                             HangmanPlayer player)
    {
        super(title);

        this.player = player;
        this.storage = storage;
        
        // create a game instance
        game = new Hangman10RoundsGame(word);
        
        // list of buttons
        buttons = new HashMap<String, JButton>();
        
        setUpperPane();
        setLowerPane();
        
        // Window should be able to get keyboard focus to respond to pressed buttons.
        setFocusable(true);
        addKeyListener(new HangmanKeyAdapter());    
    }

    /*
     *  Split upper panel to two parts:
     *  header and image area.
     */
    private void setUpperPane()
    {
        // Setup header panel.  
        hiddenWordLabel = new Label(game.getMaskedWord());
        hiddenWordLabel.setFont(DEF_FONT);
        
        upperPane.setLayout(new GridBagLayout());   
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        upperPane.add(hiddenWordLabel, gbc);
        
        // Setup image area.
        imageArea = new HangmanStatePicture("pendu00.jpg");
        gbc.gridx = 0;
        gbc.gridy = 1;
        
        upperPane.add(imageArea, gbc);
    }


    // Setup alphabet letters in a lower pane.
    private void setLowerPane()
    {
        lowerPane.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;     // row index of the first button
        gbc.gridy = 0;     // column index of the first button
        gbc.weightx = 1;   // distribute horizontal space evenly     
        gbc.weighty = 1;   // distribute vertical space evenly
        gbc.ipadx = 10;
        
        int position = 0; // order of the letter
   
        for (char c = 'A'; c <= 'Z'; c++)
        {
            handleButton(new JButton(c + ""), gbc, position++);
        }
        
        handleButton(new JButton("À"), gbc, position++);       
        handleButton(new JButton("É"), gbc, position++);
        handleButton(new JButton("È"), gbc, position++);        
        handleButton(new JButton("Ç"), gbc, position++);

    }
    
    private void handleButton(JButton letter, 
                              GridBagConstraints gbc,
                              int position)
    {
        // store button to a map of buttons
        buttons.put(letter.getText(), letter);

        // Add handler for pressed button event
        letter.addActionListener(this);
        
        letter.setFont(DEF_FONT);
        letter.setBackground(Color.WHITE);
        letter.setOpaque(true);
        letter.setFocusPainted(false);
        // space between label and the button's border
        letter.setMargin(new Insets(0, 0, 0, 0));
        
        if (position > 0) // don't increase for the first letter 
        {
            gbc.gridx++;
            
            // next row
            if (position%10 == 0) 
            {
                gbc.gridy++; 
                gbc.gridx = 0;
            }
        }
        
        lowerPane.add(letter, gbc);        
    }

    /**
     * For every pressed button, check if a corresponding
     * letter exists in a hidden word.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Get letter of the pressed button.
        String letter = e.getActionCommand();
        
        // Check if clicked letter exists in a word.
        checkLetter(letter);
        
        // Return keyboard input to the window.
        requestFocusInWindow();
    }
    
    /**
     * Check if letter exists in a secret word,
     * and show response to a player.
     * 
     * @param letter Letter clicked or typed by the player.
     */
    private void checkLetter(String letter)
    {
        // Check if letter exists in a secret word.
        boolean match = game.checkPlayerGuess(letter);
        
        // Handle match or miss.
        showResponse(letter, match);       
    }

    /**
     * Inform player of match/miss (by changing color of the button)
     * and show the secret word with letters guessed by now.<br>
     * 
     * This method also shows graphical stage of the Hangman game.
     * 
     * @param input Input substring provided by player.
     * @param match Result of the substring search in the hidden word:
     *        <ul>
     *           <li><b>true</b>  - substring was found in the word.
     *           <li><b>false</b> - substring was not found.
     *        </ul>
     */
    public void showResponse(String input, boolean match)
    {   
        JButton curButton = buttons.get(input);
            
        /* 
         * Change button color if typed/clicked button is currently 
         * shown on the screen.
         */
        if (curButton != null)
        {
            if (match)
            {
                // Update masked word presentation.
                hiddenWordLabel.setText(game.getMaskedWord());

                // Change button color and background.
                curButton.setBackground(MATCH_COLOR);
                curButton.setForeground(Color.WHITE);
                curButton.setBorderPainted(false);
            }
            else
            {
                // Change button color and background in case of miss.
                curButton.setForeground(MISS_COLOR);
                curButton.setBorder(new LineBorder(MISS_COLOR));
            }
        }
        
        // display current presentation of the gallows
        imageArea.setImg(String.format("pendu%02d.jpg", game.getFailures()));
               
        // Check if we can continue guessing the word.
        if (game.isGameCompleted())
        {
            storage.saveResult(player, game);

            int dialogResult = JOptionPane.showConfirmDialog (this, 
                    game + "\n" + "Do you want to continue?",
                    "Thanks for your game!",
                    JOptionPane.YES_NO_OPTION);
            
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                // start another game and dispose this window.
                player.play();
                dispose();
            }
            else
                System.exit(0); // user doesn't want to continue

        }
    }

    /**
     * Inner class for handling keyboard events.
     */
    private class HangmanKeyAdapter extends KeyAdapter
    {
        /**
         * Check if typed letter exists in a hidden word.
         */
        @Override
        public void keyTyped(KeyEvent e)
        {
            /* Verify typed character. We convert it to upper case
             * because buttons are initialized with upper case letters.
             */       
            checkLetter((e.getKeyChar() + "").toUpperCase());        
        }
    }
}

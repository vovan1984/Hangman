package hangman.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import hangman.HangmanGame;

public class HangmanGameWindow extends HangmanWindow implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private static final Font DEF_FONT = new Font("Serif", Font.PLAIN, 20);
    
    // Colors of buttons after miss or match.
    private static final Color MATCH_COLOR = HangmanWindow.LIGHT_BLUE;
    private static final Color MISS_COLOR = Color.RED;
    
    private Label hiddenWordLabel;
    private HangmanGame game;
    private HangmanGuiPlayer player;
    private HangmanImageCanvas imageArea;
    private Map<String, JButton> buttons;
    private HangmanDialog continueDialog; // dialog to show result of the game
    
    
    public HangmanGameWindow(String title,
                             String word,
                             HangmanGuiPlayer player)
    {
        super(title);

        this.player = player;
        
        // create a game instance
        game = new HangmanGuiGame(word);
        
        // list of buttons
        buttons = new HashMap<String, JButton>();
        
        setUpperPane();
        setLowerPane();
        
        // modal dialog to display result of the game
        continueDialog = new HangmanDialog(this, "Thanks for your game!",
                true);
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
        imageArea = new HangmanImageCanvas("pendu00.jpg");
        gbc.gridx = 0;
        gbc.gridy = 1;
        
        upperPane.add(imageArea, gbc);
    }


    // Setup alphabet letters in a lower pane.
    private void setLowerPane()
    {
        lowerPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;        
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.PAGE_END;
        
        // space label
        gbc.gridwidth = 10;
        gbc.fill = GridBagConstraints.BOTH;
        Label label = new Label();
        
        label.setBackground(new Color(0xFFBEDDFC));
        //lowerPane.add(label, gbc);
        
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 0;
        gbc.ipadx = 10;
        gbc.insets = new Insets(10, 5, 5, 0);  //top padding
        
        int position = 0;
   
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
        //letter.setBorder(BorderFactory.createEmptyBorder());
        //letter.setPreferredSize(new Dimension(62, 62));
        letter.setMargin(new Insets(0, 0, 0, 0));
        
        if (position > 0) // don't increase for the first letter 
        {
            gbc.gridx++;
            
            // next row
            if (position%10 == 0) 
            {
                gbc.gridy++; 
                gbc.gridx = 0;
                if (position >= 10)
                    gbc.weighty = 0.0;
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
        
        // display current presentation of the gallows
        switch (game.getFailures())
        {
            case 0:  break;
            case 1:  imageArea.setImg("pendu01.jpg");
                     break;
            case 2:  imageArea.setImg("pendu02.jpg");
                     break;
            case 3:  imageArea.setImg("pendu03.jpg");
                     break;
            case 4:  imageArea.setImg("pendu04.jpg");
                     break;
            case 5:  imageArea.setImg("pendu05.jpg");
                     break;
            case 6:  imageArea.setImg("pendu06.jpg");
                     break;
            case 7:  imageArea.setImg("pendu07.jpg");
                     break;
            case 8:  imageArea.setImg("pendu08.jpg");
                     break;
            case 9:  imageArea.setImg("pendu09.jpg");
                     break;
            case 10: imageArea.setImg("pendu10.jpg");
                     break;
            default: break;
        }
        
        // Check if we can continue guessing the word.
        if (!game.canContinueGame())
        {
            player.saveResult(game);
            continueDialog.setMessage(game.toString());
            continueDialog.setVisible(true);
            
            // start another game and dispose this window.
            player.play();
            dispose();
        }
    }
    
}

package hangman.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

import hangman.HangmanDictionary;
import hangman.HangmanStats;

/**
 * 
 * The first screen displayed to a player in 
 * order to get player's details.
 *
 * @author Vladimir Igumnov
 *
 */
public class HangmanWelcomeWindow extends HangmanWindow implements ActionListener
{
    private static final long serialVersionUID = 1L;
    
    private static final String GAME_NAME = "HANGMAN";
    private static final int DEF_BUTTON_HEIGHT = 50;
    private static final int DEF_BUTTON_WIDTH = 220;
    
    // set default font for a name of the game
    private static final Font DEF_TITLE_FONT = new Font("Serif", Font.PLAIN, 80);
    
    private JLabel nameLabel; 
    private String title;
    private HangmanDictionary dictionary;
    private HangmanStats playersInfo;
    private GridBagConstraints upAlignment, lowAlignment;
    
    public HangmanWelcomeWindow(String title,
                               HangmanDictionary dictionary,
                               HangmanStats playersInfo)
    {
        super(title);
        this.title = title;
        this.dictionary = dictionary;
        this.playersInfo = playersInfo;
        
        // create button for starting new game.
        JButton newGameButton = createNewGameButton();
        
        // place button to the center of a lower panel
        lowerPane.setLayout(new GridBagLayout());
        lowAlignment = new GridBagConstraints();
        lowAlignment.anchor = GridBagConstraints.CENTER;      
        lowerPane.add(newGameButton, lowAlignment);
                
        // create label with the name of the game
        nameLabel = new JLabel(GAME_NAME, JLabel.CENTER);
        nameLabel.setFont(DEF_TITLE_FONT);
        nameLabel.setForeground(Color.WHITE);
        
        // add label to the center of an upper panel
        upperPane.setLayout(new GridBagLayout());
        upAlignment = new GridBagConstraints();
        upAlignment.fill = GridBagConstraints.BOTH;  
        upperPane.add(nameLabel, upAlignment);

        // handler for resize event
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                repaint(); // repaint the window if it was resized
            }
        });  
        
        // display the window
        setVisible(true);
    }

    /**
     * This method is used to create a button for starting new game.
     * It is setup to accept clicks and pressing of Enter.
     * 
     * @return New Game button.
     */
    private JButton createNewGameButton()
    {
        // add button
        JButton newGameButton = new JButton("NEWGAME");
        newGameButton.setOpaque(true);
        newGameButton.setFocusPainted(false);
        newGameButton.setBorderPainted(false);
        newGameButton.setFont(DEF_FONT);
        newGameButton.setBackground(LIGHT_BLUE); // light blue button
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setPreferredSize(new Dimension(DEF_BUTTON_WIDTH, DEF_BUTTON_HEIGHT));
        
        // Clicking on button or pressing Enter should open LeaderBoard window.
        setFocusable(true); // has to be focusable to receive key events
        getRootPane().setDefaultButton(newGameButton);
        newGameButton.addActionListener(this); // handler for button clicked event
        
        return newGameButton;
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Dimension d = getSize(); // get current dimension of the window
        
        nameLabel.setFont(DEF_TITLE_FONT.deriveFont((float)0.12*Math.min(d.height, d.width)));   
    }

    /**
     * Handler for pressed NEWGAME button.
     * It closes current screen, and returns control to the main flow.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        this.setVisible(false);
        var leaderBoard = new HangmanLeaderboardWindow(title,
                                                dictionary,
                                                playersInfo);
        leaderBoard.setVisible(true);
    }   
}

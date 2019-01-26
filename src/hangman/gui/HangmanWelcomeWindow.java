package hangman.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
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
    
    private Label nameLabel; 
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
        
        // add button
        JButton newGameButton = new JButton("NEWGAME");
        newGameButton.setFocusPainted(false);
        newGameButton.setBorderPainted(false);

        newGameButton.setFont(DEF_FONT);
        newGameButton.setBackground(LIGHT_BLUE); // light blue button
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setPreferredSize(new Dimension(DEF_BUTTON_WIDTH, DEF_BUTTON_HEIGHT));

        // place button to the center of a lower panel
        lowerPane.setLayout(new GridBagLayout());
        lowAlignment = new GridBagConstraints();
        lowAlignment.anchor = GridBagConstraints.CENTER;      
        
        newGameButton.addActionListener(this); // handler for button pressed event

        lowerPane.add(newGameButton, lowAlignment);
        
        upperPane.setLayout(new GridBagLayout());
        upAlignment = new GridBagConstraints();
        upAlignment.fill = GridBagConstraints.BOTH;       
        upAlignment.weightx = 0.4;
        upAlignment.weighty = 0.6; // cell takes 75% of the area height
        upAlignment.gridwidth = 1;
        upAlignment.gridx = 1; // coordinates of the cell
        upAlignment.gridy = 1;     
        
        // add label with the name of the game
        nameLabel = new Label(GAME_NAME, Label.CENTER);
        nameLabel.setFont(DEF_TITLE_FONT);
        nameLabel.setForeground(Color.WHITE);
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
